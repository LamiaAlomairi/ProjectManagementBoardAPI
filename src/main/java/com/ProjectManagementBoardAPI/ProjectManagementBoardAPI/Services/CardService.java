package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Card;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories.CardRepository;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.RequestObject.CardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /*******  Get All Card  ******/
    public List<Card> getAllCards() {
        try {
            return cardRepository.getAllCards();
        } catch (Exception e) {
            System.out.println("Cannot get all Cards " + e.getMessage());
            return null;
        }
    }

    /*******  Get Card by id  ******/
    public Card getCardById(Integer id) {
        try {
            return cardRepository.getCardById(id);
        }
        catch (Exception e) {
            System.out.println("Cannot get all Card with this id " + e.getMessage());
            return null;
        }
    }

    /****** Update Card ******/
    public void updateCard(Integer id, String newTitle, String newDescription) {
        try {
            cardRepository.updateCard(id, newTitle, newDescription);
        } catch (Exception e) {
            System.out.println("Cannot update Card: " + e.getMessage());
        }
    }

    /****** Delete Card ******/
    public void deleteCardById(Integer id) {
        try {
            cardRepository.deleteCardById(id);
        } catch (Exception e) {
            System.out.println("Cannot delete Card: " + e.getMessage());
        }
    }
}
