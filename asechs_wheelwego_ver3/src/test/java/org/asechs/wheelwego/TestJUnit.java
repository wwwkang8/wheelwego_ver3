package org.asechs.wheelwego;

import javax.annotation.Resource;

import org.asechs.wheelwego.model.BoardDAO;
import org.asechs.wheelwego.model.FoodTruckDAO;
import org.asechs.wheelwego.model.FoodTruckService;
import org.asechs.wheelwego.model.MemberService;
import org.asechs.wheelwego.model.MypageDAO;
import org.asechs.wheelwego.model.MypageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring-model.xml"})
public class TestJUnit {
   @Resource
   private MemberService service;
   @Resource
   private FoodTruckService foodtruckService;
   @Resource
   private BoardDAO boardDAO;
   @Resource
   private FoodTruckDAO foodTruckDAO;
   @Resource
   private MypageDAO mypageDAO;
   @Resource
   private MypageService mypageService;

   @Test
   public void test(){
	  //System.out.println(foodtruckService.findFoodtruckNameByMenuId("75"));
	  System.out.println(foodtruckService.getPreviousBookingNumberByCustomerId("java"));
   }
}