package com.ssafy.happyhouse.socket.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ssafy.happyhouse.socket.model.SocketDto;

@Controller
@CrossOrigin("*")
public class SocketController {

	public static final Logger logger = LoggerFactory.getLogger(SocketController.class);

	@MessageMapping("/receive")
	@SendTo("/send")
	// SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보냄
	// 정의한 SocketDto를 1) 인자값, 2) 반환값으로 사용
	public SocketDto SocketHandler(SocketDto socket) {
		String userName = socket.getUserName();
		String content = socket.getContent();

		logger.info(userName+"님의 messege: "+content);
		SocketDto result = new SocketDto(userName, content);
		return result;
	}
}
