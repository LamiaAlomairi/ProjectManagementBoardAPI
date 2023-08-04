package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Board;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Section;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    SectionService sectionService;

    /*******  Create Board  ******/
    public Board createBoard(Board board) {
        try {
            // Get all existing sections from the Section table
            List<Section> existingSections = sectionService.getAllSections();

            // Add the existing sections to the board and save them in the mapping table
            for (Section existingSection : existingSections) {
                board.getSections().add(existingSection);
            }

            // Save the board to the database and return the saved board
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
            System.out.println("Cannot Get Boards " + e.getMessage());
            return null;
        }
    }

    /*******  Get Board by id  ******/
    public Board getBoardById(Long id) {
        try {
            return boardRepository.findById(id).orElse(null);
        } catch (Exception e) {
            System.out.println("Cannot get Board with this id " + e.getMessage());
            return null;
        }
    }

    /****** Update Board ******/
    public Board updateBoard(Long id, Board board) {
        try {
            Optional<Board> optionalBoard = boardRepository.findById(id);
            if (optionalBoard.isPresent()) {
                Board existingBoard = optionalBoard.get();
                existingBoard.setName(board.getName());
                return boardRepository.save(existingBoard);
            }
        } catch (Exception e) {
            System.out.println("Cannot update Board: " + e.getMessage());
        }
        return null;
    }

    /****** Delete Board ******/
    public void deleteBoardById(Long id) {
        try {
            boardRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Cannot delete Board: " + e.getMessage());
        }
    }
}
