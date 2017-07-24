package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.bean.Order;
import com.example.demo.repository.OrderRepository;

@Controller
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;

	@RequestMapping("/orders")
	public String loadOrder(Model model) {
		List<Order> orderList = orderRepository.findAll();
		model.addAttribute("orders", orderList);
		return "orders";
	}

}
