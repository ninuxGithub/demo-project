package com.example.demo.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Order implements Serializable {

	private static final long serialVersionUID = -1835021583969266196L;

	//@GenericGenerator(name = "system-uuid", strategy = "uuid")//　声明一个策略通用生成器，name为"system-uuid",策略strategy为"uuid"。
	//@GeneratedValue(generator = "system-uuid")// 用generator属性指定要使用的策略生成器。
	//适合String 类型的ID
	
	/**
	 * 订单ID
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long orderId;

	/**
	 * 产品名称
	 */
	private String produceName;

	/**
	 * 生产日期
	 */
	private Date productDate;

	/**
	 * 保质期
	 */
	private Date qualityGuaranteePeriod;

	/**
	 * 库存
	 */
	private Integer stockAmount;

}
