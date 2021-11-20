package com.ssafy.happyhouse.board.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "BoardDto : 게시글정보", description = "게시글의 상세 정보를 나타낸다.")
public class BoardDto {
	@ApiModelProperty(value = "글 일련번호")
	private int boardNo;
	@ApiModelProperty(value = "제목")
	private String title;
	@ApiModelProperty(value = "내용")
	private String content;
	@ApiModelProperty(value = "작성자")
	private String writer;
	@ApiModelProperty(value = "조회수")
	private int readCount;
	@ApiModelProperty(value = "공개여부")
	private String isExposing;
	@ApiModelProperty(value = "공지사항 여부")
	private String isNotice;
	@ApiModelProperty(value = "등록 일시")
	private String regTime;
	@ApiModelProperty(value = "수정 일시")
	private String modTime;
	
	public BoardDto() {}
	public BoardDto(int boardNo, int readCount) {
		super();
		this.boardNo = boardNo;
		this.readCount = readCount;
	}
	public BoardDto(int boardNo, String title, String content, String writer, int readCount, String isExposing,
			String isNotice, String regTime, String modTime) {
		super();
		this.boardNo = boardNo;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.readCount = readCount;
		this.isExposing = isExposing;
		this.isNotice = isNotice;
		this.regTime = regTime;
		this.modTime = modTime;
	}

	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public String getIsExposing() {
		return isExposing;
	}
	public void setIsExposing(String isExposing) {
		this.isExposing = isExposing;
	}
	public String getIsNotice() {
		return isNotice;
	}
	public void setIsNotice(String isNotice) {
		this.isNotice = isNotice;
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
		return "BoardDto [boardNo=" + boardNo + ", title=" + title + ", content=" + content + ", writer=" + writer
				+ ", readCount=" + readCount + ", isExposing=" + isExposing + ", isNotice=" + isNotice + ", regTime="
				+ regTime + ", modTime=" + modTime + "]";
	}
}