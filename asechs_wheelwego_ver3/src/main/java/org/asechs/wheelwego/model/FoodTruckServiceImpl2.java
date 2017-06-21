package org.asechs.wheelwego.model;

import java.util.List;

import javax.annotation.Resource;

import org.asechs.wheelwego.model.vo.BookingVO;
import org.asechs.wheelwego.model.vo.ListVO;
import org.asechs.wheelwego.model.vo.ReviewVO;
import org.asechs.wheelwego.model.vo.TruckVO;
import org.asechs.wheelwego.model.vo.WishlistVO;
import org.springframework.stereotype.Service;
/**
 * 본인 이름
 *  수정 날짜 (수정 완료)
 * 대제목[마이페이지/푸드트럭/멤버/게시판/예약] - 소제목
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
public class FoodTruckServiceImpl2 implements FoodTruckService {
	@Resource
	private FoodTruckDAO foodTruckDAO;

	@Override
	public String findFoodtruckNameByMenuId(String menuId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TruckVO> foodtruckList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TruckVO> searchFoodTruckList(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TruckVO> searchFoodTruckByGPS(TruckVO gpsInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TruckVO foodTruckAndMenuDetail(String foodtruckNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerBookMark(WishlistVO wishlistVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBookMarkCount(WishlistVO wishlistVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void registerReview(ReviewVO reviewVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ListVO getReviewListByTruckNumber(String reviewPageNo, String foodTruckNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListVO getFoodTruckListByName(String pageNo, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListVO getFoodTruckListByGPS(String pageNo, TruckVO gpsInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListVO filtering(String option, String name, String pageNo, String latitude, String longitude,
			TruckVO gpsInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void bookingMenu(BookingVO bookingVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getRecentlyBookingNumberBySellerId(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPreviousBookingNumberBySellerId(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getBookingStateBybookingNumber(String bookingNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getFoodtruckNumberList(TruckVO truckVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPreviousBookingNumberByCustomerId(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
