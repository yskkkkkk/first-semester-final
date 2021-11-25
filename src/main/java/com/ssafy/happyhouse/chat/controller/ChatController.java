package com.ssafy.happyhouse.chat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ssafy.happyhouse.chat.model.ChatDto;
import com.ssafy.happyhouse.chat.service.ChatService;

@Controller
@CrossOrigin("*")
public class ChatController {

	public static final Logger logger = LoggerFactory.getLogger(ChatController.class);

	@Autowired
	private ChatService chatService;
	
	@MessageMapping("/receive")
	@SendTo("/send")
	// SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보냄
	// 정의한 SocketDto를 1) 인자값, 2) 반환값으로 사용
	public ChatDto SocketHandler(ChatDto chat) {
		String userName = chat.getUserName();
		String content = chat.getContent();

		logger.info(userName+"님의 messege: "+content);

		chatService.saveChat(chat);
		ChatDto result = new ChatDto();
		result.setUserName(userName);
		result.setContent(content);
		return result;
	}
}
