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

@Service
public class BoardServiceImpl implements BoardService {
	@Resource
	private BoardDAO boardDAO;

	////////// 강정호. 자유게시판
	////////// Service/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
		// 사진을 받아오고
		List<MultipartFile> fileList = vo.getFile();
		// 글 수정을 먼저 한다
		String contentNo = boardDAO.updateBoard(vo);
		ArrayList<String> list=new ArrayList<String>();
		for(int i=0;i<vo.getFile().size();i++){
			String modityFile=vo.getFile().get(i).getOriginalFilename();
			if (!modityFile.equals(""))
				list.add(modityFile.trim());
			else
			System.out.println("list isEmpty");
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
							// 파일 넘버 지정
							fileVO.setNo(contentNo);
							// 파일 경로 지정
							fileVO.setFilepath(fileName);
							// 보드VO 에 파일VO 저장
							boardVO.setFileVO(fileVO);
							// 새로운 사진 서버에 업데이트
							boardDAO.freeboardWriteFileUpload(boardVO);
						} catch (IllegalStateException | IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	@Override
	public MemberVO getNameById(BoardVO bvo) {
		return boardDAO.getNameById(bvo);
	}

	@Override
	public BoardVO getFreeBoardDetail(String no) {
		return boardDAO.getFreeBoardDetail(no);
	}

	@Override
	public void freeboardDelete(String no) {
		boardDAO.freeboardDelete(no);
	}

	// 자유게시판 조회수 업데이트
	@Override
	public void updateHits(int hits) {
		boardDAO.updateHits(hits);
	}

	@Override
	public List<FileVO> getFreeBoardFilePath(String no) {
		return boardDAO.getFreeBoardFilePath(no);
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
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 댓글 불러오기
	 * ----------------------------------------
	 * 코드 설명 : 수정, 삭제시에 댓글
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

	////////// 강정호. 창업게시판
	////////// Service/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*-------------------창업게시판-------------------------------------------------*/

	@Override
	public ListVO getBusinessInfoBoardList(String pageNo) {
		int totalCount = boardDAO.getBusinessInfoBoardTotalContentCount();
		PagingBean pagingBean = null;
		if (pageNo == null)
			pagingBean = new PagingBean(totalCount, 1);
		else
			pagingBean = new PagingBean(totalCount, Integer.parseInt(pageNo));
		/*
		 * HashMap<String,Integer> paramMap=new HashMap<String,Integer>();
		 * paramMap.put("startRowNumber",pagingBean.getStartRowNumber());
		 * paramMap.put("endRowNumber", pagingBean.getEndRowNumber());
		 */
		return new ListVO((List<BoardVO>) boardDAO.getBusinessInfoBoardList(pagingBean), pagingBean);
	}

	@Override
	public void businessWrite(BoardVO bvo, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		MemberVO mvo = (MemberVO) session.getAttribute("memberVO");
		bvo.setId(mvo.getId());
		// 글 정보먼저 insert한다.
		String contentNo = boardDAO.businessWrite(bvo);

		// 강정호. 파일 업로드. 컨트롤러에 넣기에는 너무 길어서 서비스에 넣었습니다.
		// 그 다음 파일 이름을 insert한다
		//String uploadPath="C:\\Users\\KOSTA\\git\\wheelwego\\asechs_wheelwego\\src\\main\\webapp\\resources\\img\\";
		String uploadPath=request.getSession().getServletContext().getRealPath("/resources/img/");
		List<MultipartFile> fileList=bvo.getFile();
		//ArrayList<String> filePath=new ArrayList<String>();
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
						// filePath.add(uploadPath+fileName);
						boardDAO.businessWriteFileUpload(boardVO);
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	@Override
	public void updateHitsBusiness(int hits) {
		boardDAO.updateHitsBusiness(hits);
	}

	@Override
	public BoardVO getBusinessBoardDetail(String no) {
		return boardDAO.getBusinessBoardDetail(no);
	}

	@Override
	public void businessDelete(String no) {
		boardDAO.businessDelete(no);
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
		List<MultipartFile> fileList = vo.getFile();// 사진을 받아오고
		// 글 수정을 먼저 한다
		String contentNo = boardDAO.businessupdateBoard(vo);
		ArrayList<String> list=new ArrayList<String>();
		for(int i=0;i<vo.getFile().size();i++){
			String modityFile=vo.getFile().get(i).getOriginalFilename();
			if (!modityFile.equals(""))
				list.add(modityFile.trim());
			else
			System.out.println("list isEmpty");
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
							// 파일 넘버 지정
							fileVO.setNo(contentNo);
							// 파일 경로 지정
							fileVO.setFilepath(fileName);
							// 보드VO 에 파일VO 저장
							boardVO.setFileVO(fileVO);
							// 새로운 사진 서버에 업데이트
							boardDAO.businessWriteFileUpload(boardVO);
						} catch (IllegalStateException | IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	@Override
	public MemberVO business_getNameById(BoardVO bvo) {
		return boardDAO.business_getNameById(bvo);
	}

	@Override
	public List<FileVO> getBusinessFilePath(String no) {
		return boardDAO.getBusinessFilePath(no);
	}

	@Override
	public List<CommentVO> getbusinessCommentList(String no) {
		return boardDAO.getbusinessCommentList(no);
	}

	@Override
	public void writebusinessComment(CommentVO cvo) {
		boardDAO.writebusinessComment(cvo);

	}

	@Override
	public void deletebusinessComment(CommentVO cvo) {
		boardDAO.deletebusinessComment(cvo);

	}

	@Override
	public CommentVO getbusinessComment(CommentVO cvo) {
		return boardDAO.getbusinessComment(cvo);
	}

	@Override
	public void updatebusinessComment(CommentVO cvo) {
		boardDAO.updatebusinessComment(cvo);
	}

	////////// 강정호. Q&A게시판
	////////// Service/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public ListVO getQnABoardList(String pageNo) {
		int totalCount = boardDAO.getQnATotalContentCount();
		PagingBean pagingBean = null;
		if (pageNo == null)
			pagingBean = new PagingBean(totalCount, 1);
		else
			pagingBean = new PagingBean(totalCount, Integer.parseInt(pageNo));
		/*
		 * HashMap<String,Integer> paramMap=new HashMap<String,Integer>();
		 * paramMap.put("startRowNumber",pagingBean.getStartRowNumber());
		 * paramMap.put("endRowNumber", pagingBean.getEndRowNumber());
		 */
		return new ListVO((List<BoardVO>) boardDAO.getQnABoardList(pagingBean), pagingBean);
	}
	//강정호. Q&A 글 등록
		@Override
		public void qnaWrite(BoardVO bvo, HttpServletRequest request) {
			HttpSession session=request.getSession(false);
			MemberVO mvo=(MemberVO) session.getAttribute("memberVO");
			bvo.setId(mvo.getId());
			// 글 정보먼저 insert한다.
			String contentNo=boardDAO.qnaWrite(bvo);
			
			// 강정호. 파일 업로드. 컨트롤러에 넣기에는 너무 길어서 서비스에 넣었습니다.
			// 그 다음 파일 이름을 insert한다
			//String uploadPath="C:\\Users\\KOSTA\\git\\wheelwego\\asechs_wheelwego\\src\\main\\webapp\\resources\\img\\";
			String uploadPath=request.getSession().getServletContext().getRealPath("/resources/img/");
			List<MultipartFile> fileList=bvo.getFile();
			//ArrayList<String> filePath=new ArrayList<String>();
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
							//filePath.add(uploadPath+fileName);
							boardDAO.qnaWriteFileUpload(boardVO);
						}catch(IllegalStateException | IOException e){
							e.printStackTrace();
							}
						}
				}
			}
		}


	@Override
	public BoardVO getqnaBoardDetail(String no) {
		return boardDAO.getqnaBoardDetail(no);
	}

	@Override
	public List<FileVO> getqnaFilePath(String no) {
		return boardDAO.getqnaFilePath(no);
	}

	@Override
	public MemberVO qna_getNameById(BoardVO bvo) {
		return boardDAO.qna_getNameById(bvo);
	}

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
		List<MultipartFile> fileList = vo.getFile();// 사진을 받아오고
		// 글 수정을 먼저 한다
		String contentNo = boardDAO.qnaupdateBoard(vo);
		ArrayList<String> list=new ArrayList<String>();
		for(int i=0;i<vo.getFile().size();i++){
			String modityFile=vo.getFile().get(i).getOriginalFilename();
			// 사용자가 사진을 하나 이상 수정한다고 하면 
			// 리스트에 사용자가 보낸파일 명을 저장시킨다
			// 아무것도 수정하지 않으면 리스트에 아무것도 없게된다
			if (!modityFile.equals(""))
				list.add(modityFile.trim());
			else
			System.out.println("list isEmpty");
		}
		// 리스트가 비워져 있음 글을 업데이트하고 리턴으로 나간다
		if(list.isEmpty()){
			boardDAO.qnaupdateBoard(vo);
			return;
		}else{
			// 리스트 존재시 기존 파일을 삭제하고 수정하고자 하는 파일을 재등록한다.
			boardDAO.qnaDeleteFile(contentNo);
			for (int i = 0; i < fileList.size(); i++) {
				if (fileList.isEmpty() == false) {
					String fileName = fileList.get(i).getOriginalFilename();
					BoardVO boardVO = new BoardVO();
					FileVO fileVO = new FileVO();
					if (fileName.equals("") == false) {
						try {
							fileList.get(i).transferTo(new File(uploadPath + fileName));
							// 파일 넘버 지정
							fileVO.setNo(contentNo);
							// 파일 경로 지정
							fileVO.setFilepath(fileName);
							// 보드VO 에 파일VO 저장
							boardVO.setFileVO(fileVO);
							// 새로운 사진 서버에 업데이트
							boardDAO.qnaWriteFileUpload(boardVO);
						} catch (IllegalStateException | IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	@Override
	public void updateHitsqna(int hits) {
		boardDAO.updateHitsqna(hits);

	}

	public List<CommentVO> getqnaCommentList(String no) {
		return boardDAO.getqnaCommentList(no);
	}

	@Override
	public void writeqnaComment(CommentVO cvo) {
		boardDAO.writeqnaComment(cvo);
	}

	@Override
	public void deleteqnaComment(CommentVO cvo) {
		boardDAO.deleteqnaComment(cvo);
	}

	@Override
	public CommentVO getqnaComment(CommentVO cvo) {
		return boardDAO.getqnaComment(cvo);
	}

	@Override
	public void updateqnaComment(CommentVO cvo) {
		boardDAO.updateqnaComment(cvo);

	}
}
