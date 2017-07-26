package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.bean.Order;
import com.example.demo.bean.SelectItem;
import com.example.demo.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;
	
	private String contextPath;

	@ResponseBody
	@RequestMapping("/orderList")
	public List<Order> orderList() {
		List<Order> orderList = orderRepository.findAll();
		return orderList;
	}
	
	@RequestMapping("/orders")
	public String loadOrder(Model model , HttpServletRequest request) {
		List<Order> orderList = orderRepository.findAll();
		model.addAttribute("orders", orderList);
		contextPath = request.getServletContext().getContextPath();
		model.addAttribute("basePath", contextPath);
		log.info("==>contextPath is {}", contextPath);
		return "orders";
	}

	@RequestMapping("/inputOrder")
	public String addOrder(Model model) {
		model.addAttribute("order", new Order());
		return "inputOrder";
	}

	@RequestMapping(value = "/inputOrderAction", method = RequestMethod.POST)
	public ModelAndView addOrderAction(@ModelAttribute("order") @Valid Order order, BindingResult bindResult, ModelAndView mv) {
		System.out.println(order);
		if(bindResult.hasErrors()){  
			List<FieldError> fieldErrors = bindResult.getFieldErrors();
			mv.addObject("fieldErrors", fieldErrors);  
			mv.addObject("order", order);
			mv.setViewName("inputOrder");
			return mv;
        }else{  
        	if(order.getOrderId()==null){
        		orderRepository.save(order);
        	}else{
        		orderRepository.saveAndFlush(order);
        	}
            List<Order> orders = orderRepository.findAll();
            mv.addObject("msg", "提交成功！");  
            mv.addObject("orders", orders);
            mv.setViewName("redirect:/orders");
        }  
		return mv;
	}

	@RequestMapping("/message")
	public String message(Model model) {
		model.addAttribute("message", "js message test");
		Map<Integer,String> map = new LinkedHashMap<>();
		List<SelectItem<Integer, String>> list = new LinkedList<>();
		SelectItem<Integer,String> item =null;
		String[] weeks = new String[]{"","星期一","星期二","星期三","星期四","星期五","星期六","星期日",};
		for(int i=1; i<=7; i++){
			item = new SelectItem<Integer, String>(i, weeks[i]);
			map.put(i, weeks[i]);
			list.add(item);
		}
		
		model.addAttribute("map", map);
		model.addAttribute("list", list);
		
		return "message";
	}
	
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("orderId") Long id,  Model model) {
		if(null != id){
			orderRepository.delete(id);
		}
		return "redirect:/orders";
	}
	
	
	@RequestMapping("/modify")
	public String modify(@RequestParam("orderId") Long id,  Model model) {
		
		if(null != id){
			Order order = orderRepository.findOne(id);
			if(null != order){
				model.addAttribute("order", order);
			}else{
				log.info("从数据库中没有找到对应的记录");
			}
		}else{
			log.info("从请求参数中获取的Id is null");
		}
		
		return "inputOrder";
	}
	
	
	
	/**
	 * 解决前台页面日期String 无法转换成java 对应的Date类型
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	   // binder.addValidators(new BeanValidator<Order>());
	}
	
	

	/*@ModelAttribute("order")
	public Order orderModel(Model model) {
		boolean containsAttribute = model.containsAttribute("order");
		if(!containsAttribute){
			return new Order();
		}else{
			return (Order) model.asMap().get("order");
		}
	}*/

}
