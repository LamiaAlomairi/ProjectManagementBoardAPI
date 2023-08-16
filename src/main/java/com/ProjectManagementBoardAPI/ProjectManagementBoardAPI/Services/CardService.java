package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Board;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Card;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Section;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories.BoardRepository;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories.CardRepository;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories.SectionRepository;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.RequestObject.CardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private SectionRepository sectionRepository;

    public String createCard(CardRequest cardRequest) {
        try {
            Board board = null;
            Section section = null;

            // Find the specified Board if provided
            if (cardRequest.getBoardId() != null) {
                board = boardRepository.findById(cardRequest.getBoardId()).orElse(null);
                if (board == null) {
                    return "Board not found.";
                }
            }

            // Find the specified Section if provided
            if (cardRequest.getSectionId() != null) {
                section = sectionRepository.findById(cardRequest.getSectionId()).orElse(null);
                if (section == null) {
                    return "Section not found.";
                }
            }

            Card card = CardRequest.convert(cardRequest, board, section);

            // Make sure the section is associated with the board
            if (section != null) {
                section.setBoard(board);
                sectionRepository.save(section);
            }

            cardRepository.save(card);
            return "Card created successfully.";
        } catch (Exception e) {
            return "An error occurred: " + e.getMessage();
        }
    }

    public List<Card> getAllCards() {
        try {
            return cardRepository.findAll();
        } catch (Exception e) {
            System.out.println("Cannot get all Cards " + e.getMessage());
            return null;
        }
    }

    public Card getCardById(Long id) {
        try {
            return cardRepository.findById(id).orElse(null);
        } catch (Exception e) {
            System.out.println("Cannot get Card with this id " + e.getMessage());
            return null;
        }
    }

    public Card updateCard(Long id, CardRequest cardRequest) {
        Card existingCard = cardRepository.findById(id).orElse(null);
        if (existingCard != null) {
            existingCard.setTitle(cardRequest.getTitle());
            existingCard.setDescription(cardRequest.getDescription());

            // Associate the new Section if sectionId is provided
            if (cardRequest.getSectionId() != null) {
                Section section = sectionRepository.findById(cardRequest.getSectionId()).orElse(null);
                existingCard.setSection(section);
            }

            return cardRepository.save(existingCard);
        } else {
            System.out.println("Cannot update Card: Card not found");
            return null;
        }
    }

    public void deleteCardById(Long id) {
        try {
            cardRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Cannot delete Card: " + e.getMessage());
        }
    }
}
