package edu.kh.project.board.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.board.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@Slf4j
@RequiredArgsConstructor
//@SessionAttributes("{loginMember}")
public class BoardController {
	
	private final BoardService service;
	
	/** 게시글 목록 조회
	 * @param boardCode : 게시판 종류 구분 번호
	 * @param cp : 현재 조회 요청한 페이지 (없으면 1)
	 * @return
	 * 
	 * - /board/xxx
	 *  /board 이하 1레벨 자리에 숫자로된 요청 주소가
	 *   작성되어 있을 때만 동작 -> 정규표현식 이용
	 * 
	 * [0-9] : 한 칸에 0~9사이 숫자 입력 가능
	 * + : 하나 이상
	 * 
	 * [0-9]+ : 모든 숫자
	 * 
	 */
	@GetMapping("{boardCode:[0-9]+}")
	public String selectBoardList(
			@PathVariable("boardCode") int boardCode,
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			Model model
			) {
		
		log.debug("boardCode : " + boardCode);
		
		// 조회 서비스 호출 후 결과 반환
		Map<String, Object> map = service.selectBoardList(boardCode, cp);
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("boardList" , map.get("boardList"));
		
		
		return "board/boardList"; // boardList.html로 forward
	}
	
	// 상세 조회 요청 주소
		//  /board/1/1998?cp=1
		//  /board/2/1960?cp=2
		
		@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}")
		public String boardDetail(
				@PathVariable("boardCode") int boardCode,
				@PathVariable("boardNo")   int boardNo,
				Model model,
				RedirectAttributes ra
				) {
			
			
			return "board/boardDetail"; // board/boardDetail.html로 forward
			
		}
	
	
	
	
	
}
