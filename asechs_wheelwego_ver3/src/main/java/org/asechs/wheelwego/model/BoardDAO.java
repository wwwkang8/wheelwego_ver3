package org.asechs.wheelwego.model;

import java.util.List;

import org.asechs.wheelwego.model.vo.BoardVO;
import org.asechs.wheelwego.model.vo.CommentVO;
import org.asechs.wheelwego.model.vo.FileVO;
import org.asechs.wheelwego.model.vo.MemberVO;
import org.asechs.wheelwego.model.vo.PagingBean;

public interface BoardDAO {

	List<BoardVO> getFreeBoardList(PagingBean pagingBean);

	List<BoardVO> getBusinessInfoBoardList(PagingBean pagingBean);

	int getFreeBoardTotalContentCount();

	int getBusinessInfoBoardTotalContentCount();

	int getQnATotalContentCount();

	List<BoardVO> getQnABoardList(PagingBean pagingBean);

	BoardVO getFreeBoardDetail(String no);

	void freeboardDelete(String no);

	void updateHits(int hits);

	String freeboardWrite(BoardVO bvo);

	String updateBoard(BoardVO vo);

	MemberVO getNameById(BoardVO bvo);

	void updateHitsBusiness(int hits);

	BoardVO getBusinessBoardDetail(String no);

	void businessDelete(String no);

	void business_updateBoard(BoardVO vo);

	MemberVO business_getNameById(BoardVO bvo);

	void freeboardWriteFileUpload(BoardVO boardVO);

	List<FileVO> getFreeBoardFilePath(String no);

	String businessWrite(BoardVO bvo);

	void businessWriteFileUpload(BoardVO boardVO);

	List<FileVO> getBusinessFilePath(String no);

	String qnaWrite(BoardVO bvo);

	void qnaWriteFileUpload(BoardVO boardVO);

	List<FileVO> getqnaFilePath(String no);

	MemberVO qna_getNameById(BoardVO bvo);

	BoardVO getqnaBoardDetail(String no);


	void writeFreeboardComment(CommentVO cvo);

	List<CommentVO> getFreeboardCommentList(String no);

	void deleteFreeboardComment(CommentVO cvo);

	void freeboardUpdateFileUpload(BoardVO boardVO);

	void freeboardDeleteFile(String no);

	void businessDeleteFile(String contentNo);

	String businessupdateBoard(BoardVO vo);

	void qnaDeleteFile(String contentNo);

	String qnaupdateBoard(BoardVO vo);

	void qnaDelete(String no);

	void updateHitsqna(int hits);

	CommentVO getFreeboardComment(CommentVO cvo);

	void updateFreeboardComment(CommentVO cvo);

	List<CommentVO> getbusinessCommentList(String no);

	void deletebusinessComment(CommentVO cvo);

	void writebusinessComment(CommentVO cvo);

	CommentVO getbusinessComment(CommentVO cvo);

	void updatebusinessComment(CommentVO cvo);

	List<CommentVO> getqnaCommentList(String no);

	void writeqnaComment(CommentVO cvo);

	void deleteqnaComment(CommentVO cvo);

	CommentVO getqnaComment(CommentVO cvo);

	void updateqnaComment(CommentVO cvo);



}
