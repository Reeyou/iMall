package com.reeyou.imall.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Reeyou
 * @data 2019/5/20 15:53
 */
@Getter
@Setter
public class ProductListVo {
	private Integer id;
	private Integer categoryId;

	private String name;
	private String categoryName;
	private Integer stock;
	private String subtitle;
	private String mainImage;
	private BigDecimal price;

	private Integer status;

	private String imageHost;

	private Date updateTime;
}
