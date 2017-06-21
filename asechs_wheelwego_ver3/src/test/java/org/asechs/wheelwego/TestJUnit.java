package org.asechs.wheelwego;

import javax.annotation.Resource;

import org.asechs.wheelwego.model.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring-model.xml"})
public class TestJUnit {
   @Resource(name="memberServiceImpl")
   private MemberService service;
/*   @Resource
   private FoodTruckService foodtruckService;
   @Resource
   private BoardDAO boardDAO;
   @Resource
   private FoodTruckDAO foodTruckDAO;
   @Resource
   private MypageDAO mypageDAO;
   @Resource
   private MypageService mypageService;*/

   @Test
   public void test(){
	  //System.out.println(foodtruckService.findFoodtruckNameByMenuId("75"));
	  //System.out.println(foodtruckService.getPreviousBookingNumberByCustomerId("java"));
	  System.out.println(service.findFoodTruckNumberById("seller01"));
   }
}