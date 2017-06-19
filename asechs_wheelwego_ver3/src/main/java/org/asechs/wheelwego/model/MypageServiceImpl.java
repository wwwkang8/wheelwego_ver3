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
import org.springframework.web.multipart.MultipartFile;

@Service
public class MypageServiceImpl implements MypageService {
   @Resource
   private MypageDAO mypageDAO;
   @Resource
   private FoodTruckDAO foodtruckDAO;
  
   @Override
   public ListVO customerBookingList(String pageNo, String customerId) {
	      System.out.println("customerId : " + customerId);
	      int totalCount = mypageDAO.getCustomerBookingListCount(customerId);
	      System.out.println("totalCount" + totalCount);


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
	      System.out.println("listVO" + listVO);
	      return listVO;
	   }
  
   @Override
   public List<BookingVO> getBookingList(int bookingNumber) {
      return mypageDAO.getBookingList(bookingNumber);
   }
   @Override
   public int getMyPoint(String customerId) {
      return mypageDAO.getMyPoint(customerId);
   }

   @Override
   public void calPoint(String usePoint, String totalAmount, int bookingNumber) {
	   System.out.println("service : "+usePoint);
      int _usePoint = 0;
      if (usePoint!=null &&usePoint!="")
      {
         _usePoint = Integer.parseInt(usePoint);
         minusPoint(_usePoint, bookingNumber);
      }
      addPoint(Integer.parseInt(totalAmount), bookingNumber);
   }

   @Override
   public void addPoint(int totalAmount, int bookingNumber) {
      HashMap<String, Object> pointInfo = new HashMap<String, Object>();
      pointInfo.put("point", Math.round(totalAmount * 0.05));
      pointInfo.put("bookingNumber", bookingNumber);
      mypageDAO.addPoint(pointInfo);
   }
   @Override
   public void minusPoint(int usePoint, int bookingNumber) {
      usePoint = -usePoint;
      HashMap<String, Integer> pointInfo = new HashMap<String, Integer>();
      pointInfo.put("point", usePoint);
      pointInfo.put("bookingNumber", bookingNumber);
      mypageDAO.minusPoint(pointInfo);
   }
   
   @Override
   public List<WishlistVO> heartWishList(String id){
      return mypageDAO.heartWishList(id);
   }
   @Override
   public List<TruckVO> myWishList(String id) {
      return mypageDAO.myWishList(id);
         
   }
   @Override
   public void deleteWishList(WishlistVO wishlistVO) {
      mypageDAO.deleteWishList(wishlistVO);
   }
   
   /**
    *  트럭정보 등록하기
    *  이후 파일경로도 같이 등록한다
    *  Transaction 처리
    */
   @Override
   public void registerFoodtruck(TruckVO tvo, String uploadPath) {
      MultipartFile truckFile=tvo.getFoodtruckFile(); 
      FileManager fm=new FileManager();
      String fileName=truckFile.getOriginalFilename();
      if(fileName.equals("")==false){
         try {
            String renamedFile=fm.rename(truckFile,tvo.getFoodtruckNumber());
            tvo.setFileVO(new FileVO(tvo.getFoodtruckName(), renamedFile));
            mypageDAO.registerFoodtruck(tvo);  //트럭정보 등록
            mypageDAO.saveFilePath(new FileVO(tvo.getFoodtruckNumber(), renamedFile));
            fm.uploadFile(truckFile,uploadPath+renamedFile);
         } catch (IOException e) {
            e.printStackTrace();
         } //서버에 전송
      }else{
         mypageDAO.registerFoodtruck(tvo);
         mypageDAO.saveFilePath(new FileVO(tvo.getFoodtruckNumber(), "defaultTruck.jpg"));
      }
   }

   @Override
   public TruckVO findtruckInfoByTruckNumber(String truckNumber) {
       TruckVO truckVO=mypageDAO.findtruckInfoByTruckNumber(truckNumber);
       truckVO.setAvgGrade(foodtruckDAO.findAvgGradeByTruckNumber(truckNumber));
       truckVO.setWishlistCount(foodtruckDAO.findWishlistCountByTruckNumber(truckNumber));
       return truckVO;
   }
   /**
    * 푸드트럭 설정을 업데이트한다.
    * 푸드트럭 정보 등록과 프로필 사진 변경
    */
   @Override
   public void updateMyfoodtruck(TruckVO truckVO, String uploadPath) {
      MultipartFile truckFile=truckVO.getFoodtruckFile(); 
      FileManager fm=new FileManager();
      String fileName=truckFile.getOriginalFilename();
      if(fileName.equals("")==false){
         try {
         String renamedFile=fm.rename(truckFile,truckVO.getFoodtruckNumber());
         truckVO.setFileVO(new FileVO(truckVO.getFoodtruckNumber(), renamedFile));
         mypageDAO.updateMyfoodtruck(truckVO);  //트럭정보 등록
         mypageDAO.updateFilePath(truckVO.getFileVO()); //파일경로 등록
            fm.uploadFile(truckFile, uploadPath+renamedFile);
         } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }else{
         mypageDAO.updateMyfoodtruck(truckVO); 
      }
   }
   @Override
   public String findtruckNumberBySellerId(String sellerId) {
      return mypageDAO.findtruckNumberBySellerId(sellerId);
   }
   @Override
   public List<FoodVO> showMenuList(String truckNumber) {
      return mypageDAO.showMenuList(truckNumber);
   }

   //메뉴 등록
   @Override
   public void registerMenuList(List<FoodVO> foodList, String truckNumber, String uploadPath) {
      FileManager fm=new FileManager();
      for(int i=0;i<foodList.size();i++){
         try{
            foodList.get(i).setFoodTruckNumber(truckNumber); //트럭넘버를 세팅
            foodList.get(i).setFileVO(new FileVO(truckNumber, "defaultMenu.jpg"));
            mypageDAO.registerMenu(foodList.get(i)); //메뉴를 등록한다.
            MultipartFile foodFile=foodList.get(i).getMenuFile(); //메뉴사진받아와서
            String renamedFile=fm.rename(foodFile,truckNumber+"_"+foodList.get(i).getMenuId()); //파일 이름 수정
            mypageDAO.updateMenuFilepath(new FileVO(foodList.get(i).getMenuId(), renamedFile)); //파일 경로 수정
            fm.uploadFile(foodFile, uploadPath+renamedFile); //서버에 파일 업로드
         }catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

   //메뉴수정
   @Override
   public void updateMenu(TruckVO truckVO, String uploadPath) {
      List<FoodVO> foodList=truckVO.getFoodList();
      for(int i=0;i<foodList.size();i++){
         try{
         String fileName=foodList.get(i).getMenuFile().getOriginalFilename();
         if(fileName.equals("")){ //파일이 없으면
            mypageDAO.updateMenu(foodList.get(i)); //메뉴정보만 수정
         }else{ //파일이 있으면
            FileManager fm=new FileManager();
            MultipartFile foodFile=foodList.get(i).getMenuFile(); //사진 받아서
            String renamedFile=fm.rename(foodFile,truckVO.getFoodtruckNumber()+"_"+foodList.get(i).getMenuId()); //파일 이름 수정
            foodList.get(i).setFileVO(new FileVO(foodList.get(i).getMenuId(),renamedFile));
            mypageDAO.updateMenu(foodList.get(i)); //메뉴정보 수정
            mypageDAO.updateMenuFilepath(foodList.get(i).getFileVO()); //파일 경로 수정
            fm.uploadFile(foodFile, uploadPath+renamedFile);
         }
         }catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

   @Override
   public void deleteMyTruck(String foodtruckNumber) {
      mypageDAO.deleteMyTruck(foodtruckNumber);
   }
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
   @Override
   public void updateMyReview(ReviewVO reviewVO) {
      mypageDAO.updateMyReview(reviewVO);
   }
   @Override
   public void deleteMyReview(String reviewNo) {
      mypageDAO.deleteMyReview(reviewNo);
   }
   @Override
   public ReviewVO findReviewInfoByReviewNo(String reviewNo) {
      return mypageDAO.findReviewInfoByReviewNo(reviewNo);
   }
   @Override
   public TruckVO getGPSInfo(String sellerId) {
      return mypageDAO.getGPSInfo(sellerId);
   }
   @Override
   public void setGPSInfo(TruckVO gpsInfo) {
      System.out.println(gpsInfo);

      if (gpsInfo.getLatitude() == 0.0)
      {
         System.out.println("leave");
         mypageDAO.leaveFoodtruck(gpsInfo);         
      }
         
      else
      {
         System.out.println("stay");
         mypageDAO.stayFoodtruck(gpsInfo);
      }
   }
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
   @Override
   public int getWishListFlag(String customerId, String foodtruckNumber) {
      WishlistVO wishlistVO=new WishlistVO(foodtruckNumber,customerId); 
      return mypageDAO.getWishListFlag(wishlistVO);
   }
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
@Override
public void updateBookingState(BookingVO bookingVO) {
	mypageDAO.updateBookingState(bookingVO);
	
}
/*@Override
public List<BookingVO> getBookingVO(String foodTruckNumber) {
	//List<BookingVO> list=mypageDAO.getBookingVO
	return mypageDAO.getBookingVO(foodTruckNumber);
}*/

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


@Override
public List<BookingDetailVO> getBookingDetailVO(BookingVO bookingVO) {
	return mypageDAO.getBookingDetailVO(bookingVO);
}
@Override
public void freeboardDeleteInMaypage(String contentNo) {
	mypageDAO.freeboardDeleteInMaypage(contentNo);
	
}
/*public List<BookingVO> getSellerBookingListByTruckNumber(String foodTruckNumber) {
	return mypageDAO.getSellerBookingListByTruckNumber(foodTruckNumber);
}*/
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
@Override
public void businessDeleteInMaypage(String contentNo) {
	mypageDAO.businessDeleteInMaypage(contentNo);
	
}
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
@Override
public void qnaDeleteInMaypage(String contentNo) {
	mypageDAO.qnaDeleteInMaypage(contentNo);
}
@Override
public int checkBookingState(String customerId) {
   return mypageDAO.checkBookingState(customerId);
}

@Override
public ListVO getPointListById(String id, String nowPage) {
	ListVO pointList=new ListVO();
	if(nowPage==null)
		nowPage="1";
	int totalList= mypageDAO.getTotalPointCountById(id);
	PagingBean pagingBean=new PagingBean(Integer.parseInt(nowPage), totalList, id);
	pointList.setPointList(mypageDAO.getPointListById(pagingBean));
	pointList.setPagingBean(pagingBean);
	return pointList;
}




}