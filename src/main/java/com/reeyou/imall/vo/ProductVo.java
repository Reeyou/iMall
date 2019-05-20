package com.reeyou.imall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Reeyou
 * @data 2019/5/20 11:17
 */
@Data
public class ProductVo {
	private Integer id;
	private Integer categoryId;

	private String name;
	private String subtitle;
	private String mainImage;
	private BigDecimal price;

	private Integer status;

	private String imageHost;
}
