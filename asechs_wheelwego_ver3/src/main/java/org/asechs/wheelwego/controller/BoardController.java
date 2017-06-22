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

	@Resource(name="boardServiceImpl2")
	private BoardService boardService;
 
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 게시판 아이콘
	 * --------------------------------------
	 * 코드 설명 : 게시판 아이콘을 클릭하면 해당 게시판으로 이동하는 기능
	 * @return
	 */
	@RequestMapping("boardSelectList.do")
	public String showBoardList() {
		return "board/boardSelectList.tiles";
	}
	

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
	/**
	 * 김호겸 작성
	 *  2017.6.22 (수정 완료)
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
			boardService.updateHits(hits);
			BoardVO bvo = boardService.getFreeBoardDetail(no);
			List<FileVO> fileNameList=boardService.getFreeBoardFilePath(no);
			List<CommentVO> freeboardCommentList=boardService.getFreeboardCommentList(no);
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
		/**
		 * 김호겸 작성
		 *  2017.6.22 (수정 완료)
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
		 *  2017.6.22 (수정 완료)
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
		 *  2017.6.22 (수정 완료)
		 * 자유게시판 수정하기- 게시글 수정하기
		 * ------------------------------------------------------
		 * 수정 후 상세보기로 넘어가야 함으로 vo의 no을 받아 넘긴다.
		  */
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

		/**
		 * 강정호
		 * 2017.06.21 (수정완료)
		 * 게시판 - 창업정보게시판 게시물 목록 보기
		 * ----------------------------------------------
		 * 코드 설명 : 사용자가 창업게시판 접속할 때 게시물 목록을 페이지 번호와 같이 보여준다.
		 * @param pageNo
		 * @return
		 */
	@RequestMapping("business_list.do")
	public ModelAndView businessInfoBoardList(String pageNo) {
		return new ModelAndView("board/business_list.tiles", "businessInfoBoardList",
				boardService.getBusinessInfoBoardList(pageNo));
	}
	/**
	 * 김호겸, 강정호 작성
	 *  2017.6.22 (수정 완료)
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
			boardService.updateHitsBusiness(hits);
			BoardVO bvo = boardService.getBusinessBoardDetail(no);
			List<FileVO> fileNameList=boardService.getBusinessFilePath(no);
			List<CommentVO> businessCommentList=boardService.getbusinessCommentList(no);
			MemberVO name = boardService.business_getNameById(bvo);
			model.addAttribute("detail_business", bvo);
			model.addAttribute("fileNameList",fileNameList);
			model.addAttribute("businessCommentList",businessCommentList);
			model.addAttribute("name", name);
			return "board/business_detail_content.tiles";
		}
		
		/**
		 * 강정호
		 * 2017.06.21(수정완료)
		 * 게시판 - 창업 게시판 글 등록
		 * -------------------------------------
		 * 코드 설명 : 창업게시판에서 글을 등록할 때 사용하는 메서드이다.
		 *  board/business_write_form.jsp에서 <form> 태그로 넘어온 BoardVO에는
		 *  글 정보(제목, 글 내용, 작성자 ) 와 첨부 사진정보가 들어 있다.
		 * @param bvo
		 * @param request
		 * @return
		 */
		@RequestMapping(value="afterLogin_board/business_write.do",method=RequestMethod.POST)
		public String buesinessWrite(BoardVO bvo, HttpServletRequest request){
			boardService.businessWrite(bvo, request);
			return "redirect:../board/business_detail_content.do?no="+bvo.getNo();
		}
		/**
		 * 김호겸 작성
		 *  2017.6.22 (수정 완료)
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
		 *  2017.6.22 (수정 완료)
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
		 *  2017.6.22 (수정 완료)
		 * 창업게시판 수정하기- 게시글 수정하기
		 * ------------------------------------------------------
		 * 수정 후 상세보기로 넘어가야 함으로 vo의 no을 받아 넘긴다.
		  */
		@RequestMapping(value="afterLogin_board/business_updateBoard.do",method=RequestMethod.POST)
		public String businessupdateBoard(BoardVO vo) {
			boardService.businessupdateBoard(vo);
			return "redirect:../board/business_detail_content.do?no="+vo.getNo();
		}
		
		/**
		 * 강정호
		 * 2017.06.21(수정완료)
		 * 게시판 - 창업게시판 댓글 등록
		 * -----------------------------------------
		 * 코드 설명 : 게시물 상세보기 페이지에서 댓글을 등록하는 기능입니다.
		 * 상세 보기 페이지에 있는 댓글 작성 폼에서 받아온 CommentVO를 
		 * 데이터 베이스에 등록해주는 메서드이다.
		 * return에서 "redirect:../~~"를 해주는 이유는 redirect는 afterLogin_board/ 디렉토리 내에서 리다이렉트가 되는데
		 * ../를 사용하여 afterLogin_board/ 디렉토리에서 나가게 되어 jsp 파일을 찾아준다.
		 * @param cvo
		 * @return
		 */
		@RequestMapping(value="afterLogin_board/writebusinessComment.do",method=RequestMethod.POST)
		public String writebusinessComment(CommentVO cvo){
			boardService.writebusinessComment(cvo);
			return "redirect:../board/business_detail_content.do?no="+cvo.getContentNo();
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
		@RequestMapping(value="afterLogin_board/deletebusinessComment.do",method=RequestMethod.POST)
		@ResponseBody
		public String deletebusinessComment(CommentVO cvo){
			boardService.deletebusinessComment(cvo);
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
		@RequestMapping("afterLogin_board/business_update_comment.do")
		public String updatebusinessCommentForm(HttpServletRequest request, Model model){
			String commentNo=request.getParameter("commentNo");
			String contentNo=request.getParameter("contentNo");
			CommentVO cvo=new CommentVO();
			cvo.setCommentNo(Integer.parseInt(commentNo));
			cvo.setContentNo(Integer.parseInt(contentNo));
			model.addAttribute("businessComment", boardService.getbusinessComment(cvo));
			return "board/business_update_comment.tiles";
		}

		/**
		 * 강정호
		 * 2017.06.21(수정완료)
		 * 게시판 - 댓글 수정
		 * ----------------------------------
		 * 코드 설명 : 댓글 수정창에서 수정 버튼 클릭시 수정된 댓글 정보를 
		 * 데이터 베이스에 업데이트 하는 메서드입니다.
		 * 댓글 수정은 ajax 통신으로 업데이트 됩니다.
		 * @param cvo
		 * @return
		 */
		@RequestMapping("updatebusinessComment.do")
		@ResponseBody
		public String updatebusinessComment(CommentVO cvo){
			boardService.updatebusinessComment(cvo);
			return "";
		}
		

	/**
	 * 강정호
	 * 2017.06.21 (수정완료)
	 * 게시판 - 질문답변게시판 게시물 목록 보기
	 * ----------------------------------------------
	 * 코드 설명 : 사용자가 질문답변게시판에 접속할 때 게시물 목록을 페이지 번호와 같이 보여준다.
	 * @param pageNo
	 * @return
	 */
	@RequestMapping("qna_list.do")
	public ModelAndView QnABoardList(String pageNo) {
		return new ModelAndView("board/qna_list.tiles", "qnaBoardList", boardService.getQnABoardList(pageNo));
	}
	/**
	 * 김호겸,강정호 작성
	 *  2017.6.22(수정 완료)
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
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 질문답변 게시판 글 등록
	 * -------------------------------------
	 * 코드 설명 : 질문답변게시판에서 글을 등록할 때 사용하는 메서드이다.
	 *  board/business_write_form.jsp에서 <form> 태그로 넘어온 BoardVO에는
	 *  글 정보(제목, 글 내용, 작성자 ) 와 첨부 사진정보가 들어 있다.
	 * @param bvo
	 * @param request
	 * @return
	 */
	@RequestMapping("afterLogin_board/qna_write.do")
	public String qnaWrite(BoardVO bvo, HttpServletRequest request){
		boardService.qnaWrite(bvo, request);
		return "redirect:../board/qna_detail_content.do?no="+bvo.getNo();
	}
	/**
	 * 김호겸 작성
	 *  2017.6.22 (수정 완료)
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
			 *  2017.6.22 (수정 완료)
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
	
	/**
	 * 강정호
	 * 2017.06.21(수정완료)
	 * 게시판 - 창업게시판 댓글 등록
	 * -----------------------------------------
	 * 코드 설명 : 게시물 상세보기 페이지에서 댓글을 등록하는 기능입니다.
	 * 상세 보기 페이지에 있는 댓글 작성 폼에서 받아온 CommentVO를 
	 * 데이터 베이스에 등록해주는 메서드이다.
	 * return에서 "redirect:../~~"를 해주는 이유는 redirect는 afterLogin_board/ 디렉토리 내에서 리다이렉트가 되는데
	 * ../를 사용하여 afterLogin_board/ 디렉토리에서 나가게 되어 jsp 파일을 찾아준다.
	 * @param cvo
	 * @return
	 */
	@RequestMapping("afterLogin_board/writeqnaComment.do")
	public String writeqnaComment(CommentVO cvo){
		boardService.writeqnaComment(cvo);
		return "redirect:../board/qna_detail_content.do?no="+cvo.getContentNo();
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
	@RequestMapping("afterLogin_board/deleteqnaComment.do")
	@ResponseBody
	public String deleteqnaComment(CommentVO cvo){
		boardService.deleteqnaComment(cvo);
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
	@RequestMapping(value="updateqnaComment.do", method=RequestMethod.POST)
	@ResponseBody
	public String updateqnaComment(CommentVO cvo){
		boardService.updateqnaComment(cvo);
		return "";
		}
	}

