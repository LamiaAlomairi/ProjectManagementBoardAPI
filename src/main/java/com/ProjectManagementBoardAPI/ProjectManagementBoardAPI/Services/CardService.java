package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Card;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Section;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private SectionService sectionService;

    /*******  Create Card  ******/
    public void createCard(Card card){
        try {
            Section section = sectionService.getSectionById(card.getSection().getId());
            card.setSection(section);
            cardRepository.save(card);
        } catch (Exception e) {
            System.out.println("Cannot create card " + e.getMessage());
        }
    }

    /*******  Get All Card  ******/
    public List<Card> getAllCards() {
        try {
            return cardRepository.findAll();
        } catch (Exception e) {
            System.out.println("Cannot get all Cards " + e.getMessage());
            return null;
        }
    }

    /*******  Get Card by id  ******/
    public Card getCardById(Integer id) {
        try {
            return cardRepository.findById(id).get();
        }
        catch (Exception e) {
            System.out.println("Cannot get all Card with this id " + e.getMessage());
            return null;
        }
    }

    /****** Update Card ******/
    public Card updateCard(@PathVariable Integer id, @RequestBody Card card) {
        try {
            Optional<Card> optionalCard = cardRepository.findById(id);
            if (optionalCard.isPresent()) {
                Card existingCard = optionalCard.get();
                existingCard.setTitle(card.getTitle());
                existingCard.setDescription(card.getDescription());
                return cardRepository.save(existingCard);
            }
        } catch (Exception e) {
            System.out.println("Cannot update Card: " + e.getMessage());
        }
        return null;
    }

    /****** Delete Card ******/
    public void deleteCardById(Integer id) {
        try {
            cardRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Cannot delete Card: " + e.getMessage());
        }
    }
}
