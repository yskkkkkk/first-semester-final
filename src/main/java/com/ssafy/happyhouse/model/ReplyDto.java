package com.ssafy.happyhouse.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ReplyDto : 댓글 정보", description = "댓글의 상세 정보를 나타낸다.")
public class ReplyDto {
	@ApiModelProperty(value = "댓글 일련번호")
	private int replyNo;
	@ApiModelProperty(value = "글 일련번호")
	private int boardNo;
	@ApiModelProperty(value = "내용")
	private String content;
	@ApiModelProperty(value = "작성자")
	private String writer;
	@ApiModelProperty(value = "추천 수")
	private int recommand;
	@ApiModelProperty(value = "등록 일시")
	private String regTime;
	@ApiModelProperty(value = "수정 일시")
	private String modTime;
	
	
}
