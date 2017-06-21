package org.asechs.wheelwego.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.asechs.wheelwego.model.BoardService;
import org.asechs.wheelwego.model.vo.BoardVO;
import org.asechs.wheelwego.model.vo.CommentVO;
import org.asechs.wheelwego.model.vo.FileVO;
import org.asechs.wheelwego.model.vo.MemberVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
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
@Controller
public class BoardController {

	@Resource(name="boardServiceImpl")
	private BoardService boardService;

	// 강정호 작성. 보드리스트(3개의 아이콘 나오는 것)
	@RequestMapping("boardSelectList.do")
	public String showBoardList() {
		return "board/boardSelectList.tiles";
	}
	

	
	////////////강정호. 자유게시판 freeboard/////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 강정호
	 * 2017.06.21 (수정완료)
	 * 게시판 - 자유게시판 게시물 목록 보기
	 * ----------------------------------------------
	 * 코드 설명 : 사용자가 자유게시판에 접속할 때 게시물 목록을 페이지 번호와 같이 보여준다.
	 * @param pageNo
	 * @return
	 */
	@RequestMapping("freeboard_list.do")
	public ModelAndView freeBoardList(String pageNo) {
		return new ModelAndView("board/freeboard_list.tiles", "freeBoardList", boardService.getFreeBoardList(pageNo));
	}
	
	// 호겸 작성. 자유게시판 상세보기 and 조회수
		@RequestMapping("/board/freeboard_detail_content.do")
		public String freeboard_detail_content(String no, Model model) {
			System.out.println("자유게시판 상세보기 : 컨트롤러 시작");
			int hits = Integer.parseInt(no);
			// 조회수 올리기
			boardService.updateHits(hits);
			// 글제목, 조회수, 내용 가져오는 메서드
			BoardVO bvo = boardService.getFreeBoardDetail(no);
			// 파일 이름 가져오는 메서드
			List<FileVO> fileNameList=boardService.getFreeBoardFilePath(no);
			List<CommentVO> freeboardCommentList=boardService.getFreeboardCommentList(no);
			// 작성자 이름 갖고오기
			MemberVO name = boardService.getNameById(bvo);
			model.addAttribute("detail_freeboard", bvo);
			model.addAttribute("fileNameList",fileNameList);
			model.addAttribute("name", name);
			model.addAttribute("freeboardCommentList",freeboardCommentList);
			return "board/freeboard_detail_content.tiles";
		}
		
		/**
		 * 강정호
		 * 2017.06.21(수정완료)
		 * 게시판 - 자유 게시판 글 등록
		 * -------------------------------------
		 * 코드 설명 : 자유게시판에서 글을 등록할 때 사용하는 메서드이다.
		 *  board/freeboard_write_form.jsp에서 <form> 태그로 넘어온 BoardVO에는
		 *  글 정보(제목, 글 내용, 작성자 ) 와 첨부 사진정보가 들어 있다.
		 * @param bvo
		 * @param request
		 * @return
		 */
		@RequestMapping(value="freeboard_write.do", method=RequestMethod.POST)
		public String freeboardWrite(BoardVO bvo, HttpServletRequest request) {
			boardService.freeboardWrite(bvo, request);
			return "redirect:board/freeboard_detail_content.do?no=" + bvo.getNo();
		}
		
		// 호겸 작성. 자유게시판 게시물 삭제
		@RequestMapping("afterLogin_board/freeboardDelete.do")
		public String freeboardDelete(String no) {
			boardService.freeboardDelete(no);
			return "redirect:../freeboard_list.do";
		}
		
		// 호겸 작성. 자유게시판 게시물 수정 폼으로 가기
		@RequestMapping("afterLogin_board/freeboard_update_form.do")
		public String freeboard_update_form(String no, Model model,HttpServletRequest request) {
			//아이디를 받아와서 작성자를 뽑아야된다
			System.out.println("freeboard_update_form 통과");
			BoardVO bvo = boardService.getFreeBoardDetail(no);
			MemberVO name = boardService.getNameById(bvo);
			model.addAttribute("name", name);
			model.addAttribute("detail_freeboard", bvo);
			return "board/freeboard_update_form.tiles";
		}
		
		// 호겸 작성. 자유게시판 게시물 수정 해버리기
		@RequestMapping("afterLogin_board/updateBoard.do")
		public String updateBoard(BoardVO vo) {
			boardService.updateBoard(vo);
			return "redirect:../board/freeboard_detail_content.do?no=" + vo.getNo();
		}
		
		/**
		 * 강정호
		 * 2017.06.21(수정완료)
		 * 게시판 - 자유게시판 댓글 등록
		 * -----------------------------------------
		 * 코드 설명 : 게시물 상세보기 페이지에서 댓글을 등록하는 기능입니다.
		 * 상세 보기 페이지에 있는 댓글 작성 폼에서 받아온 CommentVO를 
		 * 데이터 베이스에 등록해주는 메서드이다.
		 * return에서 "redirect:../~~"를 해주는 이유는 redirect는 afterLogin_board/ 디렉토리 내에서 리다이렉트가 되는데
		 * ../를 사용하여 afterLogin_board/ 디렉토리에서 나가게 되어 jsp 파일을 찾아준다.
		 * @param cvo
		 * @return
		 */
		@RequestMapping(value="afterLogin_board/writeFreeboardComment.do",method=RequestMethod.POST)
		public String writeFreeboardComment(CommentVO cvo){
			boardService.writeFreeboardComment(cvo);
			return "redirect:../board/freeboard_detail_content.do?no="+cvo.getContentNo();
		}
		
		
		/**
		 * 강정호
		 * 2017.06.21(수정완료)
		 * 게시판 - 댓글 삭제
		 * ---------------------------------
		 * 코드 설명 : 게시물 상세보기 페이지에서 댓글을 삭제하는 기능입니다.
		 * 상세보기 페이지에서 댓글 삭제 버튼을 누르면 Ajax 통신으로 게시물 번호, 댓글 번호를 이용하여
		 * 삭제를 합니다.
		 * @param cvo
		 * @return
		 */
		@RequestMapping(value="afterLogin_board/deleteFreeboardComment.do", method=RequestMethod.POST)
		@ResponseBody
		public String deleteFreeboardComment(CommentVO cvo){
			boardService.deleteFreeboardComment(cvo);
			return null;
		}
		
		/**
		 * 강정호
		 * 2017.06.21(수정완료)
		 * 게시판 - 댓글 수정창 팝업
		 * ----------------------------------
		 * 코드 설명 : 게시물 상세보기 페이지에서 댓글을 수정창을 띄워 주는 메서드입니다.
		 * 상세보기 페이지에서 댓글 수정 버튼을 클릭시, 댓글 수정창이 팝업으로 나올때,
		 * 게시물 번호와 댓글 번호를 넘겨 주어 어떤 게시물의 어떤 댓글이 수정이 되는지 구분합니다.
		 * @param request
		 * @param model
		 * @return
		 */
		@RequestMapping("afterLogin_board/freeboard_update_comment.do")
		public String updateFreeboardCommentForm(HttpServletRequest request, Model model){
			String commentNo=request.getParameter("commentNo");
			String contentNo=request.getParameter("contentNo");
			CommentVO cvo=new CommentVO();
			cvo.setCommentNo(Integer.parseInt(commentNo));
			cvo.setContentNo(Integer.parseInt(contentNo));
			model.addAttribute("freeboardComment", boardService.getFreeboardComment(cvo));
			return "board/freeboard_update_comment.tiles";
		}
		
		/**
		 * 강정호
		 * 2017.06.21(수정완료)
		 * 게시판 - 댓글 수정
		 * ----------------------------------
		 * 코드 설명 : 댓글 수정창에서 수정 버튼 클릭시 수정된 댓글 정보를 데이터 베이스에 업데이트 하는 메서드입니다.
		 * 댓글 수정은 ajax 통신으로 업데이트 됩니다.
		 * @param cvo
		 * @return
		 */
		@RequestMapping(value="updateFreeboardComment.do",method=RequestMethod.POST)
		@ResponseBody
		public String updateFreeboardComment(CommentVO cvo){
			boardService.updateFreeboardComment(cvo);
			return "null";
		}
////////////강정호. 창업게시판 business/////////////////////////////////////////////////////////////////////////////////////////////////////

	// 강정호 작성. 창업정보 게시판 리스트 보여주는 메서드
	@RequestMapping("business_list.do")
	public ModelAndView businessInfoBoardList(String pageNo) {
		/*
		 * System.out.println("창업컨트롤러 창업게시판 메핑 통과"); List<BoardVO>
		 * businessInfoBoardList=boardService.getBusinessInfoBoardList();
		 */
		return new ModelAndView("board/business_list.tiles", "businessInfoBoardList",
				boardService.getBusinessInfoBoardList(pageNo));
	}
	
	// 강정호. 창업게시판 상세보기
		@RequestMapping("board/business_detail_content.do")
		public String business_detail_content(String no, Model model) {
			int hits = Integer.parseInt(no);
			// 조회수 올리기
			boardService.updateHitsBusiness(hits);
			BoardVO bvo = boardService.getBusinessBoardDetail(no);
			List<FileVO> fileNameList=boardService.getBusinessFilePath(no);
			// 댓글 불러오는 메서
			List<CommentVO> businessCommentList=boardService.getbusinessCommentList(no);
			MemberVO name = boardService.business_getNameById(bvo);
			model.addAttribute("detail_business", bvo);
			model.addAttribute("fileNameList",fileNameList);
			model.addAttribute("businessCommentList",businessCommentList);
			model.addAttribute("name", name);
			return "board/business_detail_content.tiles";
		}
		
		//강정호 창업 게시판 글 등록 메서드
		@RequestMapping(value="afterLogin_board/business_write.do",method=RequestMethod.POST)
		public String buesinessWrite(BoardVO bvo, HttpServletRequest request){
			boardService.businessWrite(bvo, request);
			return "redirect:../board/business_detail_content.do?no="+bvo.getNo();
		}
		
		// 호겸 작성. 창업 게시물 삭제
		@RequestMapping("afterLogin_board/businessDelete.do")
		public String businessDelete(String no) {
			boardService.businessDelete(no);
			return "redirect:../business_list.do";
		}

		// 호겸 작성. 창업 게시물 수정 폼으로 가기
		@RequestMapping("afterLogin_board/business_update_form.do")
		public String business_update_form(String no, Model model) {
			System.out.println(no);
			BoardVO bvo = boardService.getBusinessBoardDetail(no);
			MemberVO name = boardService.business_getNameById(bvo);
			model.addAttribute("name", name);
			model.addAttribute("detail_freeboard", bvo);
			return "board/business_update_form.tiles";
		}
		
		// 호겸 작성. 창업게시물 수정 해버리기
		@RequestMapping(value="afterLogin_board/business_updateBoard.do",method=RequestMethod.POST)
		public String businessupdateBoard(BoardVO vo) {
			boardService.businessupdateBoard(vo);
			System.out.println(vo.getNo());
			return "redirect:../board/business_detail_content.do?no="+vo.getNo();
		}
		
		@RequestMapping(value="afterLogin_board/writebusinessComment.do",method=RequestMethod.POST)
		public String writebusinessComment(CommentVO cvo){
			boardService.writebusinessComment(cvo);
			return "redirect:../board/business_detail_content.do?no="+cvo.getContentNo();
		}
		
		@RequestMapping(value="afterLogin_board/deletebusinessComment.do",method=RequestMethod.POST)
		@ResponseBody
		public String deletebusinessComment(CommentVO cvo){
			System.out.println(cvo);
			boardService.deletebusinessComment(cvo);
			return null;
		}
		
		@RequestMapping("afterLogin_board/business_update_comment.do")
		public String updatebusinessCommentForm(HttpServletRequest request, Model model){
			String commentNo=request.getParameter("commentNo");
			String contentNo=request.getParameter("contentNo");
			CommentVO cvo=new CommentVO();
			cvo.setCommentNo(Integer.parseInt(commentNo));
			cvo.setContentNo(Integer.parseInt(contentNo));
			model.addAttribute("businessComment", boardService.getbusinessComment(cvo));
			System.out.println(boardService.getbusinessComment(cvo));
			return "board/business_update_comment.tiles";
		}

		
		@RequestMapping("updatebusinessComment.do")
		@ResponseBody
		public String updatebusinessComment(CommentVO cvo){
			boardService.updatebusinessComment(cvo);
			return "";
		}
		

		
////////////강정호. Q&A게시판 business/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// 강정호 작성. Q&A 게시판 리스트 보여주는 메서드
	@RequestMapping("qna_list.do")
	public ModelAndView QnABoardList(String pageNo) {
		return new ModelAndView("board/qna_list.tiles", "qnaBoardList", boardService.getQnABoardList(pageNo));
	}
	// 강정호. 질문답변게시판 상세보기
	@RequestMapping("board/qna_detail_content.do")
	public String qna_detail_content(String no, Model model) {
		int hits = Integer.parseInt(no);
		// 조회수 올리기
		boardService.updateHitsqna(hits);
		BoardVO bvo = boardService.getqnaBoardDetail(no);
		List<FileVO> fileNameList=boardService.getqnaFilePath(no);
		List<CommentVO> qnaCommentList=boardService.getqnaCommentList(no);
		MemberVO name = boardService.qna_getNameById(bvo);
		model.addAttribute("detail_qna", bvo);
		model.addAttribute("fileNameList",fileNameList);
		model.addAttribute("name", name);
		model.addAttribute("qnaCommentList", qnaCommentList);
		return "board/qna_detail_content.tiles";
	}

	//강정호 Q&A 게시판 글 등록 메서드
	@RequestMapping("afterLogin_board/qna_write.do")
	public String qnaWrite(BoardVO bvo, HttpServletRequest request){
		boardService.qnaWrite(bvo, request);
		return "redirect:../board/qna_detail_content.do?no="+bvo.getNo();
	}
	// 호겸 작성. qna 게시물 삭제
			@RequestMapping("afterLogin_board/qnaDelete.do")
			public String qnaDelete(String no) {
				boardService.qnaDelete(no);
				return "redirect:../qna_list.do";
			}

			// 호겸 작성. qna 게시물 수정 폼으로 가기
			@RequestMapping("afterLogin_board/qna_update_form.do")
			public String qna_update_form(String no, Model model) {
				BoardVO bvo = boardService.getqnaBoardDetail(no);
				MemberVO name = boardService.qna_getNameById(bvo);
				List<FileVO> fileNameList=boardService.getqnaFilePath(no);
				model.addAttribute("name", name);
				model.addAttribute("fileNameList",fileNameList);
				model.addAttribute("detail_qna", bvo);
				return "board/qna_update_form.tiles";
			}
			
			// 호겸 작성. qna게시물 수정 해버리기
			@RequestMapping("afterLogin_board/qna_updateBoard.do")
			public String qnaupdateBoard(BoardVO vo) {
				boardService.qnaupdateBoard(vo);
				return "redirect:../board/qna_detail_content.do?no="+vo.getNo();
			
	}
	
	//강정호 Q&A 게시판 댓글 등록메서드
	@RequestMapping("afterLogin_board/writeqnaComment.do")
	public String writeqnaComment(CommentVO cvo){
		boardService.writeqnaComment(cvo);
		return "redirect:../board/qna_detail_content.do?no="+cvo.getContentNo();
	}
	
	//강정호. Q&A 댓글 삭제
	@RequestMapping("afterLogin_board/deleteqnaComment.do")
	@ResponseBody
	public String deleteqnaComment(CommentVO cvo){
		boardService.deleteqnaComment(cvo);
		return null;
	}
	
	@RequestMapping("afterLogin_board/qna_update_comment.do")
	public String updateqnaCommentForm(HttpServletRequest request, Model model){
		String commentNo=request.getParameter("commentNo");
		String contentNo=request.getParameter("contentNo");
		CommentVO cvo=new CommentVO();
		cvo.setCommentNo(Integer.parseInt(commentNo));
		cvo.setContentNo(Integer.parseInt(contentNo));
		model.addAttribute("qnaComment", boardService.getqnaComment(cvo));
		return "board/qna_update_comment.tiles";
	}
	
	@RequestMapping(value="updateqnaComment.do", method=RequestMethod.POST)
	@ResponseBody
	public String updateqnaComment(CommentVO cvo){
		boardService.updateqnaComment(cvo);
		return "";
		}
	}

