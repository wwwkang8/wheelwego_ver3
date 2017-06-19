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

@Repository
public class MypageDAOImpl implements MypageDAO {
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;
	//현지
	@Override
	public List<BookingVO> customerBookingList(PagingBean pagingBean){
		return sqlSessionTemplate.selectList("mypage.customerBookingList",pagingBean);
	}
	//현지
	@Override
	   public List<BookingVO> getBookingList(int bookingNumber) {
	      return sqlSessionTemplate.selectList("mypage.getBookingList", bookingNumber);
	   }
	@Override
	   public int getMyPoint(String customerId) {
	      return sqlSessionTemplate.selectOne("mypage.getMyPoint", customerId);
	   }

	   @Override
	   public void minusPoint(HashMap<String, Integer> pointInfo) {
	      sqlSessionTemplate.insert("mypage.minusPoint", pointInfo);
	   }

	   @Override
	   public void addPoint(HashMap<String, Object> pointInfo) {
	      sqlSessionTemplate.insert("mypage.addPoint", pointInfo);
	   }

	@Override
	public List<WishlistVO> heartWishList(String id){
		return sqlSessionTemplate.selectList("mypage.heartWishList", id);
	}
	@Override
	public List<TruckVO> myWishList(String id) {
		return sqlSessionTemplate.selectList("mypage.myWishList", id);
	}

	@Override
	public void deleteWishList(WishlistVO wishlistVO) {
		sqlSessionTemplate.delete("mypage.deleteWishList", wishlistVO);
	}

	public void registerFoodtruck(TruckVO tvo) {
		sqlSessionTemplate.insert("mypage.registerFoodtruck", tvo);
	}

	@Override
	public TruckVO findtruckInfoByTruckNumber(String truckNumber) {
		return sqlSessionTemplate.selectOne("mypage.findtruckInfoByTruckNumber", truckNumber);
	}

	@Override
	public void saveFilePath(FileVO fileVO) {
		sqlSessionTemplate.insert("mypage.saveFilePath", fileVO);
	}

	@Override
	public void updateMyfoodtruck(TruckVO truckVO) {
		sqlSessionTemplate.update("mypage.updateMyfoodtruck", truckVO);
	}

	@Override
	public void updateFilePath(FileVO fileVO) {
		sqlSessionTemplate.update("mypage.updateFilePath", fileVO);
	}

	@Override
	public String findtruckNumberBySellerId(String sellerId) {
		return sqlSessionTemplate.selectOne("mypage.findtruckNumberBySellerId", sellerId);
	}

	@Override
	public List<FoodVO> showMenuList(String truckNumber) {
		return sqlSessionTemplate.selectList("mypage.showMenuList", truckNumber);
	}

	@Override
	public void deleteAllMenu(String truckNumber) {
		sqlSessionTemplate.delete("mypage.deleteAllMenu", truckNumber);
	}

	@Override
	public void registerMenu(FoodVO foodVO) {
		sqlSessionTemplate.insert("mypage.registerMenu", foodVO);
	}

	@Override
	public void deleteMenu(String menuId) {
		sqlSessionTemplate.delete("mypage.deleteMenu", menuId);
	}

	@Override
	public void updateMenu(FoodVO foodVO) {
		sqlSessionTemplate.update("mypage.updateMenu", foodVO);
	}

	@Override
	public FoodVO findMenuByMenuId(String menuId) {
		return sqlSessionTemplate.selectOne("mypage.findMenuByMenuId", menuId);
	}

	@Override
	public void updateMenuFilepath(FileVO fileVO) {
		sqlSessionTemplate.update("mypage.updateMenuFilepath", fileVO);

	}

	@Override
	public void deleteMyTruck(String foodtruckNumber) {
		sqlSessionTemplate.delete("mypage.deleteMyTruck", foodtruckNumber);
	}

	@Override
	public List<ReviewVO> showMyReviewList(PagingBean pagingBean) {
		return sqlSessionTemplate.selectList("mypage.showMyReview", pagingBean);
	}

	@Override
	public void updateMyReview(ReviewVO reviewVO) {
		sqlSessionTemplate.update("mypage.updateMyReview", reviewVO);
	}

	@Override
	public void deleteMyReview(String reviewNo) {
		sqlSessionTemplate.delete("mypage.deleteMyReview", reviewNo);
	}

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
		System.out.println("dao: "+sqlSessionTemplate.selectList("mypage.getWishList", pagingBean));
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
	public int getTotalFreeboardCount(String id) {
		return sqlSessionTemplate.selectOne("mypage.getTotalFreeboardCount", id);
	}
	
	@Override

	public void updateBookingState(BookingVO bookingVO) {
		sqlSessionTemplate.update("mypage.updateBookingState",bookingVO);
		
	}
	
	@Override
	public List<BookingVO> getBookingVO(String foodTruckNumber) {
		System.out.println("getBookingVO 푸드트럭 넘버"+foodTruckNumber);
		return sqlSessionTemplate.selectList("mypage.getBookingVOCount", foodTruckNumber);
	}
	
	@Override
	public List<BookingVO> getBookingVO(PagingBean pagingBean) {
		return sqlSessionTemplate.selectList("mypage.getBookingVO", pagingBean);
	}
	@Override
	public List<BookingDetailVO> getBookingDetailVO(BookingVO bookingVO) {
		return sqlSessionTemplate.selectList("mypage.getBookingDetailVO",bookingVO);
	}
	public void freeboardDeleteInMaypage(String contentNo) {
		sqlSessionTemplate.delete("board.freeboardDelete", contentNo);
	}
	@Override
	public List<BoardVO> showMyContentByFreeList(PagingBean pagingBean) {
		return sqlSessionTemplate.selectList("board.showMyContentByFreeList", pagingBean);
	}
	/*public List<BookingVO> getSellerBookingListByTruckNumber(String foodTruckNumber) {
		return sqlSessionTemplate.selectList("mypage.getSellerBookingListByTruckNumber",foodTruckNumber);
	}*/
	@Override
	public int getTotalbusinessCount(String id) {
		return sqlSessionTemplate.selectOne("mypage.getTotalbusinessCount", id);
	}
	@Override
	public List<BoardVO> showMyContentBybusinessList(PagingBean pagingBean) {
		return sqlSessionTemplate.selectList("board.showMyContentBybusinessList", pagingBean);
	}
	@Override
	public void businessDeleteInMaypage(String contentNo) {
		sqlSessionTemplate.delete("board.businessDelete", contentNo);
	}
	@Override
	public int getTotalqnaCount(String id) {
		return sqlSessionTemplate.selectOne("mypage.getTotalqnaCount", id);
	}
	@Override
	public List<BoardVO> showMyContentByqnaList(PagingBean pagingBean) {
		return sqlSessionTemplate.selectList("board.showMyContentByqnaList", pagingBean);
	}
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
}
