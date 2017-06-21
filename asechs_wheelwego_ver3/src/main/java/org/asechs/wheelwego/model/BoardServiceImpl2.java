package org.asechs.wheelwego.model;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.asechs.wheelwego.model.vo.BoardVO;
import org.asechs.wheelwego.model.vo.CommentVO;
import org.asechs.wheelwego.model.vo.FileVO;
import org.asechs.wheelwego.model.vo.ListVO;
import org.asechs.wheelwego.model.vo.MemberVO;
import org.springframework.stereotype.Service;
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
@Service
public class BoardServiceImpl2 implements BoardService {
	@Resource
	private BoardDAO boardDAO;

	@Override
	public ListVO getFreeBoardList(String pageNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListVO getBusinessInfoBoardList(String pageNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListVO getQnABoardList(String pageNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoardVO getFreeBoardDetail(String no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void freeboardDelete(String no) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateHits(int hits) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateHitsBusiness(int hits) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BoardVO getBusinessBoardDetail(String no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void businessDelete(String no) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void freeboardWrite(BoardVO bvo, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBoard(BoardVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MemberVO getNameById(BoardVO bvo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberVO business_getNameById(BoardVO bvo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FileVO> getFreeBoardFilePath(String no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void businessWrite(BoardVO bvo, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<FileVO> getBusinessFilePath(String no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void qnaWrite(BoardVO bvo, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<FileVO> getqnaFilePath(String no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberVO qna_getNameById(BoardVO bvo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoardVO getqnaBoardDetail(String no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeFreeboardComment(CommentVO cvo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CommentVO> getFreeboardCommentList(String no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteFreeboardComment(CommentVO cvo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void businessupdateBoard(BoardVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void qnaDelete(String no) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void qnaupdateBoard(BoardVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateHitsqna(int hits) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CommentVO getFreeboardComment(CommentVO cvo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateFreeboardComment(CommentVO cvo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CommentVO> getbusinessCommentList(String no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletebusinessComment(CommentVO cvo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writebusinessComment(CommentVO cvo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CommentVO getbusinessComment(CommentVO cvo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatebusinessComment(CommentVO cvo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CommentVO> getqnaCommentList(String no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeqnaComment(CommentVO cvo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteqnaComment(CommentVO cvo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CommentVO getqnaComment(CommentVO cvo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateqnaComment(CommentVO cvo) {
		// TODO Auto-generated method stub
		
	}

	

}
