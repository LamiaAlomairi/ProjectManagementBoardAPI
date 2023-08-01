package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Controllers;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.RequestObject.BoardRequestObject;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
