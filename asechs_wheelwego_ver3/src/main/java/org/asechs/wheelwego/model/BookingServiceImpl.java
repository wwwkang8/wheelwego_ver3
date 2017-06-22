package org.asechs.wheelwego.model;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.asechs.wheelwego.model.vo.BookingDetailVO;
import org.asechs.wheelwego.model.vo.BookingVO;
import org.asechs.wheelwego.model.vo.ListVO;
import org.asechs.wheelwego.model.vo.PagingBean;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {
	@Resource
	private BookingDAO bookingDAO;
	/** 	  
	정현지
	2017.06.22 (수정완료)
	마이페이지 - 나의 주문내역 리스트 푸드트럭 이름 가져오기
	기능설명 : step1) bookingNumber로 menuId 찾기
	*/
   @Override
	public List<String> findMenuIdByBookingNumber(String bookingNumber){
		return bookingDAO.findMenuIdByBookingNumber(bookingNumber);
	}

	/** 	  
	정현지
	2017.06.21 (수정완료)
	마이페이지 - 나의 주문내역 리스트 (pagingBean 적용)
	기능설명 : 나의 주문 내역 리스트에 pagingBean 적용
	*/
  @Override
  public ListVO customerBookingList(String pageNo, String customerId) {
	      int totalCount = bookingDAO.getCustomerBookingListCount(customerId);
	      PagingBean pagingBean = null;
	      if (pageNo == null)
	         pagingBean = new PagingBean(totalCount);
	      else
	         pagingBean = new PagingBean(totalCount, Integer.parseInt(pageNo));	      
	      pagingBean.setContentNumberPerPage(6);
	      pagingBean.setCustomerId(customerId);      
	      ListVO listVO = new ListVO();
	      listVO.setPagingBean(pagingBean);  
	      listVO.setBookingMenuList(bookingDAO.customerBookingList(pagingBean));
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
	    return bookingDAO.getBookingList(bookingNumber);
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
			bookingDAO.minusPoint(pointInfo);
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
			bookingDAO.addPoint(pointInfo);
		}

		/**
		 * 김래현 황윤상
		   2017.06.21 수정완료
	 	    마이페이지-보유포인트 조회
		 */
		@Override
		public int getMyPoint(String customerId) {
			return bookingDAO.getMyPoint(customerId);
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
			bookingDAO.updateBookingState(bookingVO);
			
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
			List<BookingVO> list=bookingDAO.getBookingVO(foodTruckNumber);
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
			listVO.setBookingNumberList(bookingDAO.getBookingVO(pagingBean));
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
			return bookingDAO.getBookingDetailVO(bookingVO);
		}

		/**
		 * 황윤상
		 * 2017.06.22 수정완료
		 * 예약 - 사용자 아이디에 해당하는 선행주문이 있는지 체크
		 */
		@Override
		public int checkBookingState(String customerId) {
			return bookingDAO.checkBookingState(customerId);
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
			PagingBean pagingBean=new PagingBean(Integer.parseInt(nowPage), bookingDAO.getTotalPointCountById(id), id);
			pointList.setPointList(bookingDAO.getPointListById(pagingBean));
			pointList.setPagingBean(pagingBean);
			return pointList;
		}

		/**
		 * 정현지
		 * 2017.06.22 수정완료
		 * 예약 - 메뉴 예약하기
		 */
		@Override
		public void bookingMenu(BookingVO bookingVO) {
			bookingDAO.bookingMenu(bookingVO);		
		}

		/**
		 * 박다혜
		 * 2017.06.22 수정완료
		 * 예약 - 사업자의 최근 예약번호 가져오기 (ajax)
		 * -----------------------------------------------------
		 * 주문이 들어왔을 때 결제완료 상태이므로 
		 * 주문이 들어왔는지 알기위해 결제완료상태인 최근 예약번호를 가져온다.
		 */
		@Override
		public int getRecentlyBookingNumberBySellerId(String id) {
			return bookingDAO.getRecentlyBookingNumberBySellerId(id);
		}
		/**
		 * 박다혜
		 * 2017.06.22 수정완료
		 * 예약 - 사업자의 이전 예약번호 가져오기  (ajax)
		 * -------------------------------------------------------------------
		 * 예약 상태에 상관 없이 사업자의 최근 예약 번호를 가져온다.
		 */
		@Override
		public int getPreviousBookingNumberBySellerId(String id) {
			return bookingDAO.getPreviousBookingNumberBySellerId(id);
		}

		/**
		 * 박다혜
		 * 2017.06.22 수정완료
		 * 예약 - 예약번호에 해당하는 예약상태 가져오기  (ajax)
		 */
		@Override
		public String getBookingStateBybookingNumber(String bookingNumber) {
			return bookingDAO.getBookingStateBybookingNumber(Integer.parseInt(bookingNumber));
		}

		/**
		 * 박다혜
		 * 2017.06.22 수정완료
		 * 예약 - 사용자아이디에 해당하는 결제 완료 상태의 이전 예약넘버 가져오기  (ajax)
		 */
		@Override
		public String getPreviousBookingNumberByCustomerId(String id) {
			return bookingDAO.getPreviousBookingNumberByCustomerId(id);
		}
		

}
