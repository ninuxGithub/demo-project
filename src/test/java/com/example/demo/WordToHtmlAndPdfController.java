package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户的controller
 * 
 * @author Administrator
 * 
 */
@Controller
public class WordToHtmlAndPdfController {
	
	public String getRelPath(){
		Properties props = new Properties();
		String pathString = System.getProperty("user.dir").replace("bin", "");
		pathString += "webapps\\CensusRegisterApproveSystem\\"
				+ "limit.properties";
		FileInputStream fis;
		String relPath = null;
		try {
			fis = new FileInputStream(pathString);
			props.load(fis);
			relPath=props.getProperty("image.address");
		} catch (Exception e1) {
			e1.printStackTrace();
			return "0";
		}
		return relPath;
	}
	@RequestMapping(value = "/wordToHtml")
	@ResponseBody
	public String[] wordToHtml(String src, HttpServletRequest request) throws Exception {
		src=URLDecoder.decode(request.getParameter("src"),"utf-8");
		String path = src.substring(src.lastIndexOf("/") + 1);
		//String path ="23230119840203462X-2017-06-12-lusp.doc";
		String relPath = getRelPath();
		Word2HtmlPDF.convertHtml(relPath + path, relPath + path.substring(0, path.indexOf(".")) + ".html");
		String[] strings=new String[1];
		strings[0]=("/myApp/image/"+path.substring(0, path.indexOf(".")) + ".html").toString();
		return strings;
	}

	@RequestMapping("/wordToPdf")
	@ResponseBody
	public String[] wordToPdf(String src,HttpServletRequest request) throws Exception {
		src=URLDecoder.decode(request.getParameter("src"),"utf-8");
		String path = src.substring(src.lastIndexOf("/") + 1);
		String relPath =getRelPath();
		Word2HtmlPDF.convert(relPath + path, relPath + path.substring(0, path.indexOf(".")) + ".pdf");
		String[] strings=new String[1];
		strings[0]=("/myApp/image/"+path.substring(0, path.indexOf(".")) + ".pdf").toString();
		return strings;
	}

	@RequestMapping("/delHtmlAndPdf")
	public void delHtmlAndPdf(String src, HttpServletRequest request) throws Exception {
		src=URLDecoder.decode(request.getParameter("src"),"utf-8");
		String path = src.substring(src.lastIndexOf("/") + 1);
		String relPath =getRelPath();
		if (path.endsWith(".html")) {
			File fileD=new File(relPath+path.substring(0, path.indexOf('.'))+".files");
			if(fileD.exists()){
				File[] files = fileD.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();	
				}
			}
			fileD.delete();
		}
		File file = new File(relPath+ path);
		if (file.exists()) {
			file.delete();
		}
	}

}
