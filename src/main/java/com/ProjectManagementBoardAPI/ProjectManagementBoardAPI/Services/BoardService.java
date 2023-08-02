package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Board;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Section;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories.BoardRepository;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.RequestObject.BoardRequestObject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;

    /*******  Create Board  ******/
//    public void createBoard(BoardRequestObject boardRequest){
//        try{
//            Board board = BoardRequestObject.convert(boardRequest);
//            boardRepository.save(board);
//        }
//        catch (Exception e) {
//            System.out.println("Cannot create board " + e.getMessage());
//        }
//    }
    @Transactional
    public void createBoard(BoardRequestObject boardRequest) {
        try {
            Board board = BoardRequestObject.convert(boardRequest);

            // Create sections and associate them with the board
            String[] sectionNames = {"To Do", "In Progress", "Done"};
            for (String name : sectionNames) {
                Section section = new Section();
                section.setName(name);
                section.setBoard(board);
                board.getSections().add(section);
            }

            // Save the board with its sections
            boardRepository.save(board);
        } catch (Exception e) {
            System.out.println("Cannot create board " + e.getMessage());
        }
    }

    /*******  Get All Board  ******/
    public List<Board> getAllBoards() {
        try {
            return boardRepository.findAll();
        } catch (Exception e) {
            System.out.println("Cannot get all Boards " + e.getMessage());
            return null;
        }
    }

    /*******  Get Board by id  ******/
    public Board getBoardById(String id) {
        try {
            return boardRepository.findById(id).get();
        }
        catch (Exception e) {
            System.out.println("Cannot get all Board with this id " + e.getMessage());
            return null;
        }
    }

    /****** Update Board ******/
    public Board updateBoard(String id, Board board) {
        try {
            Optional<Board> optionalBoard = boardRepository.findById(id);
            if (optionalBoard.isPresent()) {
                Board existingBoard = optionalBoard.get();
                existingBoard.setTitle(board.getTitle());
                return boardRepository.save(existingBoard);
            }
        } catch (Exception e) {
            System.out.println("Cannot update Board: " + e.getMessage());
        }
        return null;
    }

    /****** Delete Board ******/
    public void deleteBoardById(String id) {
        try {
            boardRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Cannot delete Board: " + e.getMessage());
        }
    }
}
