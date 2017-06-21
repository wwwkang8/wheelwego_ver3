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

	// 강정호 작성. 자유게시판 리스트 보여주는 메서드
	@RequestMapping("freeboard_list.do")
	public ModelAndView freeBoardList(String pageNo) {
		System.out.println("리스트 테스트");
		return new ModelAndView("board/freeboard_list.tiles", "freeBoardList", boardService.getFreeBoardList(pageNo));
	}
	/**
	 * 김호겸 작성
	 *  2017.6 (수정 완료)
	 * 자유게시판 상세보기- 게시판 상세보기 및 조회수
	 * ------------------------------------------------------
	 * 게시글의 no로 상세보기를 구현한다.
	 * 상세보기를 할때 조회수를 올리는 메서드와 사진 파일 이름을 가져오는 메서드를 실행해
	 * model 에 저장시킨다.
		---------------------------------
	  */
		@RequestMapping("/board/freeboard_detail_content.do")
		public String freeboard_detail_content(String no, Model model) {
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
		
		// 강정호 자유게시판 글 등록 메서드
		@RequestMapping(value="freeboard_write.do", method=RequestMethod.POST)
		public String freeboardWrite(BoardVO bvo, HttpServletRequest request) {
			boardService.freeboardWrite(bvo, request);
			//return "redirect:freeboard_list.do";
			return "redirect:board/freeboard_detail_content.do?no=" + bvo.getNo();
		}
		/**
		 * 김호겸 작성
		 *  2017.6 (수정 완료)
		 * 자유게시판 글 삭제- 게시판 글 삭제
		 * ------------------------------------------------------
		 * 자유게시판 no를 받아 해당 게시글을 삭제
		 * intercepter로 인해 redirect 시 ../ 으로 한번 나간뒤 실행한다.
		  */
		@RequestMapping("afterLogin_board/freeboardDelete.do")
		public String freeboardDelete(String no) {
			boardService.freeboardDelete(no);
			return "redirect:../freeboard_list.do";
		}
		/**
		 * 김호겸 작성
		 *  2017.6 (수정 완료)
		 * 자유게시판 수정폼 - 자유게시판 수정시 수정폼 넘어가기
		 * ------------------------------------------------------
		 * 게시글의 no 로 수정폼으로 넘어가고
		 * 그때 아이디에 해당하는 이름을 갖고오는 메서드를 정의한다.
		  */
		@RequestMapping("afterLogin_board/freeboard_update_form.do")
		public String freeboard_update_form(String no, Model model) {
			BoardVO bvo = boardService.getFreeBoardDetail(no);
			MemberVO name = boardService.getNameById(bvo);
			model.addAttribute("name", name);
			model.addAttribute("detail_freeboard", bvo);
			return "board/freeboard_update_form.tiles";
		}
		/**
		 * 김호겸 작성
		 *  2017.6 (수정 완료)
		 * 자유게시판 수정하기- 게시글 수정하기
		 * ------------------------------------------------------
		 * 수정 후 상세보기로 넘어가야 함으로 vo의 no을 받아 넘긴다.
		  */
		@RequestMapping("afterLogin_board/updateBoard.do")
		public String updateBoard(BoardVO vo) {
			boardService.updateBoard(vo);
			return "redirect:../board/freeboard_detail_content.do?no=" + vo.getNo();
		}
		
		//강정호 작성. 자유게시판 댓글 작성
		@RequestMapping(value="afterLogin_board/writeFreeboardComment.do",method=RequestMethod.POST)
		public String writeFreeboardComment(CommentVO cvo){
			boardService.writeFreeboardComment(cvo);
			return "redirect:../board/freeboard_detail_content.do?no="+cvo.getContentNo();
		}
	
		//강정호 작성. 자유게시판 댓글 삭제
		@RequestMapping(value="afterLogin_board/deleteFreeboardComment.do", method=RequestMethod.POST)
		@ResponseBody
		public String deleteFreeboardComment(CommentVO cvo){
			System.out.println(cvo);
			boardService.deleteFreeboardComment(cvo);
			return null;
			
		}
		
		//강정호 작성. 자유게시판 댓글 수정폼으로 이동
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
		
		//강정호 작성. 자유게시판 댓글 수정
		@RequestMapping(value="updateFreeboardComment.do",method=RequestMethod.POST)
		@ResponseBody
		public String updateFreeboardComment(CommentVO cvo){
			boardService.updateFreeboardComment(cvo);
			//ajax를 통해 가는 이 정보를 어떤 것으로 넣어줄까?
			return "redirect:board/freeboard_detail_content.do?no="+cvo.getContentNo();
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
	/**
	 * 김호겸, 강정호 작성
	 *  2017.6 (수정 완료)
	 * 창업게시판 상세보기- 게시판 상세보기 및 조회수
	 * ------------------------------------------------------
	 * 게시글의 no로 상세보기를 구현한다.
	 * 상세보기를 할때 조회수를 올리는 메서드와 사진 파일 이름을 가져오는 메서드를 실행해
	 * model 에 저장시킨다.
		---------------------------------
	  */
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
		/**
		 * 김호겸 작성
		 *  2017.6 (수정 완료)
		 * 창업게시판 글 삭제- 게시판 글 삭제
		 * ------------------------------------------------------
		 * 창업게시판 no를 받아 해당 게시글을 삭제
		 * intercepter로 인해 redirect 시 ../ 으로 한번 나간뒤 실행한다.
		  */
		@RequestMapping("afterLogin_board/businessDelete.do")
		public String businessDelete(String no) {
			boardService.businessDelete(no);
			return "redirect:../business_list.do";
		}
		/**
		 * 김호겸 작성
		 *  2017.6 (수정 완료)
		 * 창업게시판 수정폼 - 게시판 수정시 수정폼 넘어가기
		 * ------------------------------------------------------
		 * 게시글의 no 로 수정폼으로 넘어가고
		 * 그때 아이디에 해당하는 이름을 갖고오는 메서드를 정의한다.
		  */
		@RequestMapping("afterLogin_board/business_update_form.do")
		public String business_update_form(String no, Model model) {
			BoardVO bvo = boardService.getBusinessBoardDetail(no);
			MemberVO name = boardService.business_getNameById(bvo);
			model.addAttribute("name", name);
			model.addAttribute("detail_freeboard", bvo);
			return "board/business_update_form.tiles";
		}
		/**
		 * 김호겸 작성
		 *  2017.6 (수정 완료)
		 * 창업게시판 수정하기- 게시글 수정하기
		 * ------------------------------------------------------
		 * 수정 후 상세보기로 넘어가야 함으로 vo의 no을 받아 넘긴다.
		  */
		@RequestMapping(value="afterLogin_board/business_updateBoard.do",method=RequestMethod.POST)
		public String businessupdateBoard(BoardVO vo) {
			boardService.businessupdateBoard(vo);
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
	/**
	 * 김호겸,강정호 작성
	 *  2017.6 (수정 완료)
	 * QnA게시판 상세보기- 게시판 상세보기 및 조회수
	 * ------------------------------------------------------
	 * 게시글의 no로 상세보기를 구현한다.
	 * 상세보기를 할때 조회수를 올리는 메서드와 사진 파일 이름을 가져오는 메서드를 실행해
	 * model 에 저장시킨다.
		---------------------------------
	  */
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
	/**
	 * 김호겸 작성
	 *  2017.6 (수정 완료)
	 * QnA게시판 글 삭제- 게시판 글 삭제
	 * ------------------------------------------------------
	 * QnA게시판 no를 받아 해당 게시글을 삭제
	 * intercepter로 인해 redirect 시 ../ 으로 한번 나간뒤 실행한다.
	  */
			@RequestMapping("afterLogin_board/qnaDelete.do")
			public String qnaDelete(String no) {
				boardService.qnaDelete(no);
				return "redirect:../qna_list.do";
			}
			/**
			 * 김호겸 작성
			 *  2017.6 (수정 완료)
			 * QnA게시판 수정폼 - 게시판 수정시 수정폼 넘어가기
			 * ------------------------------------------------------
			 * 게시글의 no 로 수정폼으로 넘어가고
			 * 그때 아이디에 해당하는 이름을 갖고오는 메서드를 정의한다.
			  */
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
			/**
			 * 김호겸 작성
			 *  2017.6 (수정 완료)
			 * QnA게시판 수정하기- 게시글 수정하기
			 * ------------------------------------------------------
			 * 수정 후 상세보기로 넘어가야 함으로 vo의 no을 받아 넘긴다.
			  */
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

