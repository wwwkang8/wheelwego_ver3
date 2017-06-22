package org.asechs.wheelwego.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.asechs.wheelwego.model.vo.BoardVO;
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
	정현지
	2017.06.22 (수정완료)
	마이페이지 - 나의 주문내역 리스트 푸드트럭 이름 가져오기
	기능설명 : step1) bookingNumber로 menuId 찾기
	*/
   @Override
	public List<String> findMenuIdByBookingNumber(String bookingNumber){
		return mypageDAO.findMenuIdByBookingNumber(bookingNumber);
	}
	/** 	  
	정현지
	2017.06.22 (수정완료)
	마이페이지 - 나의 주문내역 리스트 푸드트럭 이름 가져오기
	기능설명 : step2) menuId로 foodtruckNumber 찾기
	*/
  @Override
	public String findFoodTruckNumberByMenuId(String menuId){
		return mypageDAO.findFoodTruckNumberByMenuId(menuId);
	}
	/** 	  
	정현지
	2017.06.22 (수정완료)
	마이페이지 - 나의 주문내역 리스트 푸드트럭 이름 가져오기
	기능설명 : step3) foodtruckNumber로 foodtruckName 찾기
	*/
	@Override
	public String findFoodtruckNameByFoodTruckNumber(String foodtruckNumber){
		return mypageDAO.findFoodtruckNameByFoodTruckNumber(foodtruckNumber);
	}
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
		
		FileManager fm=new FileManager();
	      MultipartFile truckFile=tvo.getFoodtruckFile(); 
	      String renamedFile=fm.rename(truckFile,tvo.getFoodtruckNumber());
	      tvo.setFileVO(new FileVO(tvo.getFoodtruckName(), renamedFile));
	            try {
	            	mypageDAO.registerFoodtruck(tvo); //FOODTRUCK Table에 저장
	            	mypageDAO.saveFilePath(new FileVO(tvo.getFoodtruckNumber(),renamedFile)); //FOODTRUCKFILE table에 이미지경로저장
					fm.uploadFile(truckFile,/*uploadPath+*/renamedFile); //서버 전송
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
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 마이페이지 - 사업자 아이디에 해당하는 푸드트럭 번호 반환
	 */
	@Override
	public String findtruckNumberBySellerId(String sellerId) {
		 return mypageDAO.findtruckNumberBySellerId(sellerId);
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 마이페이지 - 마이트럭 설정 업데이트
	 * --------------------------------------------
	 * update할 파일이 존재하지 않으면 해당 트럭의 truckVO 정보만 수정.
	 * 파일이 존재한다면 파일의 이름을 rename한다.
	 * 후에 Foodtruck table의 file경로를 수정하고 서버에 전송한 뒤
	 *  truckVO 정보로 수정한다.
	 */
	@Transactional
	@Override
	public void updateMyfoodtruck(TruckVO truckVO, String uploadPath) {
	      MultipartFile truckFile=truckVO.getFoodtruckFile();  
	      if(truckFile!=null){
	         try {
		        FileManager fm=new FileManager();
		         String renamedFile=fm.rename(truckFile,truckVO.getFoodtruckNumber()); //파일 Rename
		         mypageDAO.updateFilePath(new FileVO(truckVO.getFoodtruckNumber(), renamedFile)); //파일 경로 update
		         fm.uploadFile(truckFile, /*uploadPath+*/renamedFile); //서버 전송
		         mypageDAO.updateMyfoodtruck(truckVO); 
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }else{
		         mypageDAO.updateMyfoodtruck(truckVO); 
	      }
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
	 * 트럭으로부터 메뉴 리스트 꺼내온다.
	 * 각 메뉴에 대한 메뉴이미지파일이 있다면
	 * 이미지를 받아와서 서버로 전송한 뒤 메뉴 정보를 수정한다. 
	 *  
	 *  메뉴파일이 없다면 메뉴정보만 수정한다.
	 */
	@Transactional
	@Override
	public void updateMenu(TruckVO truckVO, String uploadPath) {
		 List<FoodVO> foodList=truckVO.getFoodList();
		 String renamedFile=null;
	      for(int i=0;i<foodList.size();i++){
	    	  MultipartFile foodFile=foodList.get(i).getMenuFile();
	    	  if(foodFile!=null){ 
		         try{
		            FileManager fm=new FileManager();
		            renamedFile=fm.rename(foodFile,truckVO.getFoodtruckNumber()+"_"+foodList.get(i).getMenuId()); //파일 이름 수정
		            foodList.get(i).setFileVO(new FileVO(foodList.get(i).getMenuId(),renamedFile)); //foodList에 renamed 되어진 FileVO정보를 setting
		            fm.uploadFile(foodFile, /*uploadPath+*/renamedFile); //서버에 전송하여 덮어씌운다.
		            mypageDAO.updateMenu(foodList.get(i)); //메뉴정보 수정
		         }
		         catch (Exception e) {
		            e.printStackTrace();
		         }
	    	 }else{	    		 
	    		 mypageDAO.updateMenu(foodList.get(i)); //메뉴정보 수정
	    	 }
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
	 * 2017.06.21 수정완료
	 * 마이페이지 - 나의 리뷰목록 보기
	 * -----------------------------------------
	 * 페이징 객체 생성시, 현재 리뷰 페이지와 사용자가 작성한 리뷰 수, 사용자의 아이디를 설정해준다.
	 * 페이지에 해당하는 리뷰 목록을 반환받고
	 * ListVO객체에 페이징 객체와 페이징이 적용된 reviewList객체를 설정하여 반환한다.
	 */
	@Override
	public ListVO showMyReviewList(String customerId, String reviewPageNo) {
			ListVO pagingReviewList = new ListVO();
	      if(reviewPageNo==null)
	          reviewPageNo="1";
	       PagingBean pagingBean = new PagingBean(Integer.parseInt(reviewPageNo), mypageDAO.getTotalReviewCount(customerId), customerId);
	       pagingReviewList.setPagingBean(pagingBean);
	       pagingReviewList.setReviewList(mypageDAO.showMyReviewList(pagingBean));
	       return pagingReviewList;
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 마이페이지- 마이 리뷰 수정하기
	 */
	@Override
	public void updateMyReview(ReviewVO reviewVO) {
		 mypageDAO.updateMyReview(reviewVO);
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 마이페이지 -마이 리뷰 삭제하기
	 */
	@Override
	public void deleteMyReview(String reviewNo) {
		mypageDAO.deleteMyReview(reviewNo);
		
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 마이페이지 - 리뷰번호에 해당하는 리뷰정보 불러오기
	 */
	@Override
	public ReviewVO findReviewInfoByReviewNo(String reviewNo) {
		 return mypageDAO.findReviewInfoByReviewNo(reviewNo);
	}
	/**
	 * 황윤상
	 * 2017.06.22 수정완료
	 * 마이페이지 - 사업자의 GPS정보 조회
	 */
	@Override
	public TruckVO getGPSInfo(String sellerId) {
		  return mypageDAO.getGPSInfo(sellerId);
	}
	/**
	 * 황윤상
	 * 2017.06.22 수정완료
	 * 마이페이지 - 사용자의 GPS위치 설정
	 * --------------------------------------------
	 * GPS 정보의 위도 혹은 경도가 없다면 사용자는 트럭의 위치를 해제한 것이므로 
	 * leaveFoodtruck을 실행하고
	 * 있다면 사용자는 위치를 설정한 것이므로 stayFoodtruck을 실행한다.
	 */
	@Override
	public void setGPSInfo(TruckVO gpsInfo) {
	      if (gpsInfo.getLatitude() == 0.0)
	         mypageDAO.leaveFoodtruck(gpsInfo);         
	      else
	         mypageDAO.stayFoodtruck(gpsInfo);
	}
	/**
	 * 김래현
	 * 2017.06.22 수정완료
	 * 마이페이지 - 단골트럭
	 * ---------------
	 * 자신이 등록한 단골트럭 리스트를 가져오는 메서드
	 * 
	 */
	 @Override
	   public ListVO getWishList(String pageNo, String id) {
	      int totalCount=mypageDAO.getWishListTotalContentCount(id);
	   
	      PagingBean pagingBean=null;
	      
	      if(pageNo==null)
	         pagingBean=new PagingBean(totalCount);
	      else
	         pagingBean=new PagingBean(totalCount,Integer.parseInt(pageNo));      
	      
	      pagingBean.setContentNumberPerPage(6);
	      pagingBean.setCustomerId(id);
	      
	      System.out.println( "service : "+new ListVO(pagingBean, mypageDAO.getWishList(pagingBean)));
	      return new ListVO(pagingBean, mypageDAO.getWishList(pagingBean));
	   }
	/**
	 * 박다혜
	 * 2017.06.22 수정완료
	 * 마이페이지 - 해당 푸드트럭이 사용자의 위시리스트 사항인지 검사한다.
	 * ---------------------------------------------------------------------------
	 * WISHLIST 테이블에 사용자의 아이디와 푸드트럭번호에 해당하는 
	 * 데이터가 존재한다면 1을, 존재하지 않는다면 0을 반환한다.
	 */
	@Override
	public int getWishListFlag(String customerId, String foodtruckNumber) {
	      WishlistVO wishlistVO=new WishlistVO(foodtruckNumber,customerId); 
	      return mypageDAO.getWishListFlag(wishlistVO);
	}
	/**
	    * 김호겸
	    * 2017.6.13 (수정 완료) 
	    *마이페이지-내가 쓴 게시글 자유게시글 보기
	    * ------------------------------------------------------ 코드설명
	    * 페이징 빈을 위해 총 게시물 수를 알아야 함으로 총게시물 수 메서드 뽑아낸후
	    * 페이징 빈에 할당한다. 그 후 리스트 VO 에 해당 게시물과
	    * 페이징 빈을 set 해준다
	    */
	@Override
	public ListVO showMyContentByFreeList(String id,String contentPageNo) {
		 if(contentPageNo==null)
			 contentPageNo="1";
	      PagingBean pagingBean = new PagingBean(Integer.parseInt(contentPageNo), mypageDAO.getTotalFreeboardCount(id), id);
	     List<BoardVO> contentList=mypageDAO.showMyContentByFreeList(pagingBean);
	     ListVO pagingContentList = new ListVO();
	     pagingContentList.setBoardList(contentList);
	     pagingContentList.setPagingBean(pagingBean);
	     return pagingContentList;
	}
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 마이페이지 - 주문 상태 업데이트
	 * -----------------------------------------------
	 * 코드 설명 : 사업자의 주문 내역에서 "조리중", "조리완료" 버튼을 눌러서
	 * 주문 상태를 업데이트 해주는 메서드이다. BookingVO에 있는 예약 번호를 이용해서
	 * 예약 번호에 해당하는 주문 상태를 조리중 또는 조리 완료로 SQL로 업데이트 해준다.
	 */
	@Override
	public void updateBookingState(BookingVO bookingVO) {
		mypageDAO.updateBookingState(bookingVO);
		
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 마이페이지 - 사업자 주문 내역
	 * ------------------------------------------
	 * 코드 설명 : 푸드 트럭 넘버를 이용해서 사업자가 받은 주문 내역을 받아오는 메서드입니다.
	 * foodTruckNumber와 pageNo를 DAO에 매개 변수로 넘겨줍니다. 주문내역은 BookingVO 타입의
	 * 데이터를 List 형식으로 받아옵니다.
	 */
	@Override
	public ListVO getBookingVO(String foodTruckNumber, String pageNo) {
		List<BookingVO> list=mypageDAO.getBookingVO(foodTruckNumber);
		int totalCount=list.size();

		PagingBean pagingBean = null;
		if (pageNo == null){
			pagingBean = new PagingBean(totalCount, 1);
			pagingBean.setContentNumberPerPage(9);
		}else{
			pagingBean = new PagingBean(totalCount, Integer.parseInt(pageNo));
			pagingBean.setContentNumberPerPage(9);
		}
		
		pagingBean.setFoodTruckNumber(foodTruckNumber);
		ListVO listVO=new ListVO();
		listVO.setBookingNumberList(mypageDAO.getBookingVO(pagingBean));
		listVO.setPagingBean(pagingBean);
		return listVO;
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 마이페이지 - 사업자 주문 메뉴 내역
	 * ----------------------------------------------------
	 * 코드 설명 : 위의 메서드 getBookingVO로 받아온 주문 내역에는 주문 메뉴의 상세 내역이 없다.
	 * 그래서 getBookingDetailVO 메서드를 이용하여 해당 예약 번호에 속한 상세 메뉴 내역(수량, 가격, 메뉴이름)을 가져온다.
	 * BookingVO를 매개변수로 하여 그 안에 있는 예약 번호를 이용해서 주문 내역을 찾는다.
	 * 
	 */
	@Override
	public List<BookingDetailVO> getBookingDetailVO(BookingVO bookingVO) {
		return mypageDAO.getBookingDetailVO(bookingVO);
	}
	
	/**
	 * 김호겸
	 * 2017.6.13 (수정 완료) 
	 *마이페이지-내가 쓴 게시글 QnA게시글 삭제
	 */
	@Override
	public void freeboardDeleteInMaypage(String contentNo) {
		mypageDAO.freeboardDeleteInMaypage(contentNo);
	}
	
	/**
	 * 김호겸
	 * 2017.6.13 (수정 완료) 
	 *마이페이지-내가 쓴 게시글 창업게시글 보기
	 * ------------------------------------------------------ 코드설명
	 * 페이징 빈을 위해 총 게시물 수를 알아야 함으로 총게시물 수 메서드 뽑아낸후
	 * 페이징 빈에 할당한다. 그 후 리스트 VO 에 해당 게시물과
	 * 페이징 빈을 set 해준다
	 */
	@Override
	public ListVO showMyContentBybusinessList(String id, String contentPageNo) {
		 if(contentPageNo==null)
			 contentPageNo="1";
	      PagingBean pagingBean = new PagingBean(Integer.parseInt(contentPageNo), mypageDAO.getTotalbusinessCount(id), id);
	     List<BoardVO> contentList=mypageDAO.showMyContentBybusinessList(pagingBean);
	     ListVO pagingContentList = new ListVO();
	     pagingContentList.setBoardList(contentList);
	     pagingContentList.setPagingBean(pagingBean);
	     return pagingContentList;
	}
	
	/**
	 * 김호겸
	 * 2017.6.13 (수정 완료) 
	 *마이페이지-내가 쓴 게시글 QnA게시글 삭제
	 */
	@Override
	public void businessDeleteInMaypage(String contentNo) {
		mypageDAO.businessDeleteInMaypage(contentNo);
	}
	
	/**
	 * 김호겸
	 * 2017.6.13 (수정 완료) 
	 *마이페이지-내가 쓴 게시글 QnA게시글 보기
	 * ------------------------------------------------------ 코드설명
	 * 페이징 빈을 위해 총 게시물 수를 알아야 함으로 총게시물 수 메서드 뽑아낸후
	 * 페이징 빈에 할당한다. 그 후 리스트 VO 에 해당 게시물과
	 * 페이징 빈을 set 해준다
	 */
	@Override
	public ListVO showMyContentByqnaList(String id, String contentPageNo) {
		 if(contentPageNo==null)
			 contentPageNo="1";
	      PagingBean pagingBean = new PagingBean(Integer.parseInt(contentPageNo), mypageDAO.getTotalqnaCount(id), id);
	     List<BoardVO> contentList=mypageDAO.showMyContentByqnaList(pagingBean);
	     ListVO pagingContentList = new ListVO();
	     pagingContentList.setBoardList(contentList);
	     pagingContentList.setPagingBean(pagingBean);
	     return pagingContentList;
	}
	
	/**
	 * 김호겸
	 * 2017.6.13 (수정 완료) 
	 *마이페이지-내가 쓴 게시글 QnA게시글 삭제
	 */
	@Override
	public void qnaDeleteInMaypage(String contentNo) {
		mypageDAO.qnaDeleteInMaypage(contentNo);
	}
	/**
	 * 황윤상
	 * 2017.06.22 수정완료
	 * 예약 - 사용자 아이디에 해당하는 선행주문이 있는지 체크
	 */
	@Override
	public int checkBookingState(String customerId) {
		return mypageDAO.checkBookingState(customerId);
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 마이페이지 - 아이디에 해당하는 포인트 내역 반환
	 * --------------------------------------------------------
	 * 페이징빈 객체에 현재페이지, 아이디에 해당하는 포인트 내역 수, 검색조건으로 아이디를 설정한다.
	 * ListVO객체에 현재 페이지에 해당하는 point내역과 페이징빈 객체를 설정하여 반환한다.
	 */
	@Override
	public ListVO getPointListById(String id, String nowPage) {
		ListVO pointList=new ListVO();
		if(nowPage==null)
			nowPage="1";
		PagingBean pagingBean=new PagingBean(Integer.parseInt(nowPage), mypageDAO.getTotalPointCountById(id), id);
		pointList.setPointList(mypageDAO.getPointListById(pagingBean));
		pointList.setPagingBean(pagingBean);
		return pointList;
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 마이페이지 - 메뉴 등록
	 * -----------------------------
	 * 트럭으로부터 입력된 메뉴리스트를 반환받는다.
	 * 각 메뉴에 대해 트럭넘버와 sequence로 생성된 메뉴아이디를 설정해주고
	 * 메뉴 사진을 받아 와서 파일 이름을 [푸드트럭넘버_메뉴아이디.fileExt] 형식으로 rename한다.
	 * 후에 db에 메뉴 정보를 등록하고 서버에 파일을 업로드한다.
	 */
	@Transactional
	@Override
	public void registerMenuList(TruckVO truckVO, String uploadPath) {
		   List<FoodVO> foodList=truckVO.getFoodList();
		   String foodtruckNumber=truckVO.getFoodtruckNumber();
		   String renamedFile=null;
		   
	      for(int i=0;i<foodList.size();i++){
	         try{
		        	 foodList.get(i).setFoodTruckNumber(foodtruckNumber); //트럭넘버를 setting
		        	 foodList.get(i).setMenuId(mypageDAO.getNextMenuSequence()); //메뉴아이디 setting
		        	 FileManager fm=new FileManager();
		        	 MultipartFile foodFile=foodList.get(i).getMenuFile(); //메뉴사진받아와서
		        	 renamedFile=fm.rename(foodFile,foodtruckNumber+"_"+foodList.get(i).getMenuId()); //파일 이름 수정
		        	 foodList.get(i).setFileVO(new FileVO(foodtruckNumber, renamedFile)); //rename된 파일 이름 setting
		        	 mypageDAO.registerMenu(foodList.get(i)); //db에 메뉴를 등록한다.
		            fm.uploadFile(foodFile,/* uploadPath+*/renamedFile); //서버에 파일 업로드
	         	}catch (Exception e) {
	         		e.printStackTrace();
	         	}
	      }		
	}
  
}