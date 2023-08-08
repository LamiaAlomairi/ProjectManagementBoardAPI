package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Board;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Card;
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
            Board createdBoard = boardRepository.save(board);

            // Create three sections with specific names for the new board
            String[] sectionNames = {"To Do", "In Progress", "Done"};
            for (String sectionName : sectionNames) {
                Section section = new Section();
                section.setName(sectionName);
                section.setBoard(createdBoard);
                sectionService.createSection(section);
            }
            return createdBoard;
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
                existingBoard.setTitle(board.getTitle());
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

    /****** Get sections in a selected Board ******/
    public List<Section> getAllSectionsInBoard(Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            return board.getSections();
        }
        return null;
    }

    /****** Get in a selected sections in a selected Board ******/
    public List<Card> getCardsInSectionOfBoard(Long boardId, Long sectionId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();

            // Check if the section belongs to the board
            Section section = board.getSections().stream()
                    .filter(s -> s.getId().equals(sectionId))
                    .findFirst()
                    .orElse(null);

            if (section != null) {
                return section.getCards();
            }
        }
        return null;
    }
}
