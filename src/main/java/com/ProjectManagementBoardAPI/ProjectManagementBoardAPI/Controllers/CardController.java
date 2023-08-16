package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Controllers;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Card;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.RequestObject.CardRequest;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.ResponseObject.CardResponse;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/card")
@CrossOrigin("*")
public class CardController {
    @Autowired
    CardService cardService;

    @PostMapping
    public String createCard(@RequestBody CardRequest cardRequest) {
        try {
            return cardService.createCard(cardRequest);
        } catch (Exception e) {
            System.err.println("Cannot create Card: " + e.getMessage());
            return "An error occurred while creating the card.";
        }
    }

    @GetMapping
    public List<CardResponse> getAllCards() {
        try {
            List<Card> cards = cardService.getAllCards();
            List<CardResponse> objectList = CardResponse.convertToResponseList(cards);
            return objectList;
        } catch (Exception e) {
            System.err.println("Cannot get all Cards " + e.getMessage());
            return null;
        }
    }

    @GetMapping(value = "/{id}")
    public CardResponse getCardById(@PathVariable Long id) {
        try {
            Card card = cardService.getCardById(id);
            if (card == null) {
                System.err.println("Card not found");
                return null;
            }
            return CardResponse.convertToResponse(card);
        } catch (Exception e) {
            System.err.println("Cannot get Card with this id " + e.getMessage());
            return null;
        }
    }

    @PutMapping("/{id}")
    public Card updateCard(@PathVariable Long id, @RequestBody CardRequest card) {
        try {
            return cardService.updateCard(id, card);
        } catch (Exception e) {
            System.err.println("Cannot update this Card: " + e.getMessage());
        }
        return null;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCardById(@PathVariable Long id) {
        try {
            cardService.deleteCardById(id);
        } catch (Exception e) {
            System.err.println("Cannot delete Card: " + e.getMessage());
        }
    }
}
