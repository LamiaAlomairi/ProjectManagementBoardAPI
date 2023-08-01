package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, String> {
    @Query(value = "select b from Board b")
    List<Board> getAllBoards();
}
