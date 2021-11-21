package com.ssafy.happyhouse.map.mapper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.map.model.HouseDealDto;
import com.ssafy.happyhouse.map.model.HouseInfoDto;
import com.ssafy.happyhouse.map.model.SidoGugunCodeDto;

@Mapper
public interface HouseMapMapper {

	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	List<HouseInfoDto> getDongInGugun(String gugun) throws SQLException;
	List<HouseInfoDto> getAptInDong(String dong) throws SQLException;
	ArrayList<HouseDealDto> searchList(Map<String, String> map) throws SQLException;
}
