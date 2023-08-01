package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Card;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Integer> {
    @Query(value = "select c from Card c")
    List<Card> getAllCards();

    @Query(value = "select c from Card c where c.id = :cardId")
    Card getCardById(@Param("cardId") String id);

    @Modifying
    @Transactional
    @Query("UPDATE Card c SET c.id = :newId, c.title = :newTitle, c.description = :newDescription WHERE c.id = :oldId")
    void updateCard(@Param("oldId") String oldId, @Param("newId") String newId, @Param("newTitle") String newTitle, @Param("newDescription") String newDescription);

    @Modifying
    @Transactional
    @Query("DELETE FROM Card c WHERE c.id = :cardId")
    void deleteCardById(@Param("cardId") String id);
}
