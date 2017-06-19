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

@Repository
public class BoardDAOImpl implements BoardDAO {
	@Resource
	SqlSessionTemplate template;
	
//////////강정호. 자유게시판 Service/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public List<BoardVO> getFreeBoardList(PagingBean pagingBean) {
		return template.selectList("board.getFreeBoardList", pagingBean);
	}



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
	
	@Override
	public String freeboardWrite(BoardVO bvo) {
		template.insert("board.freeboardWrite",bvo);
		//template 메서드 써서 가져온 게시물 번호와 파일명을 insert바로 하기
		//여기서 bvo.getNo을 int로 넘겨주기
		return bvo.getNo();
	}
	
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
	
	@Override
	public void writeFreeboardComment(CommentVO cvo) {
		template.insert("board.writeFreeboardComment",cvo);
	}
	
	@Override
	public List<CommentVO> getFreeboardCommentList(String no) {
		return template.selectList("board.getFreeboardCommentList", no);
	}
	
	@Override
	public void deleteFreeboardComment(CommentVO cvo) {
		template.delete("board.deleteFreeboardComment",cvo);
		
	}
	
	@Override
	public CommentVO getFreeboardComment(CommentVO cvo) {
		return template.selectOne("board.getFreeboardComment",cvo);
	}

	//게시물 사진 수정시 선삭제
	@Override
	public void freeboardDeleteFile(String no) {
		template.delete("board.freeboardDeleteFile", no);
	}

	@Override
	public void updateFreeboardComment(CommentVO cvo) {
		template.update("board.updateFreeboardComment",cvo);

		
	}
	
	
//////////강정호. 창업게시판 Service/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public List<BoardVO> getBusinessInfoBoardList(PagingBean pagingBean) {
		return template.selectList("board.getBusinessInfoBoardList", pagingBean);
	}
	
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
	
	@Override
	public String businessWrite(BoardVO bvo) {
		template.insert("board.businessWrite",bvo);
		return bvo.getNo();
	}
	
	@Override
	public void businessWriteFileUpload(BoardVO boardVO) {
		template.insert("board.businessWriteFileUpload", boardVO);
	}
	
	@Override
	public List<FileVO> getBusinessFilePath(String no) {
		 return template.selectList("board.getBusinessFilePath",no);
	}
	
	@Override
	public List<CommentVO> getbusinessCommentList(String no) {
		return template.selectList("board.getbusinessCommentList",no);
	}
	
	@Override
	public void deletebusinessComment(CommentVO cvo) {
		template.delete("board.deletebusinessComment", cvo);
		
	}
	
	@Override
	public void writebusinessComment(CommentVO cvo) {
		template.insert("board.writebusinessComment",cvo);
		
	}
	
	@Override
	public CommentVO getbusinessComment(CommentVO cvo) {
		return template.selectOne("board.getbusinessComment",cvo);
	}
	
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


//////////강정호. Q&A게시판 Service/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public int getQnATotalContentCount() {
		return template.selectOne("board.getQnATotalContentCount");
	}
	
	@Override
	public List<BoardVO> getQnABoardList(PagingBean pagingBean) {
		return template.selectList("board.getQnABoardList", pagingBean);
	}
	

	@Override
	public String qnaWrite(BoardVO bvo) {
		template.insert("board.qnaWrite",bvo);
		return bvo.getNo();
	}

	@Override
	public void qnaWriteFileUpload(BoardVO boardVO) {
		template.insert("board.qnaWriteFileUpload",boardVO);
		
	}

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


	@Override
	public List<CommentVO> getqnaCommentList(String no) {
		return template.selectList("board.getqnaCommentList",no);
	}



	@Override
	public void writeqnaComment(CommentVO cvo) {
		template.insert("board.writeqnaComment",cvo);
		
	}



	@Override
	public void deleteqnaComment(CommentVO cvo) {
		template.delete("board.deleteqnaComment",cvo);
		
	}



	@Override
	public CommentVO getqnaComment(CommentVO cvo) {
		return template.selectOne("board.getqnaComment",cvo);
	}



	@Override
	public void updateqnaComment(CommentVO cvo) {
		template.update("board.updateqnaComment",cvo);
		
	}


}
