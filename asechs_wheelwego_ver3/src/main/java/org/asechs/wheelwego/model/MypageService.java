package org.asechs.wheelwego.model;

import java.util.List;

import org.asechs.wheelwego.model.vo.BookingDetailVO;
import org.asechs.wheelwego.model.vo.BookingVO;
import org.asechs.wheelwego.model.vo.FoodVO;
import org.asechs.wheelwego.model.vo.ListVO;
import org.asechs.wheelwego.model.vo.ReviewVO;
import org.asechs.wheelwego.model.vo.TruckVO;
import org.asechs.wheelwego.model.vo.WishlistVO;

public interface MypageService {
	ListVO customerBookingList(String pageNo, String customerId);
	
	List<BookingVO> getBookingList(int bookingNumber);
	
    public void calPoint(String usePoint, String totalAmount, int bookingNumber);

	public void minusPoint(int usePoint, int bookingNumber);
	   
	public void addPoint(int totalAmount, int bookingNumber);
	   
	public int getMyPoint(String customerId);
	
	List<WishlistVO> heartWishList(String id);
	
	public List<TruckVO> myWishList(String id);
	
	public void deleteWishList(WishlistVO wishlistVO);
	
	void registerFoodtruck(TruckVO tvo, String uploadPath);  //푸드트럭 등록하기 -트럭정보 저장 및 파일경로 저장

	TruckVO findtruckInfoByTruckNumber(String truckNumber);  //트럭번호로 트럭정보찾기
	
	String findtruckNumberBySellerId(String sellerId);  //아이디로 트럭번호찾기

	void updateMyfoodtruck(TruckVO truckVO, String uploadPath); //푸드트럭 설정 수정하기 - 트럭정보 수정 및 파일경로 수정

	List<FoodVO> showMenuList(String truckNumber); //트럭번호에 해당하는 메뉴 리스트보기

	
	void registerMenuList(List<FoodVO> foodList, String truckNumber, String uploadPath);  //트럭번호에 해당하는 메뉴리스트 등록하기

	//메뉴아이디에 따른 메뉴수정으로 바꿔야함, 수정해주고 id로 다시 찾아서 보내야함
	void updateMenu(TruckVO truckVO, String uploadPath);

	void deleteMyTruck(String foodtruckNumber);
	
	ListVO showMyReviewList(String customerId, String reviewPageNo); //리뷰 리스트보기
	
	void updateMyReview(ReviewVO reviewVO); //리뷰수정하기
	
	void deleteMyReview(String reviewNo); //리뷰 삭제하기

	public  ReviewVO findReviewInfoByReviewNo(String reviewNo);

	public TruckVO getGPSInfo(String sellerId);

	public void setGPSInfo(TruckVO gpsInfo);

	public ListVO getWishList(String pageNo, String id);
	
	public int getWishListFlag(String customerId, String foodtruckNumber);

	ListVO showMyContentByFreeList(String id, String contentPageNo);

	void updateBookingState(BookingVO bookingVO);//강정호. 결제완료->조리중->조리상태 바꿔주는 메서드

	//List<BookingVO> getBookingVO(String foodTruckNumber);
	
	//List<BookingVO> getSellerBookingListByTruckNumber(String foodTruckNumber);//강정호. 
	
	ListVO getBookingVO(String foodTruckNumber, String pageNo);//강정호. 페이징빈을 적용하여 창업주의 주문내역 가져옴

	List<BookingDetailVO> getBookingDetailVO(BookingVO bookingVO);//강정호. 해당 예약 번호에 대한 주문 메뉴 내역 불러옴

	void freeboardDeleteInMaypage(String contentNo);

	ListVO showMyContentBybusinessList(String id, String contentPageNo);

	void businessDeleteInMaypage(String contentNo);

	ListVO showMyContentByqnaList(String id, String contentPageNo);

	void qnaDeleteInMaypage(String contentNo);

	int checkBookingState(String id);

	ListVO getPointListById(String id, String nowPage);

}
