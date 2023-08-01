package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Controllers;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Card;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.RequestObject.CardRequest;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.ResponseObject.CardResponse;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/card")
public class CardController {
    @Autowired
    CardService cardService;

    /*******  Create Card  ******/
    @PostMapping
    public void createCard(@RequestBody CardRequest cardRequest) {
        try {
            cardService.createCard(cardRequest);
        } catch (Exception e) {
            System.err.println("Cannot create card: " + e.getMessage());
        }
    }

    /*******  Get All Cards  ******/
    @GetMapping
    public List<CardResponse> getAllCards() {
        try {
            List<Card> cards = cardService.getAllCards();
            List<CardResponse> listOfConvertedCards = CardResponse.convertToResponseList(cards);
            return listOfConvertedCards;
        } catch (Exception e) {
            System.err.println("Cannot get all Cards " + e.getMessage());
            return null;
        }
    }

    /*******  Get Card by id  ******/
    @GetMapping(value = "/{id}")
    public Card getCardById(@PathVariable Integer id) {
        try {
            return cardService.getCardById(id);
        } catch (Exception e) {
            System.err.println("Cannot get Card with this id " + e.getMessage());
            return null;
        }
    }

    /****** Update Card ******/
    @PutMapping("/{id}")
    public Card updateCard(@PathVariable Integer id, @RequestBody Card card) {
        try {
            return cardService.updateCard(id, card);
        } catch (Exception e) {
            System.err.println("Cannot update this Card: " + e.getMessage());
        }
        return null;
    }

    /****** Delete Card ******/
    @DeleteMapping(value = "/{id}")
    public void deleteCardById(@PathVariable Integer id) {
        try {
            cardService.deleteCardById(id);
        } catch (Exception e) {
            System.err.println("Cannot delete Card: " + e.getMessage());
        }
    }
}
