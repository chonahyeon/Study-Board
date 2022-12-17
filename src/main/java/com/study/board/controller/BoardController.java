package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public String boardMainPage() {
        return "redirect:/board/write";
    }

    @GetMapping("/board/write")
    public String boardWriteForm() {
        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board) {
        boardService.write(board);
        return "redirect:/board/list";
    }

    @GetMapping("/board/list")
    public String boardList(Model model) {
        model.addAttribute("list", boardService.boardList());
        return "boardlist";
    }

    /**
     * 접근 방법 : http://localhost:8080/board/view?id=3 ( QueryString - id로 상세페이지 접근 )
     */
    @GetMapping("/board/view")
    public String boardView(Model model,Integer id) {
        model.addAttribute("article",boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    /**
     * 접근 방법 : http://localhost:8080/board/3 ( PathVariable 사용 - 바로 id 값으로 접근 )
     */
    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model){

        model.addAttribute("article",boardService.boardView(id));
        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id,Board board){

        Board oldBoard = boardService.boardView(id); // 기존 객체 불러오기
        // 기존 객체에 덮어씌우기
        oldBoard.setTitle(board.getTitle());
        oldBoard.setContent(board.getContent());

        boardService.write(oldBoard);
        return "redirect:/board/list";
    }
}
