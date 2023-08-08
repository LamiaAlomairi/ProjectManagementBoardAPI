package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Controllers;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Card;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/card")
@CrossOrigin("*")
public class CardController {
    @Autowired
    CardService cardService;

    /*******  Create Card  ******/
    @PostMapping
    public Card createCard(@RequestBody Card card) {
        try {
            return cardService.createCard(card);
        } catch (Exception e) {
            System.err.println("Cannot create Card " + e.getMessage());
            return null;
        }
    }

    /*******  Get All Cards  ******/
    @GetMapping
    public List<Card> getAllCards() {
        try {
            return cardService.getAllCards();
        } catch (Exception e) {
            System.err.println("Cannot get all Cards " + e.getMessage());
            return null;
        }
    }

    /*******  Get Card by id  ******/
    @GetMapping(value = "/{id}")
    public Card getCardById(@PathVariable Long id) {
        try {
            return cardService.getCardById(id);
        } catch (Exception e) {
            System.err.println("Cannot get Card with this id " + e.getMessage());
            return null;
        }
    }

    /****** Update Card ******/
    @PutMapping("/{id}")
    public Card updateCard(@PathVariable Long id, @RequestBody Card card) {
        try {
            return cardService.updateCard(id, card);
        } catch (Exception e) {
            System.err.println("Cannot update this Card: " + e.getMessage());
        }
        return null;
    }

    /****** Delete Card ******/
    @DeleteMapping(value = "/{id}")
    public void deleteCardById(@PathVariable Long id) {
        try {
            cardService.deleteCardById(id);
        } catch (Exception e) {
            System.err.println("Cannot delete Card: " + e.getMessage());
        }
    }
}
