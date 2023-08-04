package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
