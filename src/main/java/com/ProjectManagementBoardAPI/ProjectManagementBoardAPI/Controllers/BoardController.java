package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Controllers;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Board;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    /*******  Create Board  ******/
    @PostMapping
    public Board createBoard(@RequestBody Board board) {
        try {
            return boardService.createBoard(board);
        } catch (Exception e) {
            System.err.println("Cannot create Board " + e.getMessage());
            return null;
        }
    }

    /*******  Get All Boards  ******/
    @GetMapping
    public List<Board> getAllBoards() {
        try {
            return boardService.getAllBoards();
        } catch (Exception e) {
            System.err.println("Cannot get all Boards " + e.getMessage());
            return null;
        }
    }


    /*******  Get Board by id  ******/
    @GetMapping(value = "/{id}")
    public Board getBoardById(@PathVariable Long id) {
        try {
            return boardService.getBoardById(id);
        } catch (Exception e) {
            System.err.println("Cannot get Board with this id " + e.getMessage());
            return null;
        }
    }

    /****** Update Board ******/
    @PutMapping("/{id}")
    public Board updateBoard(@PathVariable Long id, @RequestBody Board update) {
        try {
            return boardService.updateBoard(id, update);
        } catch (Exception e) {
            System.err.println("Cannot update this Board: " + e.getMessage());
        }
        return null;
    }

    /****** Delete Board ******/
    @DeleteMapping(value = "/{id}")
    public void deleteBoardById(@PathVariable Long id) {
        try {
            boardService.deleteBoardById(id);
        } catch (Exception e) {
            System.err.println("Cannot delete Board: " + e.getMessage());
        }
    }
}
