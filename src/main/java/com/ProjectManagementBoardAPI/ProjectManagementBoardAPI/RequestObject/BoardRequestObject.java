package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.RequestObject;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Board;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BoardRequestObject {
    String title;
//    public Board convertToBoard() {
//        Board board = new Board();
//        board.setTitle(this.title);
//        Date nowDate = new Date();
//        board.setIsActive(true);
//        board.setCreatedDate(nowDate);
//        board.setUpdatedDate(nowDate);
//        return board;
//    }

    public static Board convert(BoardRequestObject request) {
        Date nowDate = new Date();
        Board board = new Board();

        board.setTitle(request.getTitle());
        board.setIsActive(true);
        board.setCreatedDate(nowDate);
        board.setUpdatedDate(nowDate);
        return board;
    }

    public static List<Board> convert(List<BoardRequestObject> requestList) {
        List<Board> boards = new ArrayList<>();
        if (!requestList.isEmpty()) {
            for (BoardRequestObject boardRequestObject : requestList) {
                boards.add(convert(boardRequestObject));
            }
        }
        return boards;
    }
}
