package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Controllers;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Board;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.RequestObject.BoardRequestObject;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.ResponseObject.BoardResponseObject;
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
    public void createBoard(@RequestBody Board board) {
        try {
            boardService.createBoard(board);
        } catch (Exception e) {
            System.err.println("Cannot create board: " + e.getMessage());
        }
    }

    /*******  Get All Boards  ******/
    @GetMapping
    public List<BoardResponseObject> getAllBoards() {
        try {
            List<Board> boards = boardService.getAllBoards();
            List<BoardResponseObject> listOfConvertedBoards = BoardResponseObject.convertToResponseList(boards);
            return listOfConvertedBoards;
        } catch (Exception e) {
            System.err.println("Cannot get all Boards " + e.getMessage());
            return null;
        }
    }

    /*******  Get Board by id  ******/
    @GetMapping(value = "/{id}")
    public Board getBoardById(@PathVariable String id) {
        try {
            return boardService.getBoardById(id);
        } catch (Exception e) {
            System.err.println("Cannot get Board with this id " + e.getMessage());
            return null;
        }
    }

    /****** Update Board ******/
    @PutMapping("/{id}")
    public Board updateBoard(@PathVariable String id, @RequestBody Board update) {
        try {
            return boardService.updateBoard(id, update);
        } catch (Exception e) {
            System.err.println("Cannot update this Board: " + e.getMessage());
        }
        return null;
    }

    /****** Delete Board ******/
    @DeleteMapping(value = "/{id}")
    public void deleteBoardById(@PathVariable String id) {
        try {
            boardService.deleteBoardById(id);
        } catch (Exception e) {
            System.err.println("Cannot delete Board: " + e.getMessage());
        }
    }
}
