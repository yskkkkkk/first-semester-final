package com.ssafy.happyhouse.board.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ReplyDto : 댓글정보", description = "게시룸하위 댓글정보를 나타낸다.")
public class ReplyDto {

	@ApiModelProperty(value = "댓글 일련번호")
	private int replyNo;
	@ApiModelProperty(value = "게시물 일련번호")
	private int boardNo;
	@ApiModelProperty(value = "내용")
	private String content;
	@ApiModelProperty(value = "작성자")
	private String writer;
	@ApiModelProperty(value = "추천 수")
	private String recommand;
	@ApiModelProperty(value = "등록 일시")
	private String regTime;
	@ApiModelProperty(value = "수정 일시")
	private String modTime;
	
	public ReplyDto() {}
	public ReplyDto(int replyNo, String recommand) {
		super();
		this.replyNo = replyNo;
		this.recommand = recommand;
	}
	public ReplyDto(int replyNo, int boardNo, String content, String writer, String recommand, String regTime,
			String modTime) {
		super();
		this.replyNo = replyNo;
		this.boardNo = boardNo;
		this.content = content;
		this.writer = writer;
		this.recommand = recommand;
		this.regTime = regTime;
		this.modTime = modTime;
	}

	public int getReplyNo() {
		return replyNo;
	}

	public void setReplyNo(int replyNo) {
		this.replyNo = replyNo;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getRecommand() {
		return recommand;
	}

	public void setRecommand(String recommand) {
		this.recommand = recommand;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public String getModTime() {
		return modTime;
	}

	public void setModTime(String modTime) {
		this.modTime = modTime;
	}

	@Override
	public String toString() {
		return "ReplyDto [replyNo=" + replyNo + ", boardNo=" + boardNo + ", content=" + content + ", writer=" + writer
				+ ", recommand=" + recommand + ", regTime=" + regTime + ", modTime=" + modTime + "]";
	}
}
