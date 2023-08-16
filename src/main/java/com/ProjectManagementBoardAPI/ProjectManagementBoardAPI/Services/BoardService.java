package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Board;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Card;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Section;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories.BoardRepository;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.RequestObject.BoardRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    SectionService sectionService;

    /*******  Create Board  ******/
    public ResponseEntity<String> createBoard(BoardRequestObject boardRequestObject) {
        try {
            Board board = BoardRequestObject.convert(boardRequestObject);
            Board createdBoard = boardRepository.save(board);

            // Create three sections with specific names for the new board
            String[] sectionNames = {"To Do", "In Progress", "Done"};
            for (String sectionName : sectionNames) {
                Section section = new Section();
                section.setName(sectionName);
                section.setBoard(createdBoard);
                sectionService.createSection(section);
            }

            return ResponseEntity.ok("Board created successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot create board: " + e.getMessage());
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
            return boardRepository.findById(id).get();
        } catch (Exception e) {
            System.out.println("Cannot get Board with this id " + e.getMessage());
            return null;
        }
    }

    /****** Update Board ******/
    public Board updateBoard(Long id, BoardRequestObject boardRequest) {
        try{
            Board existingBoard = boardRepository.findById(id).get();
            if (existingBoard != null) {
                existingBoard.setTitle(boardRequest.getTitle());
                return boardRepository.save(existingBoard);
            } else {
                System.out.println("Cannot update Board: Board not found");
                return null;
            }
        }
        catch (Exception e) {
            System.out.println("Cannot update Board " + e.getMessage());
            return null;
        }
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
        try{
            Board board = boardRepository.findById(boardId).get();
            if (board != null) {
                return board.getSections();
            } else {
                System.out.println("Cannot get sections Board: Board not found");
            }
        }
        catch (Exception e) {
            System.out.println("Cannot delete Board: " + e.getMessage());
        }
        return new ArrayList<>();
    }


    /****** Get in a selected sections in a selected Board ******/

    public List<Card> getCardsInSectionOfBoard(Long boardId, Long sectionId) {
        try {
            Board board = boardRepository.findById(boardId).get();
            if (board != null) {
                for (Section section : board.getSections()) {
                    if (section.getId().equals(sectionId)) {
                        return section.getCards();
                    }
                }
            }
            return new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Cannot get cards in section Board: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
