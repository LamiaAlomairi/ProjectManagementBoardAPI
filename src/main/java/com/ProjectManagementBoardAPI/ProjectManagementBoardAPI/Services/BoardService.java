package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Board;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories.BoardRepository;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.RequestObject.BoardRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;

    /*******  Create Board  ******/
    public void createBoard(BoardRequestObject boardRequest){
        try{
            Board board = BoardRequestObject.convert(boardRequest);
            boardRepository.save(board);
        }
        catch (Exception e) {
            System.out.println("Cannot create board " + e.getMessage());
        }
    }

    /*******  Get All Board  ******/
    public List<Board> getAllBoards() {
        try {
            return boardRepository.getAllBoards();
        } catch (Exception e) {
            System.out.println("Cannot get all Boards " + e.getMessage());
            return null;
        }
    }

    /*******  Get Board by id  ******/
    public Board getBoardById(String id) {
        try {
            return boardRepository.getBoardById(id);
        }
        catch (Exception e) {
            System.out.println("Cannot get all Board with this id " + e.getMessage());
            return null;
        }
    }

    /****** Update Board ******/
    public void updateBoard(String oldId, String newId, String newTitle) {
        try {
            boardRepository.updateBoard(oldId, newId, newTitle);
        } catch (Exception e) {
            System.out.println("Cannot update Board: " + e.getMessage());
        }
    }
}
