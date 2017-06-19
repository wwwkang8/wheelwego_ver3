package org.asechs.wheelwego.model.vo;

import java.util.List;

/**
 * 게시물 리스트 정보와 페이징 정보를 가지고 있는 클래스
 * 
 * @author inst
 *
 */
public class ListVO {
	private List<BoardVO> boardList;
	private List<TruckVO> truckList;
	private List<ReviewVO> reviewList;
	private List<BookingVO> bookingNumberList;
	private List<BookingVO> bookingMenuList;
	private PagingBean pagingBean;
	private List<PointVO> pointList;
	public ListVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ListVO(List<BoardVO> boardList, PagingBean pagingBean) {
		super();
		this.boardList = boardList;
		this.pagingBean = pagingBean;
	}
	
	public ListVO(PagingBean pagingBean, List<TruckVO> truckList) {
		super();
		this.truckList = truckList;
		this.pagingBean = pagingBean;
	}
	

	/*	public ListVO(List<TruckVO> truckList, PagingBean pagingBean) {
		super();
		this.truckList = truckList;
		this.pagingBean = pagingBean;
	}
*/
	
	public List<BoardVO> getBoardList() {
		return boardList;
	}

	public List<PointVO> getPointList() {
		return pointList;
	}

	public void setPointList(List<PointVO> pointList) {
		this.pointList = pointList;
	}

	public void setBoardList(List<BoardVO> boardList) {
		this.boardList = boardList;
	}

	public List<TruckVO> getTruckList() {
		return truckList;
	}

	public void setTruckList(List<TruckVO> truckList) {
		this.truckList = truckList;
	}

	public PagingBean getPagingBean() {
		return pagingBean;
	}

	public void setPagingBean(PagingBean pagingBean) {
		this.pagingBean = pagingBean;
	}
	



	public List<ReviewVO> getReviewList() {
		return reviewList;
	}

	public void setReviewList(List<ReviewVO> reviewList) {
		this.reviewList = reviewList;
	}
	

	public List<BookingVO> getBookingNumberList() {
		return bookingNumberList;
	}

	public void setBookingNumberList(List<BookingVO> bookingNumberList) {
		this.bookingNumberList = bookingNumberList;
	}
	

	public List<BookingVO> getBookingMenuList() {
		return bookingMenuList;
	}

	public void setBookingMenuList(List<BookingVO> bookingMenuList) {
		this.bookingMenuList = bookingMenuList;
	}

	@Override
	public String toString() {
		return "ListVO [boardList=" + boardList + ", truckList=" + truckList + ", reviewList=" + reviewList
				+ ", bookingNumberList=" + bookingNumberList + ", pagingBean=" + pagingBean + ", pointList=" + pointList
				+ "]";
	}

	
	

}
