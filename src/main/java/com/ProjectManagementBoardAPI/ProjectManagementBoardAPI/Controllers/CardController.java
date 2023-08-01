package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Controllers;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.RequestObject.CardRequest;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
