package org.asechs.wheelwego;

import java.util.List;

import javax.annotation.Resource;

import org.asechs.wheelwego.model.MypageService;
//github.com/parkdahye/wheelwego_ver3.git
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring-model.xml"})
public class TestJUnit {
/*   @Resource(name="memberServiceImpl")
   private MemberService service;*/
/*   @Resource
   private FoodTruckService foodtruckService;
   @Resource
   private BoardDAO boardDAO;
   @Resource
   private FoodTruckDAO foodTruckDAO;
   @Resource
   private MypageDAO mypageDAO;*/
   @Resource(name="mypageServiceImpl")
   private MypageService mypageService;

   @Test
   public void test(){
	   List<String> list =  mypageService.findMenuIdByBookingNumber("48");
	   //System.out.println(list);
	   for(int i=0; i<list.size(); i++){
		   String foodtruckNumber = mypageService.findFoodTruckNumberByMenuId(list.get(i));
		   System.out.println(foodtruckNumber);
		   String foodtruckName = mypageService.findFoodtruckNameByFoodTruckNumber(foodtruckNumber);
		   System.out.println(foodtruckName);
	   }
   }
}