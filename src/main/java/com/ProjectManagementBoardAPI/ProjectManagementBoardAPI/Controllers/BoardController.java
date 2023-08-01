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
    public void createBoard(@RequestBody BoardRequestObject boardRequest) {
        try {
            boardService.createBoard(boardRequest);
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
}
