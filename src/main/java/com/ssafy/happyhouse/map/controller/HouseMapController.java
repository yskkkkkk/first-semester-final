package com.ssafy.happyhouse.map.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.map.model.HouseDealDto;
import com.ssafy.happyhouse.map.model.HouseInfoDto;
import com.ssafy.happyhouse.map.model.SidoGugunCodeDto;
import com.ssafy.happyhouse.map.service.HouseMapService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/map")
@Api("Map 컨트롤러  API V1")
public class HouseMapController {
	
	private final Logger logger = LoggerFactory.getLogger(HouseMapController.class);

	@Autowired
	private HouseMapService haHouseMapService;
	
	@ApiOperation(value = "시도 정보", notes = "전국의 시도를 반환한다.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		logger.info("sido - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getSido(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "구군 정보", notes = "전국의 구군을 반환한다.", response = List.class)
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(@RequestParam("sido") @ApiParam(value = "시도코드.", required = true) String sido) throws Exception {
		logger.info("gugun - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getGugunInSido(sido), HttpStatus.OK);
	}

	@ApiOperation(value = "동 정보", notes = "전국의 동을 반환한다.", response = List.class)
	@GetMapping("/dong")
	public ResponseEntity<List<HouseInfoDto>> dong(@RequestParam("gugun") String gugun) throws Exception {
		logger.info("dong - 호출");
		System.out.println(gugun);
		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getDongInGugun(gugun), HttpStatus.OK);
	}

	@ApiOperation(value = "아파트 정보", notes = "전국의 아파트를 반환한다.", response = List.class)
	@GetMapping("/apt")
	public ResponseEntity<List<HouseInfoDto>> apt(@RequestParam("dong") String dong) throws Exception {
		logger.info("apt - 호출");
		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getAptInDong(dong), HttpStatus.OK);
	}
	
	@ApiOperation(value = "search", notes = "조건에 맞는 아파트 리스트를 반환합니다.")
	@PostMapping("/search")
	public ResponseEntity<List<HouseDealDto>> search(@RequestBody Map<String, String> map) {
		System.out.println(map);
		String key = map.get("key");
		String value = map.get("value");
		String order = map.get("order");
		System.out.println(key+" "+value+" "+order);
		ArrayList<HouseDealDto> list = new ArrayList<HouseDealDto>();
		
		try {
			list = (ArrayList<HouseDealDto>) haHouseMapService.searchList(map);
			System.out.println(list);
			 
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<HouseDealDto>>(list, HttpStatus.INTERNAL_SERVER_ERROR);	// 500
		}
		
		if (list.isEmpty()) {
			return new ResponseEntity<List<HouseDealDto>>(list, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<HouseDealDto>>(list, HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "아파트 정보", notes = "api를 이용해 아파트를 호출한다.", response = List.class)
	@GetMapping("/getapt")
	public ResponseEntity<List<HouseDealDto>> getAllAptInfo(@RequestParam("dong") String dong) throws Exception {
		logger.info("apt - 호출");
		return new ResponseEntity<List<HouseDealDto>>(haHouseMapService.getAllAptInfo(dong), HttpStatus.OK);
	}
}
