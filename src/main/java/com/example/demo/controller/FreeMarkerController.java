package com.example.demo.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.example.demo.bean.Order;
import com.example.demo.repository.OrderRepository;

import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j(topic="logger")
public class FreeMarkerController {
	
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	private Base64 b64Encoder = new Base64();

	// 处理下载word文档
	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 告诉浏览器用什么软件可以打开此文件
		response.setHeader("content-Type", "application/msword");
		// 下载文件的默认名称
		response.setHeader("Content-Disposition", "attachment;filename=xx.doc");
		
		freeMarkerConfigurer.getConfiguration().setClassForTemplateLoading(getClass(), "/");
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate("templates/order.ftl");

		String path = ClassUtils.getDefaultClassLoader().getResource("static/").getPath();
		log.info("图片的路径为：{}", path);
		
		List<Order> list = orderRepository.findAll();
		for (int i = 0; i < list.size(); i++) {
			Order order = list.get(i);
			if(StringUtils.isBlank(order.getImageName())){
				order.setImageName("cloud.jpg");
			}
			File file = new File(path, order.getImageName());
			FileInputStream fis = new FileInputStream(file);
			byte[] imgData = new byte[fis.available()];
			fis.read(imgData);
			fis.close();
			String headPortrait = b64Encoder.encodeAsString(imgData);
			order.setHeadPortrait(headPortrait);
			log.info("图片的字节码：{}", headPortrait);
		}
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("orders", list);
		template.process(root, new OutputStreamWriter(response.getOutputStream()));
	}

}
