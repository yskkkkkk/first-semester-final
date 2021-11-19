package com.ssafy.happyhouse.news.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "NewsDto : 뉴스정보", description = "뉴스 나타낸다.")
public class NewsDto {

	@ApiModelProperty(value = "뉴스일련번호")
	private int newsNo;
	@ApiModelProperty(value = "뉴스제목")
	private String title;
	@ApiModelProperty(value = "언론사")
	private String media;
	@ApiModelProperty(value = "내용")
	private String content;
	@ApiModelProperty(value = "링크")
	private String url;
	
	public NewsDto() {
		// TODO Auto-generated constructor stub
	}

	public NewsDto(int newsNo, String title, String media, String content, String url) {
		super();
		this.newsNo = newsNo;
		this.title = title;
		this.media = media;
		this.content = content;
		this.url = url;
	}

	public int getNewsNo() {
		return newsNo;
	}

	public void setNewsNo(int newsNo) {
		this.newsNo = newsNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "NewsDto [newsNo=" + newsNo + ", title=" + title + ", media=" + media + ", content=" + content + ", url="
				+ url + "]";
	}
}
