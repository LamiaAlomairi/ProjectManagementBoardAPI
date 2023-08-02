package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Controllers;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Section;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/section")
public class SectionController {
    @Autowired
    SectionService sectionService;

    /*******  Create Section  ******/
    @PostMapping
    public Section createSection(@RequestBody Section section) {
        try {
            return sectionService.createSection(section);
        } catch (Exception e) {
            System.err.println("Cannot create card: " + e.getMessage());
        }
        return null;
    }
}
