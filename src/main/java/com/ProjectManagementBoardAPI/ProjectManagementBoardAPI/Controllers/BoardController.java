package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Controllers;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Board;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Card;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Section;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.RequestObject.BoardRequestObject;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.ResponseObject.BoardResponseObject;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.ResponseObject.CardResponse;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services.BoardService;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/board")
@CrossOrigin("*")
public class BoardController {
    @Autowired
    BoardService boardService;
    @Autowired
    CardService cardService;

    /*******  Create Board  ******/
    @PostMapping
    public ResponseEntity<Object> createBoard(@RequestBody BoardRequestObject boardRequestObject) {
        try {
            Board createdBoard = boardService.createBoard(boardRequestObject);
            if (createdBoard != null) {
                return ResponseEntity.ok(createdBoard);
            } else {
                return ResponseEntity.badRequest().body("Cannot create board.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot create board: " + e.getMessage());
        }
    }

    /*******  Get All Boards  ******/
    @GetMapping
    public List<BoardResponseObject> getAllBoards() {
        try {
            List<Board> boards = boardService.getAllBoards();
            List<BoardResponseObject> objectList = BoardResponseObject.convertToResponseList(boards);
            return objectList;
        } catch (Exception e) {
            System.err.println("Cannot get all Boards " + e.getMessage());
            return null;
        }
    }

    /*******  Get Board by id  ******/
    @GetMapping(value = "/{id}")
    public BoardResponseObject getBoardById(@PathVariable Long id) {
        try {
            Board board = boardService.getBoardById(id);
            if (board == null) {
                System.err.println("board is not found ");
                return null;
            }
            return BoardResponseObject.convertToResponse(board);
        } catch (Exception e) {
            System.err.println("Cannot get Board with this id " + e.getMessage());
            return null;
        }
    }

    /****** Update Board ******/
    @PutMapping("/{id}")
    public Board updateBoard(@PathVariable Long id, @RequestBody BoardRequestObject update) {
        try {
            return boardService.updateBoard(id, update);
        } catch (Exception e) {
            System.err.println("Cannot update this Board: " + e.getMessage());
        }
        return null;
    }

    /****** Delete Board ******/
    @DeleteMapping(value = "/{id}")
    public void deleteBoardById(@PathVariable Long id) {
        try {
            boardService.deleteBoardById(id);
        } catch (Exception e) {
            System.err.println("Cannot delete Board: " + e.getMessage());
        }
    }

    /****** Get sections in a selected Board ******/
    @GetMapping("/{boardId}/section")
    public List<Section> getAllSectionsInBoard(@PathVariable Long boardId) {
        try{
            return boardService.getAllSectionsInBoard(boardId);
        }
        catch (Exception e) {
            System.err.println("Cannot get sections Board: " + e.getMessage());
        }
        return null;
    }

    /****** Get in a selected sections in a selected Board ******/
    @GetMapping("/{boardId}/sections/{sectionId}/cards")
    public List<CardResponse> getCardsInSectionOfBoard(@PathVariable Long boardId, @PathVariable Long sectionId) {
        try {
            List<Card> cards = boardService.getCardsInSectionOfBoard(boardId, sectionId);
            List<CardResponse> objectList = CardResponse.convertToResponseList(cards);
            return objectList;
        } catch (Exception e) {
            System.err.println("Cannot get cards in section of board: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
