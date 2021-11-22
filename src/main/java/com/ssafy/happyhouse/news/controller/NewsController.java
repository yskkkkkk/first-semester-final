package com.ssafy.happyhouse.news.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.map.controller.HouseMapController;
import com.ssafy.happyhouse.news.model.NewsDto;
import com.ssafy.happyhouse.news.service.NewsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/news")
@Api("Map 컨트롤러  API V1")
public class NewsController {
	
	private final Logger logger = LoggerFactory.getLogger(HouseMapController.class);

	@Autowired
	private NewsService newsService;
	
	@ApiOperation(value = "뉴스 정보", notes = "최신 뉴스를 크롤링해서 json으로 반환한다.", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<NewsDto>> getNewsList() throws Exception {
		logger.info("newslist - 호출");
		return new ResponseEntity<List<NewsDto>>(newsService.getNewsList(), HttpStatus.OK);
	}
}
