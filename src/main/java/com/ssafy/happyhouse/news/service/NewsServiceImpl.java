package com.ssafy.happyhouse.news.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import com.ssafy.happyhouse.news.model.NewsDto;


@Service
public class NewsServiceImpl implements NewsService {

	@Override
	public List<NewsDto> getNewsList() throws Exception {

		List<NewsDto> result = new ArrayList<NewsDto>();
		int no = 1;
		// Jsoup를 이용해서 naver news 크롤링
		
		String[] keywords = {"아파트 주목", "아파트 공급", "사전 청약 시작"};
		for (String keyword : keywords) {
			String url = "https://search.naver.com/search.naver?where=news&sm=tab_jum&query=" 
					+ URLEncoder.encode(keyword, "UTF-8"); //크롤링할 url
			Document doc = null;        //페이지의 전체 소스
			
			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				e.printStackTrace();
			}

			/*
			 *  # 컨테이너 선택자 : ul.list_news > li
			    # title - 기사 제목 선택자 : a.news_tit ['title']
			    # writer - 기사 언론사 선택자 : a.info.press .text
			    # content - 기사 내용 : a.api_txt_lines .text
			    # url - 기사 url 선택: a.news_tit ['href']
			 * */
			
			//select를 이용하여 원하는 태그를 선택한다. select는 원하는 값을 가져오기 위한 중요한 기능이다.
			Elements articles = doc.select("ul.list_news > li");    

			//Iterator을 사용하여 하나씩 값 가져오기
			Iterator<Element> title = articles.select("a.news_tit").iterator();
			Iterator<Element> writer = articles.select("a.info.press").iterator();
			Iterator<Element> content = articles.select("a.api_txt_lines").iterator();
			Iterator<Element> uri = articles.select("a.news_tit").iterator();
			Iterator<Element> img = articles.select("img.thumb.api_get").iterator();
			
			while (title.hasNext()) {
				if (img.hasNext()) {
					result.add(new NewsDto(no++, 
							title.next().text(),
							writer.next().text(),
							content.next().text(), 
							uri.next().attr("href"), 
							img.next().attr("src")
					));
				} else {
					result.add(new NewsDto(no++, 
							title.next().text(),
							writer.next().text(),
							content.next().text(), 
							uri.next().attr("href"), 
							""
					));
				}	
			}
		}
		return result;
	}

}
