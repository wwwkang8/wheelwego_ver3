package org.asechs.wheelwego.model;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.asechs.wheelwego.model.vo.BoardVO;
import org.asechs.wheelwego.model.vo.BookingDetailVO;
import org.asechs.wheelwego.model.vo.BookingVO;
import org.asechs.wheelwego.model.vo.FileVO;
import org.asechs.wheelwego.model.vo.FoodVO;
import org.asechs.wheelwego.model.vo.PagingBean;
import org.asechs.wheelwego.model.vo.PointVO;
import org.asechs.wheelwego.model.vo.ReviewVO;
import org.asechs.wheelwego.model.vo.TruckVO;
import org.asechs.wheelwego.model.vo.WishlistVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

/**
 * 본인 이름 수정 날짜 (수정 완료) 대제목[마이페이지/푸드트럭/멤버/게시판/예약] - 소제목
 * ------------------------------------------------------ 코드설명
 * 
 * EX) 박다혜 2017.06.21 (수정완료) / (수정중) 마이페이지 - 마이트럭설정
 * --------------------------------- ~~~~~
 */
@Repository
public class MypageDAOImpl implements MypageDAO {
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;
	
	/** 	  
	정현지
	2017.06.21 (수정완료)
	마이페이지 - 나의 주문내역 리스트 (pagingBean 적용)
	기능설명 : 나의 주문 내역 리스트에 pagingBean 적용
	*/
	@Override
	public List<BookingVO> customerBookingList(PagingBean pagingBean) {
		return sqlSessionTemplate.selectList("mypage.customerBookingList", pagingBean);
	}
	
	/** 	  
	정현지
	2017.06.21 (수정완료)
	마이페이지 - 나의 주문내역 리스트
	기능설명 : 예약 번호를 통해 주문 내역을 가져온다
	*/
	public List<BookingVO> getBookingList(int bookingNumber) {
	      return sqlSessionTemplate.selectList("mypage.getBookingList", bookingNumber);
	   }
	
	/**
	 * 김래현,황윤상 2017.06.21 (수정완료)
	 * 마이페이지 - 포인트조회
	 * 기능설명 : 해당 아이디의 보유포인트를 조회한다.
	 */
	@Override
	public int getMyPoint(String customerId) {
		return sqlSessionTemplate.selectOne("mypage.getMyPoint", customerId);
	}
	/**
	 * 김래현,황윤상 2017.06.21 (수정완료)
	 * 마이페이지 - 포인트사용
	 * 기능설명 : 보유포인트중 사용할 포인트를 입력하고 보유포인트에서 차감한다.
	 */
	@Override
	public void minusPoint(HashMap<String, Integer> pointInfo) {
		sqlSessionTemplate.insert("mypage.minusPoint", pointInfo);
	}

	/**
	 * 김래현,황윤상 2017.06.21 (수정완료)
	 * 마이페이지 - 포인트적립
	 * 기능설명 : 선택한 음식리스트의 총액의 5%를 적립해준다
	 */
	@Override
	public void addPoint(HashMap<String, Object> pointInfo) {
		sqlSessionTemplate.insert("mypage.addPoint", pointInfo);
	}
	@Override
	public List<WishlistVO> heartWishList(String id) {
		return sqlSessionTemplate.selectList("mypage.heartWishList", id);
	}
	/**
	 * 김래현,황윤상 
	 * 2017.06.21 (수정완료) 
	 * 마이페이지 - 단골트럭 조회 기능설명 : 로그인되어있는 아이디의 저장된 단골트럭리스트
	 * select
	 */
	@Override
	public List<TruckVO> myWishList(String id) {
		return sqlSessionTemplate.selectList("mypage.myWishList", id);
	}

	/**
	 *  김래현,황윤상
	 *  2017.06.21 (수정완료)
	 *  마이페이지 - 단골트럭 삭제 기능설명 : 로그인되어있는 아이디의 저장된 단골트럭리스트
	 *  중에서 클릭된 푸드트럭넘버를 기반으로 삭제할 수 있다.
	 */
	@Override
	public void deleteWishList(WishlistVO wishlistVO) {
		sqlSessionTemplate.delete("mypage.deleteWishList", wishlistVO);
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료

	 */
	public void registerFoodtruck(TruckVO tvo) {
		sqlSessionTemplate.insert("mypage.registerFoodtruck", tvo);
	}
	/**
	 * 박다혜
	 * 2017.06.21
	 */
	@Override
	public TruckVO findtruckInfoByTruckNumber(String truckNumber) {
		return sqlSessionTemplate.selectOne("mypage.findtruckInfoByTruckNumber", truckNumber);
	}
	/**
	 * 박다혜
	 * 2017.06.21
	 */
	@Override
	public void saveFilePath(FileVO fileVO) {
		sqlSessionTemplate.insert("mypage.saveFilePath", fileVO);
	}
	/**
	 * 박다혜
	 */
	@Override
	public void updateMyfoodtruck(TruckVO truckVO) {
		sqlSessionTemplate.update("mypage.updateMyfoodtruck", truckVO);
	}
	/**
	 * 박다혜
	 */
	@Override
	public void updateFilePath(FileVO fileVO) {
		sqlSessionTemplate.update("mypage.updateFilePath", fileVO);
	}

	@Override
	public String findtruckNumberBySellerId(String sellerId) {
		return sqlSessionTemplate.selectOne("mypage.findtruckNumberBySellerId", sellerId);
	}
	/**
	 * 박다혜
	 */
	@Override
	public List<FoodVO> showMenuList(String truckNumber) {
		return sqlSessionTemplate.selectList("mypage.showMenuList", truckNumber);
	}

	@Override
	public void deleteAllMenu(String truckNumber) {
		sqlSessionTemplate.delete("mypage.deleteAllMenu", truckNumber);
	}
	/**
	 * 박다혜
	 */
	@Override
	public void registerMenu(FoodVO foodVO) {
		sqlSessionTemplate.insert("mypage.registerMenu", foodVO);
	}

	@Override
	public void deleteMenu(String menuId) {
		sqlSessionTemplate.delete("mypage.deleteMenu", menuId);
	}
	/**
	 * 박다혜
	 */
	@Override
	public void updateMenu(FoodVO foodVO) {
		sqlSessionTemplate.update("mypage.updateMenu", foodVO);
	}

	@Override
	public FoodVO findMenuByMenuId(String menuId) {
		return sqlSessionTemplate.selectOne("mypage.findMenuByMenuId", menuId);
	}


	@Override
	public void deleteMyTruck(String foodtruckNumber) {
		sqlSessionTemplate.delete("mypage.deleteMyTruck", foodtruckNumber);
	}
	/**
	 * 박다혜
	 */
	@Override
	public List<ReviewVO> showMyReviewList(PagingBean pagingBean) {
		return sqlSessionTemplate.selectList("mypage.showMyReview", pagingBean);
	}
	/**
	 * 박다혜
	 */
	@Override
	public void updateMyReview(ReviewVO reviewVO) {
		sqlSessionTemplate.update("mypage.updateMyReview", reviewVO);
	}
	/**
	 * 박다혜
	 */
	@Override
	public void deleteMyReview(String reviewNo) {
		sqlSessionTemplate.delete("mypage.deleteMyReview", reviewNo);
	}
	/**
	 * 박다혜
	 */
	@Override
	public ReviewVO findReviewInfoByReviewNo(String reviewNo) {
		return sqlSessionTemplate.selectOne("mypage.findReviewInfoByReviewNo", reviewNo);
	}

	@Override
	public TruckVO getGPSInfo(String sellerId) {
		System.out.println(sellerId);
		return sqlSessionTemplate.selectOne("mypage.getGPSInfo", sellerId);
	}

	@Override
	public void stayFoodtruck(TruckVO gpsInfo) {
		System.out.println("stay 실행 " + gpsInfo);
		sqlSessionTemplate.update("mypage.stayFoodtruck", gpsInfo);
	}

	@Override
	public void leaveFoodtruck(TruckVO gpsInfo) {
		System.out.println("leave 실행 " + gpsInfo);
		sqlSessionTemplate.update("mypage.leaveFoodtruck", gpsInfo);
	}

	@Override
	public int getWishListTotalContentCount(String id) {
		return sqlSessionTemplate.selectOne("mypage.getWishListTotalContentCount", id);
	}

	@Override
	public List<TruckVO> getWishList(PagingBean pagingBean) {
		System.out.println("dao: " + sqlSessionTemplate.selectList("mypage.getWishList", pagingBean));
		return sqlSessionTemplate.selectList("mypage.getWishList", pagingBean);
	}

	@Override
	public int getTotalReviewCount(String customerId) {
		return sqlSessionTemplate.selectOne("mypage.getTotalReviewCount", customerId);
	}

	@Override
	public int getWishListFlag(WishlistVO wishlistVO) {
		return sqlSessionTemplate.selectOne("mypage.getWishListFlag", wishlistVO);
	}

	@Override

	public void updateBookingState(BookingVO bookingVO) {
		sqlSessionTemplate.update("mypage.updateBookingState", bookingVO);

	}

	@Override
	public List<BookingVO> getBookingVO(String foodTruckNumber) {
		System.out.println("getBookingVO 푸드트럭 넘버" + foodTruckNumber);
		return sqlSessionTemplate.selectList("mypage.getBookingVOCount", foodTruckNumber);
	}

	@Override
	public List<BookingVO> getBookingVO(PagingBean pagingBean) {
		return sqlSessionTemplate.selectList("mypage.getBookingVO", pagingBean);
	}

	@Override
	public List<BookingDetailVO> getBookingDetailVO(BookingVO bookingVO) {
		return sqlSessionTemplate.selectList("mypage.getBookingDetailVO", bookingVO);
	}
	/**
	 * 김호겸
	 * 2017.6.13 (수정 완료)
	 * 마이페이지- 페이징 빈을 위해 총 게시물 수 
	 */
	@Override
	public int getTotalFreeboardCount(String id) {
		return sqlSessionTemplate.selectOne("mypage.getTotalFreeboardCount", id);
	}
	/**
	 * 김호겸
	 * 2017.6.12(수정 완료) 
	 * 마이페이지- 내가 쓴 자유게시글 삭제
	 * ------------------------------------
	 * board.xml 로 넘기는 이유는 resultMap를 위해서 이다.
	 */
	public void freeboardDeleteInMaypage(String contentNo) {
		sqlSessionTemplate.delete("board.freeboardDelete", contentNo);
	}
	/**
	 * 김호겸
	 * 2017.6.13 (수정 완료) 
	 *마이페이지-내가 쓴 게시글 자유게시글 보기
	 * ------------------------------------------------------ 코드설명
	 * rnum 으로 페이징 빈을 하였다.
	 * board.xml 로 넘기는 이유는 resultMap를 위해서 이다.
	 */
	@Override
	public List<BoardVO> showMyContentByFreeList(PagingBean pagingBean) {
		return sqlSessionTemplate.selectList("board.showMyContentByFreeList", pagingBean);
	}
	/**
	 * 김호겸
	 * 2017.6.13 (수정 완료)
	 * 마이페이지- 페이징 빈을 위해 총 게시물 수 
	 */
	@Override
	public int getTotalbusinessCount(String id) {
		return sqlSessionTemplate.selectOne("mypage.getTotalbusinessCount", id);
	}
	/**
	 * 김호겸
	 * 2017.6.14 (수정 완료) 
	 *마이페이지-내가 쓴 게시글 창업게시글 보기
	 * ------------------------------------------------------ 코드설명
	 * rnum 으로 페이징 빈을 하였다.
	 * board.xml 로 넘기는 이유는 resultMap를 위해서 이다.
	 */
	@Override
	public List<BoardVO> showMyContentBybusinessList(PagingBean pagingBean) {
		return sqlSessionTemplate.selectList("board.showMyContentBybusinessList", pagingBean);
	}
	/**
	 * 김호겸
	 * 2017.6.13(수정 완료) 
	 * 마이페이지- 내가 쓴 창업게시글 삭제
	 * ------------------------------------
	 * board.xml 로 넘기는 이유는 resultMap를 위해서 이다.
	 */
	@Override
	public void businessDeleteInMaypage(String contentNo) {
		sqlSessionTemplate.delete("board.businessDelete", contentNo);
	}
	/**
	 * 김호겸
	 * 2017.6.13 (수정 완료)
	 * 마이페이지- 페이징 빈을 위해 총 게시물 수 
	 */
	@Override
	public int getTotalqnaCount(String id) {
		return sqlSessionTemplate.selectOne("mypage.getTotalqnaCount", id);
	}
	/**
	 * 김호겸
	 * 2017.6.14 (수정 완료) 
	 *마이페이지-내가 쓴 게시글 QnA게시글 보기
	 * ------------------------------------------------------ 코드설명
	 * rnum 으로 페이징 빈을 하였다.
	 * board.xml 로 넘기는 이유는 resultMap를 위해서 이다.
	 */
	@Override
	public List<BoardVO> showMyContentByqnaList(PagingBean pagingBean) {
		return sqlSessionTemplate.selectList("board.showMyContentByqnaList", pagingBean);
	}
	/**
	 * 김호겸
	 * 2017.6.13(수정 완료) 
	 * 마이페이지- 내가 쓴 QnA게시글 삭제
	 * ------------------------------------
	 * board.xml 로 넘기는 이유는 resultMap를 위해서 이다.
	 */
	@Override
	public void qnaDeleteInMaypage(String contentNo) {
		sqlSessionTemplate.delete("board.qnaDelete", contentNo);

	}

	@Override
	public String getBookingNumberByCustomerId(String id) {
		return sqlSessionTemplate.selectOne("mypage.getBookingNumberListByCustomerId", id);

	}

	@Override
	public int checkBookingState(String customerId) {
		return sqlSessionTemplate.selectOne("mypage.checkBookingState", customerId);
	}

	@Override
	public int getTotalBookingCount(String foodTruckNumber) {
		return sqlSessionTemplate.selectOne("mypage.getTotalBookingCount", foodTruckNumber);
	}

	public int getTotalPointCountById(String id) {
		return sqlSessionTemplate.selectOne("mypage.getTotalPointCountById", id);
	}

	@Override
	public List<PointVO> getPointListById(PagingBean pagingBean) {
		return sqlSessionTemplate.selectList("mypage.getPointListById", pagingBean);
	}

	@Override
	public int getCustomerBookingListCount(String customerId) {
		return sqlSessionTemplate.selectOne("mypage.getCustomerBookingListCount", customerId);
	}
	/**
	 * 박다혜
	 */
	@Override
	public String getNextMenuSequence() {
		return sqlSessionTemplate.selectOne("mypage.getNextMenuSequence");
	}
}
