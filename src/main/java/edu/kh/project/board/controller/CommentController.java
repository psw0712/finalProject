package edu.kh.project.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.kh.project.board.model.dto.Comment;
import edu.kh.project.board.model.service.CommentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService service;
	
	@GetMapping("")
	public List<Comment> select(@RequestParam("boardNo") int boardNo) {
		
		return service.select(boardNo);
	}
}
