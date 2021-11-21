package com.ssafy.happyhouse.map.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.map.model.HouseDealDto;
import com.ssafy.happyhouse.map.model.HouseInfoDto;
import com.ssafy.happyhouse.map.model.SidoGugunCodeDto;

public interface HouseMapService {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<HouseInfoDto> getDongInGugun(String gugun) throws Exception;
	List<HouseInfoDto> getAptInDong(String dong) throws Exception;
	List<HouseDealDto> getAllAptInfo(String dong) throws Exception;
	ArrayList<HouseDealDto> searchList(Map<String, String> map) throws Exception;
}
