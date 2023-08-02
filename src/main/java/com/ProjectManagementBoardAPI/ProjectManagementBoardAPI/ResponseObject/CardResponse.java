package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.ResponseObject;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CardResponse {
    Integer id;
    String title;
    String description;
    public static CardResponse convertToResponse(Card card) {
        return CardResponse.builder()
                .id(card.getId())
                .title(card.getTitle())
                .description(card.getDescription())
                .build();
    }

    public static List<CardResponse> convertToResponseList(List<Card> response) {
        List<CardResponse> cardResponses = new ArrayList<>();
        if (!response.isEmpty()) {
            for (Card card : response) {
                cardResponses.add(convertToResponse(card));
            }
        }
        return cardResponses;
    }
}
