package com.ssafy.happyhouse.opengraph.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.happyhouse.opengraph.lib.OpenGraph;
import com.ssafy.happyhouse.opengraph.model.OpenGraphVO;

@Controller
public class OpenGraphController {
	
	@ResponseBody
	@GetMapping("/getOpenGraph")
	public OpenGraphVO getOpenGraph(@RequestParam("param") String param) {
		System.out.println(param);
		OpenGraphVO ogVO = null;
		
		try {
			OpenGraph page = new OpenGraph(param, true);
			//MetaElement[] meta = page.getProperties();
			
			ogVO = new OpenGraphVO();
			ogVO.setTitle(getContent(page, "title"));
			ogVO.setDescription(getContent(page, "description"));
			ogVO.setImage(getContent(page, "image"));
			ogVO.setType(getContent(page, "type"));
			ogVO.setUrl(getContent(page, "url"));
			ogVO.setAuthor(getContent(page, "article:author"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(ogVO);
		return ogVO;
	}
	
	private String getContent(OpenGraph page, String propertyName) {
		try {
			return page.getContent(propertyName);
		} catch (NullPointerException e) {
			return "태그 없음";
		}
	}
}
