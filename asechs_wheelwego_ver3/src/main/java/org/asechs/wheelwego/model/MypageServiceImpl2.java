package org.asechs.wheelwego.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.asechs.wheelwego.model.vo.BookingDetailVO;
import org.asechs.wheelwego.model.vo.BookingVO;
import org.asechs.wheelwego.model.vo.FileManager;
import org.asechs.wheelwego.model.vo.FileVO;
import org.asechs.wheelwego.model.vo.FoodVO;
import org.asechs.wheelwego.model.vo.ListVO;
import org.asechs.wheelwego.model.vo.PagingBean;
import org.asechs.wheelwego.model.vo.ReviewVO;
import org.asechs.wheelwego.model.vo.TruckVO;
import org.asechs.wheelwego.model.vo.WishlistVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MypageServiceImpl2 implements MypageService {
   @Resource
   private MypageDAO mypageDAO;
   @Resource
   private FoodTruckDAO foodtruckDAO;
	/**
	 * 김래현 황윤상
	   2017.06.21 수정완료
 	    마이페이지-포인트적립,사용 계산메서드
	 */
   @Override
	public void calPoint(String usePoint, String totalAmount, int bookingNumber) {
		int _usePoint = 0;
		if (usePoint!=null &&usePoint!="")
			{
			_usePoint = Integer.parseInt(usePoint);
			minusPoint(_usePoint, bookingNumber);
			}
			addPoint(Integer.parseInt(totalAmount), bookingNumber);
	   }
	/** 	  
	정현지
	2017.06.21 (수정완료)
	마이페이지 - 나의 주문내역 리스트 (pagingBean 적용)
	기능설명 : 나의 주문 내역 리스트에 pagingBean 적용
	*/
   @Override
   public ListVO customerBookingList(String pageNo, String customerId) {
	      int totalCount = mypageDAO.getCustomerBookingListCount(customerId);
	      PagingBean pagingBean = null;
	      if (pageNo == null)
	         pagingBean = new PagingBean(totalCount);
	      else
	         pagingBean = new PagingBean(totalCount, Integer.parseInt(pageNo));	      
	      pagingBean.setContentNumberPerPage(6);
	      pagingBean.setCustomerId(customerId);      
	      ListVO listVO = new ListVO();
	      listVO.setPagingBean(pagingBean);  
	      listVO.setBookingMenuList(mypageDAO.customerBookingList(pagingBean));
	      return listVO;
	   }
	/** 	  
	정현지
	2017.06.21 (수정완료)
	마이페이지 - 나의 주문내역 리스트
	기능설명 : 예약 번호를 통해 주문 내역을 가져온다
	*/
   @Override
   public List<BookingVO> getBookingList(int bookingNumber) {
      return mypageDAO.getBookingList(bookingNumber);
   }
	
	/**
	 * 김래현 황윤상
	   2017.06.21 수정완료
 	    마이페이지-포인트사용
	 */
	@Override
	public void minusPoint(int usePoint, int bookingNumber) {
		usePoint = -usePoint;
		HashMap<String, Integer> pointInfo = new HashMap<String, Integer>();
		pointInfo.put("point", usePoint);
		pointInfo.put("bookingNumber", bookingNumber);
		mypageDAO.minusPoint(pointInfo);
	   }
	/**
	 * 김래현 황윤상
	   2017.06.21 수정완료
 	    마이페이지-포인트적립
	 */
	@Override
	public void addPoint(int totalAmount, int bookingNumber) {
		HashMap<String, Object> pointInfo = new HashMap<String, Object>();
		pointInfo.put("point", Math.round(totalAmount * 0.05));
		pointInfo.put("bookingNumber", bookingNumber);
		mypageDAO.addPoint(pointInfo);
	}
	/**
	 * 김래현 황윤상
	   2017.06.21 수정완료
 	    마이페이지-보유포인트 조회
	 */
	@Override
	public int getMyPoint(String customerId) {
		return mypageDAO.getMyPoint(customerId);
	}
	/** 	  
	정현지
	2017.06.21 (수정완료)
	마이페이지 - 나의 위시리스트 목록
	기능설명 : 사용자의 아이디로 위시리스트 목록을 가져온다
	*/
	@Override
	public List<WishlistVO> heartWishList(String id){
	   return mypageDAO.heartWishList(id);
	}
	/**
	 * 김래현 황윤상
	   2017.06.21 수정완료
 	    마이페이지-단골트럭리스트
	 */
	@Override
	public List<TruckVO> myWishList(String id) {
		return mypageDAO.myWishList(id);
	}
	/**
	 * 김래현 황윤상
	 * 2017.06.21 수정완료
	 * 마이페이지-단골트럭삭제
	 */
	@Override
	public void deleteWishList(WishlistVO wishlistVO) {
		mypageDAO.deleteWishList(wishlistVO);
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 마이페이지 - 마이트럭 등록
	 * ----------------------------------------
	 * 입력된 트럭정보를 db에 등록할 때 정보는 Foodtruck table에 저장하고
	 * 푸드트럭 이미지는 FoodtruckFile table에 따로 저장한다.
	 * 또한 fileMager를 사용하여 image의 파일명을 [푸드트럭넘버.fileExt] 형식으로
	 * rename 한다.
	 */
	@Transactional
	@Override
	public void registerFoodtruck(TruckVO tvo, String uploadPath) {
		
	      MultipartFile truckFile=tvo.getFoodtruckFile(); 
	      FileManager fm=new FileManager();
	      String renamedFile=fm.rename(truckFile,tvo.getFoodtruckNumber());
	      tvo.setFileVO(new FileVO(tvo.getFoodtruckName(), renamedFile));
	       mypageDAO.registerFoodtruck(tvo);
	       mypageDAO.saveFilePath(new FileVO(tvo.getFoodtruckNumber(),renamedFile));
	            try {
					fm.uploadFile(truckFile,uploadPath+renamedFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
	         
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 마이페이지 - 트럭번호에 해당하는 트럭정보 불러오기
	 * -----------------------
	 * 트럭 넘버에 해당하는 트럭정보를 불러올 때 
	 * 평점과 즐겨찾기 수는 계속 업데이트 되는 사항이므로 
	 * 트럭 정보에 set하여 트럭정보를 보낸다.
	 */
	@Transactional
	@Override
	public TruckVO findtruckInfoByTruckNumber(String truckNumber) {
	       TruckVO truckVO=mypageDAO.findtruckInfoByTruckNumber(truckNumber);
	       truckVO.setAvgGrade(foodtruckDAO.findAvgGradeByTruckNumber(truckNumber));
	       truckVO.setWishlistCount(foodtruckDAO.findWishlistCountByTruckNumber(truckNumber));
	       return truckVO;
	}
	@Override
	public String findtruckNumberBySellerId(String sellerId) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 마이페이지 - 마이트럭 설정 업데이트
	 * --------------------------------------------
	 * 입력된 트럭정보로 수정할 때 트럭 정보는 Foodtruck table에서 update하고
	 * 푸드트럭 이미지는 FoodtruckFile table에서 update한다.
	 * 또한 업데이트할 파일이 있는 경우
	 * fileMager를 사용하여 image의 파일명을 [푸드트럭넘버.fileExt] 형식으로
	 * rename 하여 덮어씌운다.
	 */
	@Transactional
	@Override
	public void updateMyfoodtruck(TruckVO truckVO, String uploadPath) {
	      MultipartFile truckFile=truckVO.getFoodtruckFile(); 
	      if(truckFile!=null){
	         try {
	        FileManager fm=new FileManager();
	         String renamedFile=fm.rename(truckFile,truckVO.getFoodtruckNumber());
	         truckVO.setFileVO(new FileVO(truckVO.getFoodtruckNumber(), renamedFile));
	         mypageDAO.updateFilePath(truckVO.getFileVO()); //파일경로 등록
	          fm.uploadFile(truckFile, uploadPath+renamedFile);
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
	         mypageDAO.updateMyfoodtruck(truckVO); 
	      
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 마이페이지 - 트럭넘버에 해당하는 메뉴 리스트 불러오기
	 */
	@Override
	public List<FoodVO> showMenuList(String truckNumber) {
		return mypageDAO.showMenuList(truckNumber);
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 마이페이지 - 해당 트럭의 메뉴 업데이트
	 * -------------------------------------------------

	 */
	@Transactional
	@Override
	public void updateMenu(TruckVO truckVO, String uploadPath) {
		 List<FoodVO> foodList=truckVO.getFoodList();
		 String renamedFile=null;
	      for(int i=0;i<foodList.size();i++){
	    	  MultipartFile foodFile=foodList.get(i).getMenuFile(); //사진 받아서
	    	  if(foodFile!=null){ 
		         try{
		            FileManager fm=new FileManager();
		            renamedFile=fm.rename(foodFile,truckVO.getFoodtruckNumber()+"_"+foodList.get(i).getMenuId()); //파일 이름 수정
		            foodList.get(i).setFileVO(new FileVO(foodList.get(i).getMenuId(),renamedFile));
		            fm.uploadFile(foodFile, uploadPath+renamedFile);
		         }
		         catch (Exception e) {
		            e.printStackTrace();
		         }
	    	 }
	         mypageDAO.updateMenu(foodList.get(i)); //메뉴정보만 수정
	      }
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 마이페이지 - 해당 푸드트럭의 정보를 삭제한다.
	 */
	@Override
	public void deleteMyTruck(String foodtruckNumber) {
		mypageDAO.deleteMyTruck(foodtruckNumber);		
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정중
	 */
	@Override
	public ListVO showMyReviewList(String customerId, String reviewPageNo) {
	      if(reviewPageNo==null)
	          reviewPageNo="1";
	       PagingBean pagingBean = new PagingBean(Integer.parseInt(reviewPageNo), mypageDAO.getTotalReviewCount(customerId), customerId);
	       List<ReviewVO> reviewList=mypageDAO.showMyReviewList(pagingBean);
	       ListVO pagingReviewList = new ListVO();
	       pagingReviewList.setReviewList(reviewList);
	       pagingReviewList.setPagingBean(pagingBean);
	       return pagingReviewList;
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정중
	 */
	@Override
	public void updateMyReview(ReviewVO reviewVO) {
		 mypageDAO.updateMyReview(reviewVO);
		
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정중
	 */
	@Override
	public void deleteMyReview(String reviewNo) {
		mypageDAO.deleteMyReview(reviewNo);
		
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정중
	 */
	@Override
	public ReviewVO findReviewInfoByReviewNo(String reviewNo) {
		 return mypageDAO.findReviewInfoByReviewNo(reviewNo);
	}
	@Override
	public TruckVO getGPSInfo(String sellerId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setGPSInfo(TruckVO gpsInfo) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ListVO getWishList(String pageNo, String id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getWishListFlag(String customerId, String foodtruckNumber) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public ListVO showMyContentByFreeList(String id, String contentPageNo) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void updateBookingState(BookingVO bookingVO) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ListVO getBookingVO(String foodTruckNumber, String pageNo) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<BookingDetailVO> getBookingDetailVO(BookingVO bookingVO) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void freeboardDeleteInMaypage(String contentNo) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ListVO showMyContentBybusinessList(String id, String contentPageNo) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void businessDeleteInMaypage(String contentNo) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ListVO showMyContentByqnaList(String id, String contentPageNo) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void qnaDeleteInMaypage(String contentNo) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int checkBookingState(String id) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public ListVO getPointListById(String id, String nowPage) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 마이페이지 - 메뉴 등록
	 * -----------------------------
	 */
	@Transactional
	@Override
	public void registerMenuList(TruckVO truckVO, String uploadPath) {
		   List<FoodVO> foodList=truckVO.getFoodList();
		   String foodtruckNumber=truckVO.getFoodtruckNumber();
		   String renamedFile=null;
	      for(int i=0;i<foodList.size();i++){
	         try{
	        	 foodList.get(i).setFoodTruckNumber(foodtruckNumber); //트럭넘버를 세팅
	        	 foodList.get(i).setMenuId(mypageDAO.getNextMenuSequence());
	        	 FileManager fm=new FileManager();
	        	 MultipartFile foodFile=foodList.get(i).getMenuFile(); //메뉴사진받아와서
	        	 renamedFile=fm.rename(foodFile,foodtruckNumber+"_"+foodList.get(i).getMenuId()); //파일 이름 수정
	        	 foodList.get(i).setFileVO(new FileVO(foodtruckNumber, renamedFile));
	        	 mypageDAO.registerMenu(foodList.get(i)); //메뉴를 등록한다.
	            fm.uploadFile(foodFile, uploadPath+renamedFile); //서버에 파일 업로드
	         }catch (Exception e) {
	            e.printStackTrace();
	         }
	      }		
	}
  
}