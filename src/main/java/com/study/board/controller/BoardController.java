package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String boardWritePro(Board board, Model model) {
        boardService.write(board);
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("redirectUrl", "/board/list");
        return "message";
    }

    /**
     * pageable을 통해 localhost:8080/board/list?page=1&size=10
     * page와 size를 넘겨줄 수 있다. ( @pageableDefault )
     */
    @GetMapping("/board/list")
    public String boardList(Model model, Pageable pageable) {
        Page<Board> list = boardService.boardList(pageable);

        int nowPage = list.getPageable().getPageNumber();
        int startPage = Math.max(nowPage - 2, 0);
        int endPage = Math.min(nowPage + 3, list.getTotalPages()-1);

        model.addAttribute("list", list); // 변수 넘겨주기
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";
    }

    /**
     * 접근 방법 : http://localhost:8080/board/view?id=3 ( QueryString - id로 상세페이지 접근 )
     */
    @GetMapping("/board/view")
    public String boardView(Model model, Integer id) {
        model.addAttribute("article", boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id) {
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("article", boardService.boardView(id));
        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, Model model) {

        Board oldBoard = boardService.boardView(id); // 기존 객체 불러오기
        // 기존 객체에 덮어씌우기
        oldBoard.setTitle(board.getTitle());
        oldBoard.setContent(board.getContent());
        boardService.write(oldBoard);

        model.addAttribute("message", "글 수정이 완료되었습니다.");
        model.addAttribute("redirectUrl", "/board/list");

        return "message";
    }
}
