package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.RequestObject;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Card;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
public class CardRequest {
    String title;
    String description;
    public static Card convert(CardRequest request) {
        Date nowDate = new Date();
        Card card = new Card();

        card.setTitle(request.getTitle());
        card.setDescription(request.getDescription());
        card.setIsActive(true);
        card.setCreatedDate(nowDate);
        card.setUpdatedDate(nowDate);
        return card;
    }

    public static List<Card> convert(List<CardRequest> requestList) {
        List<Card> cards = new ArrayList<>();
        if (!requestList.isEmpty()) {
            for (CardRequest cardRequest : requestList) {
                cards.add(convert(cardRequest));
            }
        }
        return cards;
    }
}
