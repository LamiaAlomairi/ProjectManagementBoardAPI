package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, String> {
    @Query(value = "select b from Board b")
    List<Board> getAllBoards();

    @Query(value = "select b from Board b where b.id = :boardId")
    Board getBoardById(@Param("boardId") String id);
}
