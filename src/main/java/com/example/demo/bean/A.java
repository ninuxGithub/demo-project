package com.example.demo.bean;

import java.io.Serializable;
import java.util.List;

public class A implements Serializable {

	private static final long serialVersionUID = 4485825936024066780L;

	private Integer id;

	private List<B> list;

	public A() {
	}

	public A(Integer id, List<B> list) {
		super();
		this.id = id;
		this.list = list;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<B> getList() {
		return list;
	}

	public void setList(List<B> list) {
		this.list = list;
	}
}

class B implements Serializable {
	private static final long serialVersionUID = 2664881258245430500L;

	private String name;

	private String address;

	public B() {
	}

	public B(String name, String address) {
		super();
		this.name = name;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
