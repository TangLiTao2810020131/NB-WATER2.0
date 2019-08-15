package com.ets.system.cus_fileupload.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ets.common.ConstantConfig;
import com.ets.common.PageListData;
import com.ets.common.UUIDUtils;
import com.ets.system.cus_fileupload.entity.FileUpload;
import com.ets.system.cus_fileupload.service.FileUploadService;
import com.ets.system.log.opr.entity.tb_log_opr;
import com.ets.system.log.opr.service.LogOprService;
import com.ets.system.user.entity.tb_user;
import com.google.gson.Gson;

@Controller
@RequestMapping("fileManage")
public class FileUploadController {

    @Resource
    LogOprService logService;
    @Resource
    FileUploadService fileService;
    
    @Resource
    ConstantConfig constantConfig;


    @RequestMapping("list")
    public String list(HttpServletRequest request)
    {
        return "system/fileupload/fileHistoryList";
    }

    //所有的文件上传历史
    @RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(int page, int limit,FileUpload fu)
    {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("uploader", fu.getUploader());
        map.put("filename", fu.getFilename());
        map.put("uploadtime", fu.getUploadtime());
        map.put("page", (page)*limit);//oracle
        map.put("limit", (page-1)*limit);//oracle

        List<FileUpload> fus = fileService.getDatas(map);
        long count = fileService.getCount(map);
        PageListData<FileUpload> pageData = new PageListData<FileUpload>();
        pageData.setCode("0");
        pageData.setCount(count);
        pageData.setMessage("");
        pageData.setData(fus);

        Gson gson = new Gson();
        String listJson = gson.toJson(pageData);
        return listJson;
    }

    //通过最新的时间查找文件
    @RequestMapping("manual")
    public String manual(HttpServletRequest request)
    {
        String url=fileService.findUrlByNewTime().getFileurl();
        request.setAttribute("url",url);
        return "system/fileupload/manual";
    }
    //通过制定的URL访问历史文件
    @RequestMapping("byUrlFindFile")
    public String byUrlFindFile(HttpServletRequest request,String fileurl)
    {
        request.setAttribute("url",fileurl);
        return "system/fileupload/manual";
    }

    //文件上传要跳转的jsp页面
    @RequestMapping("fileUploadPage")
    public String fileUploadPage(HttpServletRequest request)
    {

        tb_log_opr log=new tb_log_opr();
        log.setModulename("文件管理-文件上传");
        log.setOprcontent("查看文件上传列表");
        logService.addLog(log);

        FileUpload fu=fileService.findUrlByNewTime();
        if(fu!=null)
        {
            request.setAttribute("filename",fu.getFilename());
            request.setAttribute("filesize",fu.getFilesize());
        }

        return "system/fileupload/fileUpload";
    }

    //文件上传实现
    @RequestMapping("fileUpload")
    @ResponseBody
    public Map<String,Object> fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request)
    {
        String newName=System.currentTimeMillis()+".pdf";
        Map<String,Object> map=new HashMap<String,Object>();
        if (!file.isEmpty())
        {
            // 取文件格式后缀名
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

            //判断上传文件是否是pdf格式,非pdf格式不进行文件上传
            if(!type.contains(".pdf"))
            {
                map.put("message","文件格式应为pdf格式");
                return map;
            }

            //按照时间将上传文件放在不同文件夹中
            Date date=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String dateStr=sdf.format(date);

//            //获取服务器端真实路径
//            String path = request.getSession().getServletContext().getRealPath("");
            String path = constantConfig.getSysUploadUrl();
            System.out.println(path);
            File destFile = new File(path+"/"+dateStr,newName);

            //如果父路径不存在,创建父路径
            if(!destFile.getParentFile().exists())
            {
                destFile.getParentFile().mkdirs();
            }

            try {
                InputStream is=file.getInputStream();
                BufferedInputStream bis=new BufferedInputStream(is);
                BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(destFile));
                byte[] bt=new byte[1024];
                int t=-1;
                while((t=bis.read(bt))!=-1)
                {
                    bos.write(bt,0,bt.length);
                }
                bos.close();
                bis.close();

                //将上传文件保存到数据库
                FileUpload fu=new FileUpload();
                fu.setId(UUIDUtils.getUUID());
                tb_user user = (tb_user)SecurityUtils.getSubject().getSession().getAttribute("userSession");
                fu.setUploader(user.getUsername());
                fu.setUploadtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                fu.setFilename(file.getOriginalFilename());
                fu.setFileurl("upload/"+dateStr+"/"+newName);
                fu.setFilesize(String.format("%.2f",file.getSize()/(1.0*1024*1024))+" MB");
                fileService.addFileRecord(fu);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        map.put("message","文件上传成功");
        map.put("newName",newName);

        //将上传文件信息保存到操作日志
        tb_log_opr log=new tb_log_opr();
        log.setModulename("文件管理-文件上传");
        log.setOprcontent("上传PDF文件[文件名称:"+file.getOriginalFilename()+"]");
        logService.addLog(log);

        return map;

    }


}
