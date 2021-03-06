package com.ssafy.happyhouse.map.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ssafy.happyhouse.map.mapper.HouseMapMapper;
import com.ssafy.happyhouse.map.model.DongCodeDto;
import com.ssafy.happyhouse.map.model.HouseDealDto;
import com.ssafy.happyhouse.map.model.HouseInfoDto;
import com.ssafy.happyhouse.map.model.SidoGugunCodeDto;

@Service
public class HouseMapServiceImpl implements HouseMapService {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getGugunInSido(sido);
	}

	@Override
	public List<HouseInfoDto> getDongInGugun(String gugun) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getDongInGugun(gugun);
	}

	@Override
	public List<HouseInfoDto> getAptInDong(String dong) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getAptInDong(dong);
	}
	
	@Override
	public ArrayList<HouseDealDto> searchList(Map<String, String> map) throws Exception {
		if (map.get("key").equals("dong")) map.put("key", "dongName");
		else map.put("key", "aptName");
		
		if (map.get("order").equals("new")) map.put("order", "hd.dealMonth");
		else if (map.get("order").equals("price")) map.put("order", "hd.dealAmount");
		else if (map.get("order").equals("area")) map.put("order", "(hd.area + 0.0) desc");
		return sqlSession.getMapper(HouseMapMapper.class).searchList(map);
	}
	
	@Override
	public List<DongCodeDto> getDongCode(String dongName) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getDongCode(dongName);
	}

	@Override
	public List<HouseDealDto> getAllAptInfo(String dong) throws Exception {
	      // Open API?????? ???????????? ????????? List??? ?????????
	      List<HouseDealDto> list = new ArrayList<HouseDealDto>();
	      
	      String url = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
	      String serviceKey = "E5%2B0gyiaa1UKkmGXe1aYtM9emgWtG5w8iGd3ocgj4kHr47TcfhXB%2BatWaWNTR6IaObMD7kUG%2Bu8MGI7fUanF8A%3D%3D";
	      String pageNo = "1";
	      String numOfRows="50";
	      String LAWD_CD= dong.substring(0,5);
	      String DEAL_YMD = "202110";
	      
	      Document documentInfo = DocumentBuilderFactory
	            .newInstance()
	            .newDocumentBuilder()
	            .parse(url+"?serviceKey="+serviceKey+"&pageNo="+pageNo+"&numOfRows="+numOfRows+"&LAWD_CD="+LAWD_CD+"&DEAL_YMD="+DEAL_YMD);
	      
	      // root tag
	      documentInfo.getDocumentElement().normalize();
	      //System.out.println("root?????? : "documentInfo.getDocumentElement().getNodeName()); // response
	      
	      // ????????? tag
	      NodeList nList = documentInfo.getElementsByTagName("item");
	      //System.out.println("????????? ???????????? ??? : "+nList.getLength());
	      
	      for(int temp=0; temp<nList.getLength(); temp++) {
	         Node nNode = nList.item(temp);
	         if(nNode.getNodeType() == Node.ELEMENT_NODE) {
	            Element eElement = (Element) nNode;
	            //System.out.println(eElement.getTextContent());
	            String aptCode = getTagValue("????????????", eElement);
	            String aptName = getTagValue("?????????", eElement);
	            String dongName = getTagValue("?????????", eElement);
	            String dongCode = getTagValue("????????????????????????", eElement);
	            String buildYear = getTagValue("????????????", eElement);
	            String jibun = getTagValue("??????", eElement);
	            String address = getTagValue("?????????", eElement)+" "+getTagValue("?????????", eElement)+" "+getTagValue("??????????????????????????????", eElement);
	            String price = getTagValue("????????????", eElement);
	            String year = getTagValue("???", eElement);
	            String month = getTagValue("???", eElement);
	            String day = getTagValue("???", eElement);
	            String floor = getTagValue("???", eElement);
	            String area = getTagValue("????????????", eElement);
	            
	            Random rand = new Random();
	            String img = Integer.toString(rand.nextInt(13) + 1);
	            // System.out.println(aptName);
	            
	            HouseInfoDto houseinfo = new HouseInfoDto();
	            houseinfo.setAptCode(aptCode);
	            houseinfo.setDongName(dongName);
	            houseinfo.setAptName(aptName);
	            houseinfo.setDongCode(dongCode);
	            houseinfo.setBuildYear(Integer.parseInt(buildYear));
	            houseinfo.setJibun(jibun);
	            houseinfo.setAddress(address);
	            houseinfo.setRecentPrice(price);
	            houseinfo.setImg(img);
	            
	            list.add(
	            		new HouseDealDto(aptCode, price, Integer.parseInt(year), month, day, area, floor,houseinfo));
	         }
	      }
	      
	      return list;
	   }

	   // Tag??? ???????????? ???????????? ?????????
	   private static String getTagValue(String tag, Element eElement) {
	      Node temp = eElement.getElementsByTagName(tag).item(0);
	      if(temp==null) return null; // ??????????????? ?????? ?????? return null
	      
	      NodeList nList = temp.getChildNodes();
	      Node nValue = (Node)nList.item(0);
	      
	      if(nValue==null) return null;
	      return nValue.getNodeValue();
	   }
}
