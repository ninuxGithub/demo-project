package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.bean.Order;
import com.example.demo.repository.OrderRepository;

@Controller
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;
	

	/*@ModelAttribute("order")
	public Order orderModel(Model model) {
		boolean containsAttribute = model.containsAttribute("order");
		if(!containsAttribute){
			return new Order();
		}else{
			return (Order) model.asMap().get("order");
		}
	}*/

	@RequestMapping("/orders")
	public String loadOrder(Model model) {
		List<Order> orderList = orderRepository.findAll();
		model.addAttribute("orders", orderList);
		return "orders";
	}

	@RequestMapping("/addOrder")
	public String addOrder(Model model) {
		model.addAttribute("order", new Order());
		return "addOrder";
	}

	@RequestMapping(value = "/addOrderAction", method = RequestMethod.POST)
	public ModelAndView addOrderAction(@ModelAttribute("order") @Valid Order order, BindingResult bindResult, ModelAndView mv) {
		System.out.println(order);
		if(bindResult.hasErrors()){  
			List<FieldError> fieldErrors = bindResult.getFieldErrors();
			mv.addObject("fieldErrors", fieldErrors);  
			mv.addObject("order", order);
			mv.setViewName("addOrder");
			return mv;
        }else{  
        	mv.addObject("msg", "提交成功！");  
            orderRepository.saveAndFlush(order);
            List<Order> orders = orderRepository.findAll();
            mv.addObject("orders", orders);
            mv.setViewName("redirect:/orders");
        }  
		return mv;
	}

	@RequestMapping("/message")
	public String message(Model model) {
		model.addAttribute("message", "js message test");
		return "message";
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

}
