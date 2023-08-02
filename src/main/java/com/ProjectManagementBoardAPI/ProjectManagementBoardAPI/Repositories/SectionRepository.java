package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Integer> {
    Section findByName(String name);
}
