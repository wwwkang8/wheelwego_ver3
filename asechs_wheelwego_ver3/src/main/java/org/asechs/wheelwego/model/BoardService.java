package org.asechs.wheelwego.model;


import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.asechs.wheelwego.model.vo.BoardVO;
import org.asechs.wheelwego.model.vo.CommentVO;
import org.asechs.wheelwego.model.vo.FileVO;
import org.asechs.wheelwego.model.vo.ListVO;
import org.asechs.wheelwego.model.vo.MemberVO;

public interface BoardService {

	ListVO getFreeBoardList(String pageNo);

	ListVO getBusinessInfoBoardList(String pageNo);

	ListVO getQnABoardList(String pageNo);

	BoardVO getFreeBoardDetail(String no);

	void freeboardDelete(String no);

	void updateHits(int hits);

	void updateHitsBusiness(int hits);

	BoardVO getBusinessBoardDetail(String no);

	void businessDelete(String no);

	void freeboardWrite(BoardVO bvo, HttpServletRequest request);

	void updateBoard(BoardVO vo);

	MemberVO getNameById(BoardVO bvo);

	MemberVO business_getNameById(BoardVO bvo);

	List<FileVO> getFreeBoardFilePath(String no);

	void businessWrite(BoardVO bvo, HttpServletRequest request);

	List<FileVO> getBusinessFilePath(String no);

	void qnaWrite(BoardVO bvo, HttpServletRequest request);

	List<FileVO> getqnaFilePath(String no);

	MemberVO qna_getNameById(BoardVO bvo);

	BoardVO getqnaBoardDetail(String no);

	void writeFreeboardComment(CommentVO cvo);

	List<CommentVO> getFreeboardCommentList(String no);

	void deleteFreeboardComment(CommentVO cvo);

	void businessupdateBoard(BoardVO vo);

	void qnaDelete(String no);

	void qnaupdateBoard(BoardVO vo);

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
