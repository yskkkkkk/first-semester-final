package com.ssafy.happyhouse.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="WorldDto : 관심단어", description = "관심 단어와 비중을 가진 domain class입니다.")
public class WordDto {

	@ApiModelProperty(value = "관심 단어")
	private String word;

	@ApiModelProperty(value = "관심 비중")
	private double weight;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
