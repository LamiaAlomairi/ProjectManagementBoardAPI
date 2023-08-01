package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Board;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories.BoardRepository;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.RequestObject.BoardRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;

    public void addBoard(BoardRequestObject boardRequest){
        Board board = BoardRequestObject.convert(boardRequest);
        boardRepository.save(board);
    }
}
