package com.yang.kwang.firebase.controller;

import com.yang.kwang.firebase.model.Board;
import com.yang.kwang.firebase.service.BoardService;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @Autowired
    BoardService boardService;

    @GetMapping("/list")
    public String boardList(Model model) throws Exception {

        List<Board> boards = boardService.getBoardDocument();
        model.addAttribute("boards", boards);

        return "board/list";
    }

    @GetMapping("/writeForm")
    public String boardWrite(Model model, @RequestParam(required = false) String docId) throws Exception{
        if (docId == null) {
            model.addAttribute("board",new Board());
        }else{
           Board board = boardService.getBoardDocument(docId);
            logger.info("boardWrite board ====> " + board.toString());
           model.addAttribute("board",board);
        }


        return "board/writeForm";
    }

    @PostMapping("/write")
    public String boardWriteForm(@Valid Board board, BindingResult bindingResult) throws Exception{
        String docId = board.getDocId();

        if(docId.isEmpty()){
            // 등록
            boardService.boardWriteForm(board);
        }else{
            // 수정
            boardService.updateBoardDocument(board);
        }
        //boardService.boardWriteForm(board);
        return "redirect:/board/list";
    }

    /*
        삭제
     */
    @PostMapping("/delete")
    public String boardDelete(@Valid Board board, BindingResult bindingResult) throws Exception{
        boardService.deleteBoardDocument(board);
        return "redirect:/board/list";
    }
}
