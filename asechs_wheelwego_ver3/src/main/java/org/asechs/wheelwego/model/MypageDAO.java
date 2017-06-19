package org.asechs.wheelwego.model;

import java.util.HashMap;
import java.util.List;


import org.asechs.wheelwego.model.vo.BookingDetailVO;

import org.asechs.wheelwego.model.vo.BoardVO;

import org.asechs.wheelwego.model.vo.BookingVO;
import org.asechs.wheelwego.model.vo.FileVO;
import org.asechs.wheelwego.model.vo.FoodVO;
import org.asechs.wheelwego.model.vo.PagingBean;
import org.asechs.wheelwego.model.vo.PointVO;
import org.asechs.wheelwego.model.vo.ReviewVO;
import org.asechs.wheelwego.model.vo.TruckVO;
import org.asechs.wheelwego.model.vo.WishlistVO;

public interface MypageDAO {
	List<BookingVO> customerBookingList(PagingBean pagingBean);
	
	public List<BookingVO> getBookingList(int bookingNumber);
	
	public void minusPoint(HashMap<String, Integer> pointInfo);

	 public void addPoint(HashMap<String, Object> pointInfo);
	
	public int getMyPoint(String customerId);
	
	List<WishlistVO> heartWishList(String id);
	
	public List<TruckVO> myWishList(String id);
	
	public void deleteWishList(WishlistVO wishlistVO);

	void registerFoodtruck(TruckVO tvo);
	
	TruckVO findtruckInfoByTruckNumber(String truckNumber); //트럭번호로 트럭정보찾기

	String findtruckNumberBySellerId(String sellerId); //아이디로 트럭넘버 찾기
	
	void saveFilePath(FileVO fileVO); //파일경로저장하기

	void updateMyfoodtruck(TruckVO truckVO); //푸드트럭 설정 수정

	void updateFilePath(FileVO fileVO); //파일경로 수정

	List<FoodVO> showMenuList(String truckNumber); //트럭넘버에 해당하는 메뉴리스트 보기

	void deleteAllMenu(String truckNumber); //트럭넘버에 해당하는 모든 메뉴 삭제하기

	void deleteMenu(String menuId); //메뉴아이디에 해당하는 메뉴지우기
	
	void registerMenu(FoodVO foodVO); //메뉴등록하기
	
	void updateMenu(FoodVO foodVO); //메뉴 수정하기
	
	FoodVO findMenuByMenuId(String menuId);
	
	void updateMenuFilepath(FileVO fileVO);

	void deleteMyTruck(String foodtruckNumber);
	
	List<ReviewVO> showMyReviewList(PagingBean pagingBean); //리뷰 리스트보기
	
	void updateMyReview(ReviewVO reviewVO); //리뷰수정하기
	
	void deleteMyReview(String reviewNo); //리뷰 삭제하기

	public ReviewVO findReviewInfoByReviewNo(String reviewNo);

	public TruckVO getGPSInfo(String sellerId);

	public void stayFoodtruck(TruckVO gpsInfo);

	public void leaveFoodtruck(TruckVO gpsInfo);

	public int getWishListTotalContentCount(String id);

	public List<TruckVO> getWishList(PagingBean pagingBean);

	public int getTotalReviewCount(String customerId);
	
	public int getWishListFlag(WishlistVO wishlistVO);

	int getTotalFreeboardCount(String id);

	void updateBookingState(BookingVO bookingVO);
	
	List<BookingVO> getBookingVO(String foodTruckNumber);

	List<BookingVO> getBookingVO(PagingBean pagingBean);

	List<BookingDetailVO> getBookingDetailVO(BookingVO bookingVO);

	List<BoardVO> showMyContentByFreeList(PagingBean pagingBean);

	void freeboardDeleteInMaypage(String contentNo);

	//List<BookingVO> getSellerBookingListByTruckNumber(String foodTruckNumber);

	int getTotalbusinessCount(String id);

	List<BoardVO> showMyContentBybusinessList(PagingBean pagingBean);

	void businessDeleteInMaypage(String contentNo);

	int getTotalqnaCount(String id);

	List<BoardVO> showMyContentByqnaList(PagingBean pagingBean);

	void qnaDeleteInMaypage(String contentNo);
	
	public String getBookingNumberByCustomerId(String id);

	int checkBookingState(String customerId);

	int getTotalBookingCount(String foodTruckNumber);

	int getTotalPointCountById(String id);

	List<PointVO> getPointListById(PagingBean pagingBean);
	
	public int getCustomerBookingListCount(String customerId);


}
