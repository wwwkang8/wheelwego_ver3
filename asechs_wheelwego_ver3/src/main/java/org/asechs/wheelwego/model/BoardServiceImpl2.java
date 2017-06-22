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
	/**
	 * 김호겸
	 * 2017.06.21(수정완료)
	 * 자유게시판 - 상세보기
	 */
	@Override
	public BoardVO getFreeBoardDetail(String no) {
		return boardDAO.getFreeBoardDetail(no);
	}
	/**
	 * 김호겸
	 * 2017.06.21(수정완료)
	 * 자유게시판 - 자유게시판 게시물 삭제
	 */
	@Override
	public void freeboardDelete(String no) {
		boardDAO.freeboardDelete(no);
	}
	/**
	 * 김호겸
	 * 2017.06.21(수정완료)
	 * 자유게시판-게시글 볼때 조회수 올리기
	 */
		@Override
		public void updateHits(int hits) {
			boardDAO.updateHits(hits);
		}
		/**
		 * 김호겸
		 * 2017.06.21(수정완료)
		 * 창업게시판 - 게시판 게시물 조회수 올리기
		 */
		@Override
		public void updateHitsBusiness(int hits) {
			boardDAO.updateHitsBusiness(hits);
		}
		/**
		 * 김호겸
		 * 2017.06.21(수정완료)
		 * 창업게시판 - 상세보기
		 */
		@Override
		public BoardVO getBusinessBoardDetail(String no) {
			return boardDAO.getBusinessBoardDetail(no);
		}
		/**
		 * 김호겸
		 * 2017.06.21(수정완료)
		 * 창업게시판 - 게시판 게시물 삭제
		 */
		@Override
		public void businessDelete(String no) {
			boardDAO.businessDelete(no);
		}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 자유 게시판 글 등록
	 * ---------------------------------------
	 * 코드 설명 : 자유게시판에 글 등록과 파일업로드를 하기 위한 메서드이다.
	 * 글 등록 기능은 3가지의 메서드로 이루어집니다.
	 * freeboardWrite() : 글 정보를 등록하는 메서드입니다. 
	 * 글번호, 글제목, 내용, 작성자 등의 정보를 데이터베이스에 등록합니다.
	 *  transferTo() : uploadPath(파일을 업로드할 경로), fileName을 이용하여 
	 * 서버상에 MultipartFile 형식의 첨부 사진을 올려줍니다.
	 *  freeboardWriteFileUpload() : 첨부 사진 이름을 데이터 베이스에 저장하는 메서드입니다.
	 *  나중에 첨부 사진 이름과 경로를 이용하여 글 상세보기에서 사진을 불러올 수 있습니다.
	 */
	@Override
	public void freeboardWrite(BoardVO bvo, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		MemberVO mvo = (MemberVO) session.getAttribute("memberVO");
		bvo.setId(mvo.getId());
		String contentNo = boardDAO.freeboardWrite(bvo);
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
	/**
	 * 김호겸 작성
	 *  2017.6.9 (수정 완료)
	 * 자유게시판- 글 수정하기 & 사진 수정
	 * ------------------------------------------------------
	 * 사용자가 정의한 사진을 받아오고 수정한 글을 수정을 먼저한다.
	 * 글을 수정하면 해당 번호를 contentNo로 받고 사용자가 보낸 사진을 for 문을 돌려
	 * modityFile 에 저장시킨다.
	 * modityFile 이 존재하면 리스트에 저장하고 없으면 저장하지않는다.
	 * 그 후 리스트가 존재하면 그 기존 사진을 지우고 새로운 사진을 insert 한다.
	  */
	@Override
	public void updateBoard(BoardVO vo) {
		String uploadPath = "C:\\Users\\KOSTA\\git\\wheelwego\\asechs_wheelwego\\src\\main\\webapp\\resources\\img\\";
		List<MultipartFile> fileList = vo.getFile();
		String contentNo = boardDAO.updateBoard(vo);
		ArrayList<String> list=new ArrayList<String>();
		for(int i=0;i<vo.getFile().size();i++){
			String modityFile=vo.getFile().get(i).getOriginalFilename();
			if (!modityFile.equals("")){
				list.add(modityFile.trim());
			}else{
			}
		}
		if(list.isEmpty()){
			boardDAO.updateBoard(vo);
			return;
		}else{
			boardDAO.freeboardDeleteFile(contentNo);
			for (int i = 0; i < fileList.size(); i++) {
				if (fileList.isEmpty() == false) {
					String fileName = fileList.get(i).getOriginalFilename();
					BoardVO boardVO = new BoardVO();
					FileVO fileVO = new FileVO();
					if (fileName.equals("") == false) {
						try {
							fileList.get(i).transferTo(new File(uploadPath + fileName));
							fileVO.setNo(contentNo);
							fileVO.setFilepath(fileName);
							boardVO.setFileVO(fileVO);
							boardDAO.freeboardWriteFileUpload(boardVO);
						} catch (IllegalStateException | IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	/**
	 * 김호겸
	 * 2017.06.21(수정완료)
	 * 자유게시판 - 아이디에 해당하는 이름 갖고오기
	 */
	@Override
	public MemberVO getNameById(BoardVO bvo) {
		return boardDAO.getNameById(bvo);
	}
	/**
	 * 김호겸
	 * 2017.06.21(수정완료)
	 * 창업게시판 - 아이디에 해당하는 이름 갖고오기
	 */
	@Override
	public MemberVO business_getNameById(BoardVO bvo) {
		return boardDAO.business_getNameById(bvo);
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판- 게시물 상세보기 사진 불러오기
	 * ----------------------------------------------
	 * 코드 설명 : 게시판 게시물 상세보기시 사진 불러오는 경로 가져오는 메서드
	 */
	@Override
	public List<FileVO> getFreeBoardFilePath(String no) {
		return boardDAO.getFreeBoardFilePath(no);
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 창업 게시판 글 등록
	 * ---------------------------------------
	 * 코드 설명 : 창업게시판에 글 등록과 파일업로드를 하기 위한 메서드이다.
	 * 글 등록 기능은 3가지의 메서드로 이루어집니다.
	 * businessWrite() : 글 정보를 등록하는 메서드입니다.  글번호, 글제목, 내용, 작성자 등의 정보를 데이터베이스에 등록합니다.
	 *  transferTo() : uploadPath(파일을 업로드할 경로), fileName을 이용하여 서버상에 MultipartFile 형식의 첨부 사진을 올려줍니다.
	 *  businessWriteFileUpload() : 첨부 사진 이름을 데이터 베이스에 저장하는 메서드입니다.
	 *  나중에 첨부 사진 이름과 경로를 이용하여 글 상세보기에서 사진을 불러올 수 있습니다.
	 */
	@Override
	public void businessWrite(BoardVO bvo, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		MemberVO mvo = (MemberVO) session.getAttribute("memberVO");
		bvo.setId(mvo.getId());
		String contentNo = boardDAO.businessWrite(bvo);

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
						boardDAO.businessWriteFileUpload(boardVO);
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
					}
				}
			}
		}	
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판- 게시물 상세보기 사진 불러오기
	 * ----------------------------------------------
	 * 코드 설명 : 게시판 게시물 상세보기시 사진 불러오는 경로 가져오는 메서드
	 */
	@Override
	public List<FileVO> getBusinessFilePath(String no) {
		return boardDAO.getBusinessFilePath(no);
	}
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 질문답변 게시판 글 등록
	 * ---------------------------------------
	 * 코드 설명 : 질문답변게시판에 글 등록과 파일업로드를 하기 위한 메서드이다.
	 * 글 등록 기능은 3가지의 메서드로 이루어집니다.
	 * qnaWrite() : 글 정보를 등록하는 메서드입니다.  글번호, 글제목, 내용, 작성자 등의 정보를 데이터베이스에 등록합니다.
	 *  transferTo() : uploadPath(파일을 업로드할 경로), fileName을 이용하여 서버상에 MultipartFile 형식의 첨부 사진을 올려줍니다.
	 *  qnaWriteFileUpload() : 첨부 사진 이름을 데이터 베이스에 저장하는 메서드입니다.
	 *  나중에 첨부 사진 이름과 경로를 이용하여 글 상세보기에서 사진을 불러올 수 있습니다.
	 */
	@Override
	public void qnaWrite(BoardVO bvo, HttpServletRequest request) {
		HttpSession session=request.getSession(false);
		MemberVO mvo=(MemberVO) session.getAttribute("memberVO");
		bvo.setId(mvo.getId());
		String contentNo=boardDAO.qnaWrite(bvo);
		
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
						boardDAO.qnaWriteFileUpload(boardVO);
					}catch(IllegalStateException | IOException e){
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * 강정호
	 * 2017.06.22(수정완료)
	 * 게시판- 게시물 상세보기 사진 불러오기
	 * ----------------------------------------------
	 * 코드 설명 : 게시판 게시물 상세보기시 사진 불러오는 경로 가져오는 메서드
	 */
	@Override
	public List<FileVO> getqnaFilePath(String no) {
		return boardDAO.getqnaFilePath(no);
	}
	/**
	 * 김호겸
	 * 2017.06.21(수정완료)
	 * QnA게시판 - 아이디에 해당하는 이름 갖고오기
	 */
	@Override
	public MemberVO qna_getNameById(BoardVO bvo) {
		return boardDAO.qna_getNameById(bvo);
	}
	/**
	 * 김호겸
	 * 2017.06.21(수정완료)
	 * QnA게시판 - 상세보기
	 */
	@Override
	public BoardVO getqnaBoardDetail(String no) {
		return boardDAO.getqnaBoardDetail(no);
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

	/**
	 * 김호겸 작성
	 *  2017.6.9 (수정 완료)
	 * 창업게시판- 글 수정하기 & 사진 수정
	 * ------------------------------------------------------
	 * 사용자가 정의한 사진을 받아오고 수정한 글을 수정을 먼저한다.
	 * 글을 수정하면 해당 번호를 contentNo로 받고 사용자가 보낸 사진을 for 문을 돌려
	 * modityFile 에 저장시킨다.
	 * modityFile 이 존재하면 리스트에 저장하고 없으면 저장하지않는다.
	 * 그 후 리스트가 존재하면 그 기존 사진을 지우고 새로운 사진을 insert 한다.
	  */
	@Override
	public void businessupdateBoard(BoardVO vo) {
		String uploadPath = "C:\\Users\\KOSTA\\git\\wheelwego\\asechs_wheelwego\\src\\main\\webapp\\resources\\img\\";
		List<MultipartFile> fileList = vo.getFile();
		String contentNo = boardDAO.businessupdateBoard(vo);
		ArrayList<String> list=new ArrayList<String>();
		for(int i=0;i<vo.getFile().size();i++){
			String modityFile=vo.getFile().get(i).getOriginalFilename();
			if (!modityFile.equals("")){
				list.add(modityFile.trim());
			}else{
			}
		}
		if(list.isEmpty()){
			boardDAO.businessupdateBoard(vo);
			return;
		}else{
			boardDAO.businessDeleteFile(contentNo);
			for (int i = 0; i < fileList.size(); i++) {
				if (fileList.isEmpty() == false) {
					String fileName = fileList.get(i).getOriginalFilename();
					BoardVO boardVO = new BoardVO();
					FileVO fileVO = new FileVO();
					if (fileName.equals("") == false) {
						try {
							fileList.get(i).transferTo(new File(uploadPath + fileName));
							fileVO.setNo(contentNo);
							fileVO.setFilepath(fileName);
							boardVO.setFileVO(fileVO);
							boardDAO.businessWriteFileUpload(boardVO);
						} catch (IllegalStateException | IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	/**
	 * 김호겸
	 * 2017.06.21(수정완료)
	 * QnA게시판 - 게시판 게시물 삭제
	 */
	@Override
	public void qnaDelete(String no) {
		boardDAO.qnaDelete(no);
	}
	
	/**
	 * 김호겸 작성
	 *  2017.6.9 (수정 완료)
	 * QnA게시판- 글 수정하기 & 사진 수정
	 * ------------------------------------------------------
	 * 사용자가 정의한 사진을 받아오고 수정한 글을 수정을 먼저한다.
	 * 글을 수정하면 해당 번호를 contentNo로 받고 사용자가 보낸 사진을 for 문을 돌려
	 * modityFile 에 저장시킨다.
	 * modityFile 이 존재하면 리스트에 저장하고 없으면 저장하지않는다.
	 * 그 후 리스트가 존재하면 그 기존 사진을 지우고 새로운 사진을 insert 한다.
	  */
	@Override
	public void qnaupdateBoard(BoardVO vo) {
		String uploadPath = "C:\\Users\\KOSTA\\git\\wheelwego\\asechs_wheelwego\\src\\main\\webapp\\resources\\img\\";
		List<MultipartFile> fileList = vo.getFile();
		String contentNo = boardDAO.qnaupdateBoard(vo);
		ArrayList<String> list=new ArrayList<String>();
		for(int i=0;i<vo.getFile().size();i++){
			String modityFile=vo.getFile().get(i).getOriginalFilename();
			if (!modityFile.equals("")){
				list.add(modityFile.trim());
			}else{
			}
		}
		if(list.isEmpty()){
			boardDAO.qnaupdateBoard(vo);
			return;
		}else{
			boardDAO.qnaDeleteFile(contentNo);
			for (int i = 0; i < fileList.size(); i++) {
				if (fileList.isEmpty() == false) {
					String fileName = fileList.get(i).getOriginalFilename();
					BoardVO boardVO = new BoardVO();
					FileVO fileVO = new FileVO();
					if (fileName.equals("") == false) {
						try {
							fileList.get(i).transferTo(new File(uploadPath + fileName));
							fileVO.setNo(contentNo);
							fileVO.setFilepath(fileName);
							boardVO.setFileVO(fileVO);
							boardDAO.qnaWriteFileUpload(boardVO);
						} catch (IllegalStateException | IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	/**
	 * 김호겸
	 * 2017.06.21(수정완료)
	 * QnA게시판 - 게시물 조회수 올리기
	 */
	@Override
	public void updateHitsqna(int hits) {
		boardDAO.updateHitsqna(hits);
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
