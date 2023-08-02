package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.ResponseObject;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Section;
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
public class SectionResponseObject {
    Integer id;
    String name;
    List<CardResponse> cards;
    public static SectionResponseObject convertToResponse(Section section) {
        return SectionResponseObject.builder()
                .id(section.getId())
                .name(section.getName())
//                .cards(CardResponse.convertToResponseList(section.getCards()))
                .build();
    }

    public static List<SectionResponseObject> convertToResponseList(List<Section> response) {
        return response.stream().map(SectionResponseObject::convertToResponse).collect(Collectors.toList());
    }
}
