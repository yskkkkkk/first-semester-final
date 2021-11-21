package com.ssafy.happyhouse.map.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HouseDealDto : 아파트 매매정보", description = "아파트의 매매 정보를 나타낸다.")
public class HouseDealDto {
	@ApiModelProperty(value = "아파트 매매 일련번호")
	private int dealNo;
	@ApiModelProperty(value = "아파트일련번호")
	private String aptCode;
	@ApiModelProperty(value = "매매 가격")
	private String dealAmount;
	@ApiModelProperty(value = "년도")
	private int dealYear;
	@ApiModelProperty(value = "월")
	private String dealMonth;
	@ApiModelProperty(value = "일")
	private String dealDay;
	@ApiModelProperty(value = "면적")
	private String area;
	@ApiModelProperty(value = "층 수")
	private String floor;
	@ApiModelProperty(value = "매매 / 전월세")
	private String type;
	@ApiModelProperty(value = "월세")
	private String rentMoney;
	
	private HouseInfoDto houseinfo;
	
	public HouseDealDto(String aptCode, String dealAmount, int dealYear, String dealMonth, String dealDay, String area,
			String floor, HouseInfoDto houseinfo) {
		super();
		this.aptCode = aptCode;
		this.dealAmount = dealAmount;
		this.dealYear = dealYear;
		this.dealMonth = dealMonth;
		this.dealDay = dealDay;
		this.area = area;
		this.floor = floor;
		this.houseinfo = houseinfo;
	}
	public HouseInfoDto getHouseinfo() {
		return houseinfo;
	}
	public void setHouseinfo(HouseInfoDto houseinfo) {
		this.houseinfo = houseinfo;
	}
	public int getDealNo() {
		return dealNo;
	}
	public void setDealNo(int dealNo) {
		this.dealNo = dealNo;
	}
	public String getAptCode() {
		return aptCode;
	}
	public void setAptCode(String aptCode) {
		this.aptCode = aptCode;
	}
	public String getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}
	public int getDealYear() {
		return dealYear;
	}
	public void setDealYear(int dealYear) {
		this.dealYear = dealYear;
	}
	public String getDealMonth() {
		return dealMonth;
	}
	public void setDealMonth(String dealMonth) {
		this.dealMonth = dealMonth;
	}
	public String getDealDay() {
		return dealDay;
	}
	public void setDealDay(String dealDay) {
		this.dealDay = dealDay;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRentMoney() {
		return rentMoney;
	}
	public void setRentMoney(String rentMoney) {
		this.rentMoney = rentMoney;
	}
	@Override
	public String toString() {
		return "HouseDealDto [dealNo=" + dealNo + ", aptCode=" + aptCode + ", dealAmount=" + dealAmount + ", dealYear="
				+ dealYear + ", dealMonth=" + dealMonth + ", dealDay=" + dealDay + ", area=" + area + ", floor=" + floor
				+ ", type=" + type + ", rentMoney=" + rentMoney + ", houseinfo=" + houseinfo + "]";
	}
	
}
