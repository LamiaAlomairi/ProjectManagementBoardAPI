package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Controllers;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Section;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/{id}")
    public void getSectionById(@PathVariable Integer id) {
        try {
            sectionService.getSectionById(id);
        } catch (Exception e) {
            System.err.println("Cannot get Card with this id " + e.getMessage());
        }
    }
}
