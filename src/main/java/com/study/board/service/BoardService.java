package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 게시글 작성
    public void write(Board board){
        boardRepository.save(board);
    }

    // 게시글 목록 처리
    public Page<Board> boardList(Pageable pageable) {
        int page = pageable.getPageNumber();
        int pageLimit = 15;
        return boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
    }

    // 특정 게시글 불러오기
    public Board boardView(Integer id) {
        return boardRepository.findById(id).get();
    }

    // 특정 게시글 삭제하기
    public void boardDelete(Integer id) {
        boardRepository.deleteById(id);
    }
}
