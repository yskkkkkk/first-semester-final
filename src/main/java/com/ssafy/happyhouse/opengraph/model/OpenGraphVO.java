package com.ssafy.happyhouse.opengraph.model;

import lombok.Data;

@Data
public class OpenGraphVO {
	private String title;
	private String description;
	private String type;
	private String url;
	private String image;
	private String author;
}