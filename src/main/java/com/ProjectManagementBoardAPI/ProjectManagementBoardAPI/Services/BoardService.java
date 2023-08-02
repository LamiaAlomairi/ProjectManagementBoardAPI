package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Board;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories.BoardRepository;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    SectionRepository sectionRepository;

    /*******  Create Board  ******/
    public Board createBoard(Board board) {
        try {
            String[] sectionNames = {"To Do", "In Progress", "Done"};
            Map<Integer, String> sectionsMap = new HashMap<>();

            for (int i = 1; i <= sectionNames.length; i++) {
                sectionsMap.put(i, sectionNames[i - 1]);
            }

            board.setSections(sectionsMap);
            return boardRepository.save(board);
        } catch (Exception e) {
            System.out.println("Cannot create board " + e.getMessage());
            return null;
        }
    }

    /*******  Get All Board  ******/
    public List<Board> getAllBoards() {
        try {
            return boardRepository.findAll();
        } catch (Exception e) {
            System.out.println("Cannot get all Boards: " + e.getMessage());
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
