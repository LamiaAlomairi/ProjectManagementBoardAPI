package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Card;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories.CardRepository;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.RequestObject.CardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;

    /*******  Create Card  ******/
    public void createCard(CardRequest cardRequest){
        try{
            Card card = CardRequest.convert(cardRequest);
            cardRepository.save(card);
        }
        catch (Exception e) {
            System.out.println("Cannot create card " + e.getMessage());
        }
    }
}
