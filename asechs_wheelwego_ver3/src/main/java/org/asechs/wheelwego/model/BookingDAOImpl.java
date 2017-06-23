package org.asechs.wheelwego.model;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.asechs.wheelwego.model.vo.BookingDetailVO;
import org.asechs.wheelwego.model.vo.BookingVO;
import org.asechs.wheelwego.model.vo.PagingBean;
import org.asechs.wheelwego.model.vo.PointVO;
import org.asechs.wheelwego.model.vo.TruckVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookingDAOImpl implements BookingDAO {
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;
	/**
	 * 정현지
	 * 2017.06.22 수정완료
	 * 예약 - 메뉴 예약하기
	 * -----------------------------
	 *  예약 정보를
	 *  1. booking 테이블에 booking_number, customer_id, booking_Date를 insert한다.
	 *  2. 예약정보로부터 상세 예약 정보리스트를 불러와 booking_number를 setting하고
	 *     사용자로부터 입력받은 ,menu_id,menu_quantity를 booking_number와 
	 *     함께 bookingDetail 테이블에 insert한다.
	 */
	@Override
	public void bookingMenu(BookingVO bookingVO) {
		sqlSessionTemplate.insert("booking.bookingMenu", bookingVO); //Booking table에 data insert
		List<BookingDetailVO> bookingDetailList=bookingVO.getBookingDetail();
		for(int i=0; i<bookingDetailList.size();i++){
			bookingDetailList.get(i).setBookingNumber(bookingVO.getBookingNumber());
			sqlSessionTemplate.insert("booking.bookingDetailMenu", bookingDetailList.get(i));
		}
	}

/*	   @Override
	   public List<BookingVO> getBookingListBySellerId(String id) {
	      return sqlSessionTemplate.selectList("foodtruck.getBookingListBySellerId", id);
	   }*/
	   /**
	    * 박다혜
	    * 2017.06.22 수정중
	    * 예약 - 사업자의 결제완료상태인 최근 예약번호 가져오기 (ajax)
	    * ----------------------------------------------------------------------
	    * 주문이 들어왔을 때 결제완료 상태이므로 
	    * 주문이 들어왔는지 알기위해 결제완료상태인 최근 예약번호를 가져온다.
	    */
	   @Override
	   public int getRecentlyBookingNumberBySellerId(String id) {
	      return sqlSessionTemplate.selectOne("booking.getRecentlyBookingNumberBySellerId", id);
	   }
	   /**
	    * 박다혜
	    * 2017.06.22 수정중
	    * 예약 - 사업자의 이전 예약번호 가져오기  (ajax)
	    * -------------------------------------------------------
	    * 예약 상태에 상관 없이 사업자의 최근 예약 번호를 가져온다.
	    */
	   @Override
	   public int getPreviousBookingNumberBySellerId(String id) {
	      return sqlSessionTemplate.selectOne("booking.getPreviousBookingNumberBySellerId", id);
	   }
	   /**
	    * 박다혜
	    * 2017.06.22 수정중
	    * 예약 - 예약번호에 해당하는 예약상태 가져오기  (ajax)
	    */
	   @Override
	   public String getBookingStateBybookingNumber(int bookingNumber) {
	      return sqlSessionTemplate.selectOne("booking.getBookingStateBybookingNumber", bookingNumber);
	   }
		/**
		 * 황윤상
		 * 2017.06.22 수정중
		 * 푸드트럭 - gps정보의 반경 1km 내 해당하는 푸드트럭 번호 리스트를 가져온다.
		 */
		 @Override
		   public List<String> getFoodtruckNumberList(TruckVO gpsInfo) {
		      return sqlSessionTemplate.selectList("booking.getFoodtruckNumberList", gpsInfo);
		   }

	    
	      /**
	       * 박다혜
	       * 2017.06.22 수정중
	       * 예약 - 사용자아이디에 해당하는 이전의 예약넘버 가져오기  (ajax)
	       * --------------------------------------------------------------------------
	       * 사용자의 아이디에 해당하는 결제완료상태인 이전의 예약번호를 조회하여 반환
	       */
	   @Override
	   public String getPreviousBookingNumberByCustomerId(String id) {
	      return sqlSessionTemplate.selectOne("booking.getPreviousBookingNumberByCustomerId", id);
	   }
	   /**      
	   정현지
	   2017.06.21 (수정완료)
	   마이페이지 - 나의 주문내역 리스트 (pagingBean 적용)
	   기능설명 : 나의 주문 내역 리스트에 pagingBean 적용
	   */
	   @Override
	   public List<BookingVO> customerBookingList(PagingBean pagingBean) {
	      return sqlSessionTemplate.selectList("booking.customerBookingList", pagingBean);
	   }
	   
	   /**      
	   정현지
	   2017.06.21 (수정완료)
	   마이페이지 - 나의 주문내역 리스트
	   기능설명 : 예약 번호를 통해 주문 내역을 가져온다
	   */
	   public List<BookingVO> getBookingList(int bookingNumber) {
	         return sqlSessionTemplate.selectList("booking.getBookingList", bookingNumber);
	      }
	   
	   /**
	    * 김래현,황윤상 2017.06.21 (수정완료)
	    * 마이페이지 - 포인트조회
	    * 기능설명 : 해당 아이디의 보유포인트를 조회한다.
	    */
	   @Override
	   public int getMyPoint(String customerId) {
	      return sqlSessionTemplate.selectOne("booking.getMyPoint", customerId);
	   }
	   /**
	    * 김래현,황윤상 2017.06.21 (수정완료)
	    * 마이페이지 - 포인트사용
	    * 기능설명 : 보유포인트중 사용할 포인트를 입력하고 보유포인트에서 차감한다.
	    */
	   @Override
	   public void minusPoint(HashMap<String, Integer> pointInfo) {
	      sqlSessionTemplate.insert("booking.minusPoint", pointInfo);
	   }

	   /**
	    * 김래현,황윤상 2017.06.21 (수정완료)
	    * 마이페이지 - 포인트적립
	    * 기능설명 : 선택한 음식리스트의 총액의 5%를 적립해준다
	    */
	   @Override
	   public void addPoint(HashMap<String, Object> pointInfo) {
	      sqlSessionTemplate.insert("booking.addPoint", pointInfo);
	   }
		/**
		 * 강정호
		 * 2017.06.21(수정완료)
		 * 마이페이지 - 주문 상태 업데이트
		 * ------------------------------------------
		 * 코드 설명 : 사업자 주문 내역에서 조리중 버튼, 조리 완료 버튼을 눌러 주문 상태를 업데이트
		 * 할 때 사용하는 메서드. BookingVO에 있는 예약 번호를 이용하여 업데이트 할 주문 내역을 찾는다.
		 */
		@Override
		public void updateBookingState(BookingVO bookingVO) {
			sqlSessionTemplate.update("booking.updateBookingState", bookingVO);

		}

	   /**
	    * 강정호
	    * 2017.06.21(수정완료)
	    * 마이페이지 - 사업자 주문 개수 가져오는 메서드
	    * ------------------------------------------------------------
	    * 코드설명 : 푸드 트럭 넘버를 이용하여 사업자가 받은 주문 개수를 받아온다.
	    */
	   @Override
	   public List<BookingVO> getBookingVO(String foodTruckNumber) {
	      return sqlSessionTemplate.selectList("booking.getBookingVOCount", foodTruckNumber);
	   }
	   /**
	    * 강정호
	    * 2017.06.21(수정완료)
	    * 마이페이지 - 사업자 주문 내역
	    * ---------------------------------------------------
	    * 코드 설명 : 푸드 트럭 번호를 이용하여 사업자가 받은 주문내역을 불러오는 메서드
	    */
	   @Override
	   public List<BookingVO> getBookingVO(PagingBean pagingBean) {
	      return sqlSessionTemplate.selectList("booking.getBookingVO", pagingBean);
	   }
	   /**
	    * 강정호
	    * 2017.06.21(수정완료)
	    * 마이페이지 - 사업자 주문 상세 메뉴 내역
	    * ---------------------------------------------------
	    * 코드 설명 : 사업자가 받은 주문의 상세 메뉴 내역(수량, 단가, 메뉴이름)을 불러오는 메서드
	    */
	   @Override
	   public List<BookingDetailVO> getBookingDetailVO(BookingVO bookingVO) {
	      return sqlSessionTemplate.selectList("booking.getBookingDetailVO", bookingVO);
	   }
	   /**
	    * 강정호
	    * 2017.06.21(수정완료)
	    * 마이페이지 - 일반회원 주문 내역 확인
	    * ----------------------------------------------------
	    * 코드 설명 : 일반회원의 예약 번호를 회원 아이디를 이용해 조회한다.
	    */
	   @Override
	   public String getBookingNumberByCustomerId(String id) {
	      return sqlSessionTemplate.selectOne("booking.getBookingNumberListByCustomerId", id);

	   }
	   /**
	    * 황윤상 
	    * 2017.06.22 수정완료
	    * 예약 - 사용자 아이디에 해당하는 선행주문이 있는지 체크
	    * ------------------------------------------------------------
	    * 결제 완료 상태인 선행주문이 있는지 체크하여 있다면1을 없다면 0을 반환한다.
	    */
	   @Override
	   public int checkBookingState(String customerId) {
	      return sqlSessionTemplate.selectOne("booking.checkBookingState", customerId);
	   }
	   /**
	    * 강정호
	    * 2017.06.21(수정완료)
	    * 마이페이지 - 주문 갯수 확인
	    * -------------------------------------------
	    * 코드 설명 : 주문 갯수를 확인하여 사업자, 일반회원의 주문내역에 페이징 빈을 적용할 때 사용한다
	    */
	   @Override
	   public int getTotalBookingCount(String foodTruckNumber) {
	      return sqlSessionTemplate.selectOne("booking.getTotalBookingCount", foodTruckNumber);
	   }
	   /**
	    * 박다혜
	    * 2017.06.21 수정완료
	    * 마이페이지 - 사용자 아이디에 해당하는 총 포인트 목록 수 반환
	    */
	   public int getTotalPointCountById(String id) {
	      return sqlSessionTemplate.selectOne("booking.getTotalPointCountById", id);
	   }
	   /**
	    * 박다혜
	    * 2017.06.21 수정완료
	    * 마이페이지-사용자 아이디와 현재페이지에 해당하는 포인트 내역 반환
	    */
	   @Override
	   public List<PointVO> getPointListById(PagingBean pagingBean) {
	      return sqlSessionTemplate.selectList("booking.getPointListById", pagingBean);
	   }
	   /**
	    * 정현지
	    * 2017.06.21 수정완료
	    * 예약 - 사용자아이디에 해당하는 총 예약 내역의 수 반환
	    */
	   @Override
	   public int getCustomerBookingListCount(String customerId) {
	      return sqlSessionTemplate.selectOne("booking.getCustomerBookingListCount", customerId);
	   }

		/** 	  
		정현지
		2017.06.22 (수정완료)
		마이페이지 - 나의 주문내역 리스트 푸드트럭 이름 가져오기
		기능설명 : step1) bookingNumber로 menuId 찾기
		*/
		@Override
		public List<String> findMenuIdByBookingNumber(String bookingNumber){
			return sqlSessionTemplate.selectList("booking.findMenuIdByBookingNumber", bookingNumber);
		}
	}

