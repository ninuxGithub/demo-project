package com.example.demo.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/** 
* @ClassName: com.example.demo.bean.ResponseEntity 
* @Description: TODO(--)
* @date 2017年7月27日 上午9:20:45 
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8164976318964008464L;

	/**
	 * 响应代码
	 */
	private int code;

	/**
	 * 是否成功返回结果
	 */
	private boolean success;

	/**
	 * 返回消息
	 */
	private String message;

	/**
	 * 响应的数据
	 */
	private Object responseData;

}
