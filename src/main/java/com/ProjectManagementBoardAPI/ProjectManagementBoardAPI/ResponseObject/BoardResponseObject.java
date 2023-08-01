package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.ResponseObject;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Board;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class BoardResponseObject {
    String id;
    String title;
    public static BoardResponseObject convertToResponse(Board board) {
        return BoardResponseObject.builder()
                .id(board.getId())
                .title(board.getTitle())
                .build();
    }

    public static List<BoardResponseObject> convertToResponseList(List<Board> response) {
        List<BoardResponseObject> boardResponseObjects = new ArrayList<>();
        if (!response.isEmpty()) {
            for (Board board : response) {
                boardResponseObjects.add(convertToResponse(board));
            }
        }
        return boardResponseObjects;
    }
}
