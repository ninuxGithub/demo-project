package com.example.demo.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
* @ClassName: com.example.demo.bean.SelectItem 
* @Description: TODO(提供下拉列表的Bean)
* @date 2017年7月27日 上午9:21:18
* @param <K>
* @param <V> 
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectItem<K,V> implements Serializable{

	private static final long serialVersionUID = -4712137196048551168L;
	
	private K key;
	
	private V value;
	

}
