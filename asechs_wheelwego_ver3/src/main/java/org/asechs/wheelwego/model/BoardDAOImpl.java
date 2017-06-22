package org.asechs.wheelwego.model;

import java.util.List;

import javax.annotation.Resource;

import org.asechs.wheelwego.model.vo.BoardVO;
import org.asechs.wheelwego.model.vo.CommentVO;
import org.asechs.wheelwego.model.vo.FileVO;
import org.asechs.wheelwego.model.vo.MemberVO;
import org.asechs.wheelwego.model.vo.PagingBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
/**
 * 본인 이름
 *  수정 날짜 (수정 완료)
 * 대제목[마이페이지/푸드트럭/멤버/게시판/예약] - 소제목
 * ------------------------------------------------------
 * 코드설명
 * 
 * EX)
	박다혜
	 2017.06.21 (수정완료) / (수정중)
 	마이페이지 - 마이트럭설정
	---------------------------------
	~~~~~
  */
@Repository
public class BoardDAOImpl implements BoardDAO {
	@Resource
	SqlSessionTemplate template;
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 자유게시판 게시물 목록 보기
	 * -------------------------------------------
	 * 코드 설명 : 자유게시판 게시물 목록과 페이지 번호를 보여주기 위한 메서드이다.
	 * 페이징 빈 객체를 ServiceImpl에서 받아와 페이지 번호당 게시물 개수를 10개로 정하여
	 * 목록을 보여준다.
	 */
	@Override
	public List<BoardVO> getFreeBoardList(PagingBean pagingBean) {
		return template.selectList("board.getFreeBoardList", pagingBean);
	}


	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 게시물 총 개수 구하는 메서드
	 * --------------------------------------------------------------
	 * 코드 설명 : 게시판 게시물 총 개수를 구하는 메서드입니다.
	 */
	@Override
	public int getFreeBoardTotalContentCount() {
		return template.selectOne("board.getFreeBoardTotalContentCount");
	}
	
	@Override
	public BoardVO getFreeBoardDetail(String no) {
		return (BoardVO) template.selectOne("board.getFreeBoardDetail", no);
	}
	
	@Override
	public void freeboardDelete(String no) {
		template.delete("board.freeboardDelete", no);
	}
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 자유 게시판 글 등록
	 * ---------------------------------------
	 * 코드 설명 : 사용자의 글 정보(작성자, 글 제목, 내용)을 등록하는 메서드입니다.
	 * 데이터 베이스에 insert 시에 게시물 번호를 <selectKey>를 이용하여 
	 * ServiceImpl에 있는 freeboardWrite()로 리턴해준다.
	 * 리턴 해준 게시물 번호(시퀀스)를 이용하여 파일 업로드를 할 수 있다.
	 */
	@Override
	public String freeboardWrite(BoardVO bvo) {
		template.insert("board.freeboardWrite",bvo);
		return bvo.getNo();
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 자유 게시판 글 등록(첨부 사진 업로드 기능)
	 * ---------------------------------------
	 * 코드 설명 : boardVO에 있는 첨부 사진의 이름을 해당 게시물 번호를 이용하여
	 * 데이터 베이스에 insert 해준다. 
	 */
	@Override
	public void freeboardWriteFileUpload(BoardVO boardVO) {
		template.insert("board.freeboardWriteFileUpload", boardVO);
		
	}
	
	@Override
	public void updateHits(int hits) {
		template.update("board.updateCount", hits);
	}
	
	public String updateBoard(BoardVO vo) {
		template.update("board.updateBoard",vo);
		return vo.getNo();
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 게시물 상세보기 파일 경로 불러오기
	 * -----------------------------------------------------------
	 * 코드 설명 : 게시물 상세보기시 사진 파일 불러올 때 사진이 저장된 경로를 가져오는 메서드
	 */
	@Override
	public List<FileVO> getFreeBoardFilePath(String no) {
		return template.selectList("board.getFreeBoardFilePath",no);
	}

	@Override
	public MemberVO getNameById(BoardVO bvo) {
		return template.selectOne("board.getNameById", bvo);
	}
	
	@Override
	public void freeboardUpdateFileUpload(BoardVO boardVO) {
		template.update("board.freeboardUpdateFileUpload", boardVO);
		
	}
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 댓글 등록
	 * --------------------------------
	 * 코드 설명 : Controller, ServiceImpl로부터 넘겨받은 댓글 정보를 등록해주는 메서드입니다.
	 * 댓글의 경우 고유의 시퀀스 번호를 발급합니다.
	 */
	@Override
	public void writeFreeboardComment(CommentVO cvo) {
		template.insert("board.writeFreeboardComment",cvo);
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 댓글 리스트
	 * -----------------------------------------------------------
	 * 코드 설명 : 게시물 상세보기시 해당 게시물에 있는 댓글 목록 불러오는 메서드
	 */
	@Override
	public List<CommentVO> getFreeboardCommentList(String no) {
		return template.selectList("board.getFreeboardCommentList", no);
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시물 - 댓글 삭제
	 * -------------------------------------
	 * 코드 설명 : 게시물의 댓글을 삭제하는 메서드입니다.
	 */
	@Override
	public void deleteFreeboardComment(CommentVO cvo) {
		template.delete("board.deleteFreeboardComment",cvo);
		
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 특정 댓글 불러오는 메서드
	 * -----------------------------------------------------------
	 * 코드 설명 : 댓글 수정, 삭제시 특정 댓글 한 개만 불러오는 메서드
	 */
	@Override
	public CommentVO getFreeboardComment(CommentVO cvo) {
		return template.selectOne("board.getFreeboardComment",cvo);
	}
	/**
	 * 김호겸
	 * 2017.6.9 (수정 완료) 
	 *게시판-게시물 사진 수정시 선삭제
	 */
	@Override
	public void freeboardDeleteFile(String no) {
		template.delete("board.freeboardDeleteFile", no);
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시물- 댓글 수정
	 * ---------------------------------------
	 * 코드 설명 : 사용자가 수정한 댓글정보를 Service로부터 받아 업데이트 해주는 메서드입니다.
	 */
	@Override
	public void updateFreeboardComment(CommentVO cvo) {
		template.update("board.updateFreeboardComment",cvo);
	}
	
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 창업게시판 게시물 목록 보기
	 * -------------------------------------------
	 * 코드 설명 : 창업게시판 게시물 목록과 페이지 번호를 보여주기 위한 메서드이다.
	 * 페이징 빈 객체를 ServiceImpl에서 받아와 페이지 번호당 게시물 개수를 10개로 정하여
	 * 목록을 보여준다.
	 */
	@Override
	public List<BoardVO> getBusinessInfoBoardList(PagingBean pagingBean) {
		return template.selectList("board.getBusinessInfoBoardList", pagingBean);
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 게시물 총 개수 구하는 메서드
	 * --------------------------------------------------------------
	 * 코드 설명 : 게시판 게시물 총 개수를 구하는 메서드입니다.
	 */
	@Override
	public int getBusinessInfoBoardTotalContentCount() {
		return template.selectOne("board.getBusinessInfoBoardTotalContentCount");
	}
	

	@Override
	public void updateHitsBusiness(int hits) {
		template.update("board.updateHitsBusiness", hits);
	}

	@Override
	public BoardVO getBusinessBoardDetail(String no) {
		return template.selectOne("board.getBusinessBoardDetail", no);
	}

	@Override
	public void businessDelete(String no) {
		template.delete("board.businessDelete", no);
	}

	@Override
	public void business_updateBoard(BoardVO vo) {
		template.update("board.business_updateBoard", vo);
	}
	
	@Override
	public MemberVO business_getNameById(BoardVO bvo) {
		return template.selectOne("board.business_getNameById", bvo);
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 창업게시판 글 등록
	 * ---------------------------------------
	 * 코드 설명 : 사용자의 글 정보(작성자, 글 제목, 내용)을 등록하는 메서드입니다.
	 * 데이터 베이스에 insert 시에 게시물 번호를 <selectKey>를 이용하여 
	 * ServiceImpl에 있는 freeboardWrite()로 리턴해준다.
	 * 리턴 해준 게시물 번호(시퀀스)를 이용하여 파일 업로드를 할 수 있다.
	 */
	@Override
	public String businessWrite(BoardVO bvo) {
		template.insert("board.businessWrite",bvo);
		return bvo.getNo();
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 창업 게시판 글 등록(첨부 사진 업로드 기능)
	 * ---------------------------------------
	 * 코드 설명 : boardVO에 있는 첨부 사진의 이름을 해당 게시물 번호를 이용하여
	 * 데이터 베이스에 insert 해준다. 
	 */
	@Override
	public void businessWriteFileUpload(BoardVO boardVO) {
		template.insert("board.businessWriteFileUpload", boardVO);
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 게시물 상세보기 파일 경로 불러오기
	 * -----------------------------------------------------------
	 * 코드 설명 : 게시물 상세보기시 사진 파일 불러올 때 사진이 저장된 경로를 가져오는 메서드
	 */
	@Override
	public List<FileVO> getBusinessFilePath(String no) {
		 return template.selectList("board.getBusinessFilePath",no);
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 댓글 목록 불러오기
	 * --------------------------------------------
	 * 코드 설명 : 게시물 상세보기 페이지에서 댓글 목록을 불러오는 메서드입니다.
	 * 게시물 번호를 이용하여 불러옵니다
	 */
	@Override
	public List<CommentVO> getbusinessCommentList(String no) {
		return template.selectList("board.getbusinessCommentList",no);
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 댓글 삭제
	 * --------------------------------------------
	 * 코드 설명 : 댓글을 삭제하는 메서드
	 */
	@Override
	public void deletebusinessComment(CommentVO cvo) {
		template.delete("board.deletebusinessComment", cvo);
		
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 댓글 등록
	 * --------------------------------------------
	 * 코드 설명 : 댓글을 등록하는 메서드입니다
	 */
	@Override
	public void writebusinessComment(CommentVO cvo) {
		template.insert("board.writebusinessComment",cvo);
		
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 특정 댓글 1개 불러오기
	 * --------------------------------------------
	 * 코드 설명 : 댓글 수정, 삭제시 특정 댓글 1개를 불러오는 메서드입니다
	 */
	@Override
	public CommentVO getbusinessComment(CommentVO cvo) {
		return template.selectOne("board.getbusinessComment",cvo);
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 댓글 수정
	 * --------------------------------------------
	 * 코드 설명 : 댓글을 수정하는 메서드
	 */
	@Override
	public void updatebusinessComment(CommentVO cvo) {
		template.update("board.updatebusinessComment",cvo);
		
	}

	@Override
	public void businessDeleteFile(String no) {
		template.delete("board.businessDeleteFile", no);
		
	}

	@Override
	public String businessupdateBoard(BoardVO vo) {
		template.update("board.businessupdateBoard",vo);
		return vo.getNo();
	}

	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 게시물 총 개수 구하는 메서드
	 * --------------------------------------------------------------
	 * 코드 설명 : 게시판 게시물 총 개수를 구하는 메서드입니다.
	 */
	@Override
	public int getQnATotalContentCount() {
		return template.selectOne("board.getQnATotalContentCount");
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 질문답변 게시물 목록 보기
	 * -------------------------------------------
	 * 코드 설명 : 질문답변 게시물 목록과 페이지 번호를 보여주기 위한 메서드이다.
	 * 페이징 빈 객체를 ServiceImpl에서 받아와 페이지 번호당 게시물 개수를 10개로 정하여
	 * 목록을 보여준다.
	 */
	@Override
	public List<BoardVO> getQnABoardList(PagingBean pagingBean) {
		return template.selectList("board.getQnABoardList", pagingBean);
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 질문답변 게시판 글 등록
	 * ---------------------------------------
	 * 코드 설명 : 사용자의 글 정보(작성자, 글 제목, 내용)을 등록하는 메서드입니다.
	 * 데이터 베이스에 insert 시에 게시물 번호를 <selectKey>를 이용하여 
	 * ServiceImpl에 있는 freeboardWrite()로 리턴해준다.
	 * 리턴 해준 게시물 번호(시퀀스)를 이용하여 파일 업로드를 할 수 있다.
	 */
	@Override
	public String qnaWrite(BoardVO bvo) {
		template.insert("board.qnaWrite",bvo);
		return bvo.getNo();
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 질문답변 게시판 글 등록(첨부 사진 업로드 기능)
	 * ---------------------------------------
	 * 코드 설명 : boardVO에 있는 첨부 사진의 이름을 해당 게시물 번호를 이용하여
	 * 데이터 베이스에 insert 해준다. 
	 */
	@Override
	public void qnaWriteFileUpload(BoardVO boardVO) {
		template.insert("board.qnaWriteFileUpload",boardVO);
		
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 게시물 상세보기 파일 경로 불러오기
	 * -----------------------------------------------------------
	 * 코드 설명 : 게시물 상세보기시 사진 파일 불러올 때 사진이 저장된 경로를 가져오는 메서드
	 */
	@Override
	public List<FileVO> getqnaFilePath(String no) {
		 return template.selectList("board.getqnaFilePath",no);
	}
	
	@Override
	public MemberVO qna_getNameById(BoardVO bvo) {
		return template.selectOne("board.qna_getNameById", bvo);
	}

	@Override
	public BoardVO getqnaBoardDetail(String no) {
		return template.selectOne("board.getqnaBoardDetail", no);
	}

	@Override
	public void qnaDeleteFile(String no) {
		template.delete("board.qnaDeleteFile", no);
	}

	@Override
	public String qnaupdateBoard(BoardVO vo) {
		template.update("board.qnaupdateBoard",vo);
		return vo.getNo();
	}

	@Override
	public void qnaDelete(String no) {
		template.delete("board.qnaDelete", no);
		
	}

	@Override
	public void updateHitsqna(int hits) {
		template.update("board.updateHitsqna", hits);
		
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 댓글 목록 불러오기
	 * -----------------------------------------
	 * 코드 설명 : 특정 게시물 상세보기시 댓글 목록을 불러오는 메서드입니다
	 */
	@Override
	public List<CommentVO> getqnaCommentList(String no) {
		return template.selectList("board.getqnaCommentList",no);
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 댓글 등록
	 * --------------------------------
	 * 코드 설명 : 질문답변 게시판에 있는 게시물에 대해 댓글을 등록하는 메서드이다
	 */
	@Override
	public void writeqnaComment(CommentVO cvo) {
		template.insert("board.writeqnaComment",cvo);
		
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 댓글 삭제
	 * -------------------------------------
	 * 코드 설명 : 질문 답변 게시판에 있는 게시물의 댓글을 삭제하는 메서드이다
	 */
	@Override
	public void deleteqnaComment(CommentVO cvo) {
		template.delete("board.deleteqnaComment",cvo);
		
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 댓글 1개 불러오기
	 * ----------------------------------
	 * 코드 설명 : 댓글 수정, 삭제시 특정 댓글 1개만 불러오는 메서드
	 */
	@Override
	public CommentVO getqnaComment(CommentVO cvo) {
		return template.selectOne("board.getqnaComment",cvo);
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 댓글 수정
	 * ---------------------------
	 * 코드 설명 : 질문답변 게시판에서 사용자가 수정한 댓글 정보를 insert하는 메서드입니다
	 */
	@Override
	public void updateqnaComment(CommentVO cvo) {
		template.update("board.updateqnaComment",cvo);
		
	}

}
