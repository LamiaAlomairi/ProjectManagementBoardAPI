package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.ResponseObject;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BoardResponseObject {
    String id;
    String title;
    List<SectionResponseObject> sections;
    public static BoardResponseObject convertToResponse(Board board) {
        return BoardResponseObject.builder()
                .id(board.getId())
                .title(board.getTitle())
                .sections(SectionResponseObject.convertToResponseList(board.getSectionsWithCards()))
                .build();
    }

    public static List<BoardResponseObject> convertToResponseList(List<Board> response) {
        return response.stream().map(BoardResponseObject::convertToResponse).collect(Collectors.toList());
    }
}
