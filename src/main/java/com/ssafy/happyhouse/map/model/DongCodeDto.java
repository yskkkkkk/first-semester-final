package com.ssafy.happyhouse.map.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "DongCodeDto : 동 코드 정보", description = "동 코드 정보를 나타낸다.")
public class DongCodeDto {
	@ApiModelProperty(value = "동 코드")
	private String dongCode;
	@ApiModelProperty(value = "시 이름")
	private String sidoName;
	@ApiModelProperty(value = "구/군 이름")
	private String gugunName;
	@ApiModelProperty(value = "동 이름")
	private String dongName;
	public String getDongCode() {
		return dongCode;
	}
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	public String getSidoName() {
		return sidoName;
	}
	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}
	public String getGugunName() {
		return gugunName;
	}
	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}
	public String getDongName() {
		return dongName;
	}
	public void setDongName(String dongName) {
		this.dongName = dongName;
	}
	
	
}