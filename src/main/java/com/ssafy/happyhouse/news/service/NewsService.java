package com.ssafy.happyhouse.news.service;

import java.util.List;

import com.ssafy.happyhouse.news.model.NewsDto;

public interface NewsService {
	List<NewsDto> getNewsList() throws Exception;
}
