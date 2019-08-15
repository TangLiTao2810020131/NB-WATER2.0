package com.ets.common;

import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author klguang
 *
 */
public class JxlsUtils{

	private static final String TEMPLATE_PATH="jxls";

	public static void exportExcel(InputStream is, OutputStream os, Map<String, Object> model) throws IOException{
		Context context = new Context();
		if (model != null) {
			for (String key : model.keySet()) {
				context.putVar(key, model.get(key));
			}
		}
		JxlsHelper jxlsHelper = JxlsHelper.getInstance();
		Transformer transformer  = jxlsHelper.createTransformer(is, os);
		JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator)transformer.getTransformationConfig().getExpressionEvaluator();
		Map<String, Object> funcs = new HashMap<String, Object>();
		funcs.put("utils", new JxlsUtils());    //添加自定义功能
		evaluator.getJexlEngine().setFunctions(funcs);
		jxlsHelper.processTemplate(context, transformer);
	}

	public static void exportExcel(File xls, File out, Map<String, Object> model) throws FileNotFoundException, IOException {
		exportExcel(new FileInputStream(xls), new FileOutputStream(out), model);
	}

	public static void exportExcel(String templateName, OutputStream os, Map<String, Object> model) throws FileNotFoundException, IOException {
		File template = getTemplate(templateName);
		if(template!=null){
			exportExcel(new FileInputStream(template), os, model);	
		}
	}


	//获取jxls模版文件

	public static File getTemplate(String name){

		String templatePath = JxlsUtils.class.getClassLoader().getResource(TEMPLATE_PATH).getPath();
		System.out.println(templatePath);
		File template = new File(templatePath, name);
		if(template.exists()){
			return template;
		}
		return null;
	}	

	// 日期格式化
	public String dateFmt(Date date, String fmt) {
		if (date == null) {
			return "";
		}
		try {
			SimpleDateFormat dateFmt = new SimpleDateFormat(fmt);
			return dateFmt.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// if判断
	public Object ifelse(boolean b, Object o1, Object o2) {
		return b ? o1 : o2;
	}

	// 下载
	public static void doDownLoad(String path, String name,
			HttpServletResponse response,HttpServletRequest request) {
		try{
			final String userAgent = request.getHeader("USER-AGENT");

			String finalFileName = null;
			if(userAgent.contains("MSIE")){//IE浏览器
				finalFileName = URLEncoder.encode(name,"UTF8");
			}else if(userAgent.contains("Mozilla")){//google,火狐浏览器
				 finalFileName = new String(name.getBytes(), "ISO-8859-1");
			}else{
				finalFileName = URLEncoder.encode(name,"UTF8");//其他浏览器
			}

			response.reset();
			response.setHeader("Content-disposition","attachment;success=true;filename =" + finalFileName);
			response.setContentType("application/vnd.ms-excel");
			BufferedInputStream bis =null;
			BufferedOutputStream bos =null;
			OutputStream fos =null;
			InputStream fis =null;
			File uploadFile =new File(path);
			fis =new FileInputStream(uploadFile);
			bis =new BufferedInputStream(fis);
			fos = response.getOutputStream();
			bos =new BufferedOutputStream(fos);
			// 弹出下载对话框
			int bytesRead = 0;
			byte[] buffer =new byte[8192];
			while((bytesRead = bis.read(buffer, 0,8192)) != -1) {
				bos.write(buffer,0, bytesRead);
			}
			bos.flush();
			fis.close();
			bis.close();
			fos.close();
			bos.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
