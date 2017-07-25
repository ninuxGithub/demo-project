package com.example.demo.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectItem<K,V> implements Serializable{

	private static final long serialVersionUID = -4712137196048551168L;
	
	private K key;
	
	private V value;
	

}
