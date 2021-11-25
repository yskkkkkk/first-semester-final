package com.ssafy.happyhouse.chat.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data

@RequiredArgsConstructor
public class ChatDto {
	private int chatNo;
	private String userName;
	private String content;
	private String time;
}
