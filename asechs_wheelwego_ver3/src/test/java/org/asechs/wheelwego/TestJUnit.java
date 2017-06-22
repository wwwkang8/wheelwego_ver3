package org.asechs.wheelwego;

import javax.annotation.Resource;

import org.asechs.wheelwego.model.FoodTruckDAO;
import org.asechs.wheelwego.model.MemberService;
import org.asechs.wheelwego.model.vo.PagingBean;
import org.asechs.wheelwego.model.vo.TruckVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring-model.xml"})
public class TestJUnit {
/*   @Resource(name="foodTruckServiceImpl2")
   private MemberService service;*/
   @Resource
   private FoodTruckDAO foodTruckDAO;
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
	  //System.out.println(service.findFoodTruckNumberById("seller01"));
	   TruckVO gpsInfo = new TruckVO();
	   gpsInfo.setLatitude(37.4060784);
	   gpsInfo.setLongitude(127.1163916);
	   int totalCount=foodTruckDAO.getTruckListTotalContentCountByGPS(gpsInfo);
 	   PagingBean pagingbean = new PagingBean(Integer.parseInt("1"),totalCount);
	   System.out.println(foodTruckDAO.filteringByDate(pagingbean));
   }
}