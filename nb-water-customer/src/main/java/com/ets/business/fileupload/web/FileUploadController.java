package com.ets.business.fileupload.web;

import com.ets.business.fileupload.entity.FileUpload;
import com.ets.business.fileupload.service.FileUploadService;
import com.ets.common.PageListData;
import com.ets.system.log.opr.entity.tb_log_opr;
import com.ets.system.log.opr.service.LogOprService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("fileManage")
public class FileUploadController {

    @Resource
    LogOprService logService;
    @Resource
    FileUploadService fileService;

    @RequestMapping("list")
    public String list(HttpServletRequest request)
    {
        return "business/fileupload/fileHistoryList";
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
        return "business/fileupload/manual";
    }
    //通过制定的URL访问历史文件
    @RequestMapping("byUrlFindFile")
    public String byUrlFindFile(HttpServletRequest request,String fileurl)
    {
        request.setAttribute("url",fileurl);
        return "business/fileupload/manual";
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

        return "business/fileupload/fileUpload";
    }


}
