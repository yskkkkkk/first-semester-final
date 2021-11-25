package com.ssafy.happyhouse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	// endpoint를 정의
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/send");
	}

	@Override
	// CORS 허용
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/").setAllowedOriginPatterns("*").withSockJS();
	}
}
