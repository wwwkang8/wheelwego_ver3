package org.asechs.wheelwego.model;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
/**
 * 본인 이름
 *  수정 날짜 (수정 완료)
 * 대제목[마이페이지/푸드트럭/멤버/게시판] - 소제목
 * ------------------------------------------------------
 * 코드설명
 * 
 * EX)
	박다혜
	 2017.06.21 (수정완료) / (수정중)
 	마이페이지 - 마이트럭설정
	---------------------------------
	~~~~~
  */
@Service
public class MypageServiceImpl2 /*implements MypageService*/ {
   @Resource
   private MypageDAO mypageDAO;
   @Resource
   private FoodTruckDAO foodtruckDAO;
  
}