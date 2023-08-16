package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.RequestObject;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Card;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Board;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Section;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CardRequest {
    private String title;
    private String description;
    private Long boardId;
    private Long sectionId;

    public static Card convert(CardRequest request, Board board, Section section) {
        Date nowDate = new Date();
        Card card = new Card();

        card.setTitle(request.getTitle());
        card.setDescription(request.getDescription());
        card.setIsActive(true);
        card.setCreatedDate(nowDate);
        card.setUpdatedDate(nowDate);

        // Associate with Board and Section if provided
        card.setBoard(board);
        card.setSection(section);

        return card;
    }

//    public static List<Card> convert(List<CardRequest> requestList) {
//        List<Card> cards = new ArrayList<>();
//        if (!requestList.isEmpty()) {
//            for (CardRequest cardRequest : requestList) {
//                cards.add(convert(cardRequest));
//            }
//        }
//        return cards;
//    }
}
