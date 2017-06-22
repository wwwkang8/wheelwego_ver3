package org.asechs.wheelwego.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.asechs.wheelwego.model.vo.BoardVO;
import org.asechs.wheelwego.model.vo.CommentVO;
import org.asechs.wheelwego.model.vo.FileVO;
import org.asechs.wheelwego.model.vo.ListVO;
import org.asechs.wheelwego.model.vo.MemberVO;
import org.asechs.wheelwego.model.vo.PagingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 자유게시판 게시물 목록 보기
	 * -------------------------------------------
	 * 코드 설명 : 자유 게시판의 게시물 목록을 페이지 번호와 함께 보여준다.
	 * 사용자가 처음 게시판에 접속 할 때는 페이지 번호는 null로 받아오고
	 * 페이지 번호가 2,3,4 등 null이 아닐 경우로 케이스를 나누어 페이징 빈 객체를 활용한다.
	 * 페이징 빈 객체에는 총 게시물 수와 페이지 번호를 넣어 준 후 DAO에 보낸다.
	 */
	@Override
	public ListVO getFreeBoardList(String pageNo) {
		int totalCount = boardDAO.getFreeBoardTotalContentCount();
		PagingBean pagingBean = null;
		if (pageNo == null)
			pagingBean = new PagingBean(totalCount, 1);
		else
			pagingBean = new PagingBean(totalCount, Integer.parseInt(pageNo));
		return new ListVO((List<BoardVO>) boardDAO.getFreeBoardList(pagingBean), pagingBean);
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 창업게시판 게시물 목록 보기
	 * -------------------------------------------
	 * 코드 설명 : 창업 게시판의 게시물 목록을 페이지 번호와 함께 보여준다.
	 * 사용자가 처음 게시판에 접속 할 때는 페이지 번호는 null로 받아오고
	 * 페이지 번호가 2,3,4 등 null이 아닐 경우로 케이스를 나누어 페이징 빈 객체를 활용한다.
	 * 페이징 빈 객체에는 총 게시물 수와 페이지 번호를 넣어 준 후 DAO에 보낸다.
	 */
	@Override
	public ListVO getBusinessInfoBoardList(String pageNo) {
		int totalCount = boardDAO.getBusinessInfoBoardTotalContentCount();
		PagingBean pagingBean = null;
		if (pageNo == null)
			pagingBean = new PagingBean(totalCount, 1);
		else
			pagingBean = new PagingBean(totalCount, Integer.parseInt(pageNo));
	
		return new ListVO((List<BoardVO>) boardDAO.getBusinessInfoBoardList(pagingBean), pagingBean);
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 질문답변게시판 게시물 목록 보기
	 * -------------------------------------------
	 * 코드 설명 : 질문답변 게시판의 게시물 목록을 페이지 번호와 함께 보여준다.
	 * 사용자가 처음 게시판에 접속 할 때는 페이지 번호는 null로 받아오고
	 * 페이지 번호가 2,3,4 등 null이 아닐 경우로 케이스를 나누어 페이징 빈 객체를 활용한다.
	 * 페이징 빈 객체에는 총 게시물 수와 페이지 번호를 넣어 준 후 DAO에 보낸다.
	 */
	@Override
	public ListVO getQnABoardList(String pageNo) {
		int totalCount = boardDAO.getQnATotalContentCount();
		PagingBean pagingBean = null;
		if (pageNo == null)
			pagingBean = new PagingBean(totalCount, 1);
		else
			pagingBean = new PagingBean(totalCount, Integer.parseInt(pageNo));
	
		return new ListVO((List<BoardVO>) boardDAO.getQnABoardList(pagingBean), pagingBean);
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
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 자유 게시판 글 등록
	 * ---------------------------------------
	 * 코드 설명 : 자유게시판에 글 등록과 파일업로드를 하기 위한 메서드이다.
	 * 글 등록 기능은 3가지의 메서드로 이루어집니다.
	 * freeboardWrite() : 글 정보를 등록하는 메서드입니다.  글번호, 글제목, 내용, 작성자 등의 정보를 데이터베이스에 등록합니다.
	 *  transferTo() : uploadPath(파일을 업로드할 경로), fileName을 이용하여 서버상에 MultipartFile 형식의 첨부 사진을 올려줍니다.
	 *  freeboardWriteFileUpload() : 첨부 사진 이름을 데이터 베이스에 저장하는 메서드입니다.
	 *  나중에 첨부 사진 이름과 경로를 이용하여 글 상세보기에서 사진을 불러올 수 있습니다.
	 */
	@Override
	public void freeboardWrite(BoardVO bvo, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		MemberVO mvo = (MemberVO) session.getAttribute("memberVO");
		bvo.setId(mvo.getId());
		// 글 정보먼저 insert한다.
		String contentNo = boardDAO.freeboardWrite(bvo);
		// 강정호. 파일 업로드. 컨트롤러에 넣기에는 너무 길어서 서비스에 넣었습니다.
		// 그 다음 파일 이름을 insert한다
		//String uploadPath="C:\\Users\\KOSTA\\git\\wheelwego\\asechs_wheelwego\\src\\main\\webapp\\resources\\img\\";
		String uploadPath=request.getSession().getServletContext().getRealPath("/resources/img/");
		List<MultipartFile> fileList=bvo.getFile();
		ArrayList<String> nameList=new ArrayList<String>();
		for(int i=0; i<fileList.size(); i++){
			if(fileList.isEmpty()==false){
				BoardVO boardVO=new BoardVO();
				FileVO fileVO=new FileVO();
				String fileName=fileList.get(i).getOriginalFilename();
				if(fileName.equals("")==false){
					try{
						fileList.get(i).transferTo(new File(uploadPath+fileName));
						fileVO.setNo(contentNo);
						fileVO.setFilepath(fileName);
						boardVO.setFileVO(fileVO);
						nameList.add(fileName);
						boardDAO.freeboardWriteFileUpload(boardVO);
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
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
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 댓글 등록
	 * --------------------------------
	 * 코드 설명 : Controller로부터 받아온 댓글 정보인 CommentVO를 DAO에 넘겨서
	 * 데이터 베이스에 insert 해주기 위한 메서드
	 */
	@Override
	public void writeFreeboardComment(CommentVO cvo) {
		boardDAO.writeFreeboardComment(cvo);
		
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 댓글 목록 가져오기
	 * -------------------------------------
	 * 코드 설명 : 자유 게시판 게시물의 댓글 목록을 불러온다
	 */
	@Override
	public List<CommentVO> getFreeboardCommentList(String no) {
		return boardDAO.getFreeboardCommentList(no);
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 댓글 삭제
	 * ---------------------------------
	 * 코드 설명 : 댓글을 삭제하는 메서드입니다. 게시물 번호와 댓글 번호를 사용하여 삭제합니다.
	 */
	@Override
	public void deleteFreeboardComment(CommentVO cvo) {
		boardDAO.deleteFreeboardComment(cvo);
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
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 댓글 불러오기
	 * ----------------------------------------
	 * 코드 설명 : 수정, 삭제시에 댓글을 불러온다
	 */
	@Override
	public CommentVO getFreeboardComment(CommentVO cvo) {
		return boardDAO.getFreeboardComment(cvo);
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 댓글 수정
	 * -----------------------------------------------------
	 * 코드 설명 : 사용자가 수정한 댓글정보를 Controller로부터 받아 업데이트 해주는 메서드입니다.
	 */
	@Override
	public void updateFreeboardComment(CommentVO cvo) {
		boardDAO.updateFreeboardComment(cvo);
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 댓글 목록 가져오기
	 * -------------------------------------
	 * 코드 설명 : 창업 게시판 게시물의 댓글 목록을 불러온다
	 */
	@Override
	public List<CommentVO> getbusinessCommentList(String no) {
		return boardDAO.getbusinessCommentList(no);
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 댓글 삭제
	 * ---------------------------------
	 * 코드 설명 : 댓글을 삭제하는 메서드입니다. 게시물 번호와 댓글 번호를 사용하여 삭제합니다.
	 */
	@Override
	public void deletebusinessComment(CommentVO cvo) {
		boardDAO.deletebusinessComment(cvo);
		
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 댓글 등록
	 * --------------------------------
	 * 코드 설명 : Controller로부터 받아온 댓글 정보인 CommentVO를 DAO에 넘겨서
	 * 데이터 베이스에 insert 해주기 위한 메서드
	 */
	@Override
	public void writebusinessComment(CommentVO cvo) {
		boardDAO.writebusinessComment(cvo);
		
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 댓글 불러오기
	 * ----------------------------------------
	 * 코드 설명 : 수정, 삭제시에 댓글을 불러온다
	 */
	@Override
	public CommentVO getbusinessComment(CommentVO cvo) {
		return boardDAO.getbusinessComment(cvo);
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 댓글 수정
	 * -------------------------------
	 * 코드 설명 : 댓글을 수정할 때 사용하는 메서드
	 */
	@Override
	public void updatebusinessComment(CommentVO cvo) {
		boardDAO.updatebusinessComment(cvo);
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 댓글 목록 가져오기
	 * -------------------------------------
	 * 코드 설명 : 질문답변 게시판 게시물의 댓글 목록을 불러온다
	 */
	@Override
	public List<CommentVO> getqnaCommentList(String no) {
		return boardDAO.getqnaCommentList(no);
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 댓글 등록
	 * --------------------------------
	 * 코드 설명 : Controller로부터 받아온 댓글 정보인 CommentVO를 DAO에 넘겨서
	 * 데이터 베이스에 insert 해주기 위한 메서드
	 */
	@Override
	public void writeqnaComment(CommentVO cvo) {
		boardDAO.writeqnaComment(cvo);
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 댓글 삭제
	 * ---------------------------------
	 * 코드 설명 : 댓글을 삭제하는 메서드입니다. 게시물 번호와 댓글 번호를 사용하여 삭제합니다.
	 */
	@Override
	public void deleteqnaComment(CommentVO cvo) {
		boardDAO.deleteqnaComment(cvo);
		
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 댓글 불러오기
	 * ----------------------------------------
	 * 코드 설명 : 수정, 삭제시에 댓글을 불러온다
	 */
	@Override
	public CommentVO getqnaComment(CommentVO cvo) {
		return boardDAO.getqnaComment(cvo);
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판 - 댓글 수정
	 * -------------------------------
	 * 코드 설명 : 댓글을 수정할 때 사용하는 메서드
	 */
	@Override
	public void updateqnaComment(CommentVO cvo) {
		boardDAO.updateqnaComment(cvo);
		
	}

	

}
