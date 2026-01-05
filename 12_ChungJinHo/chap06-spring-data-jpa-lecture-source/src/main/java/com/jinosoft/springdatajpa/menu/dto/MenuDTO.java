package com.jinosoft.springdatajpa.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {
	
	private int menuCode;
	private String menuName;
	private int menuPrice;
	private int categoryCode;
	private String orderableStatus;

}