package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Section;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {
    @Autowired
    SectionRepository sectionRepository;

    /*******  Create Section  ******/
    public Section createSection(Section section){
        try{
            return sectionRepository.save(section);
        }
        catch (Exception e) {
            System.out.println("Cannot create section " + e.getMessage());
            return null;
        }
    }

    /*******  Get All Section  ******/
    public List<Section> getAllSections() {
        try {
            return sectionRepository.findAll();
        } catch (Exception e) {
            System.out.println("Cannot get all Sections " + e.getMessage());
            return null;
        }
    }

    /*******  Get Section by id  ******/
    public Section getSectionById(Long id) {
        try {
            return sectionRepository.findById(id).orElse(null);
        } catch (Exception e) {
            System.out.println("Cannot get Section with this id " + e.getMessage());
            return null;
        }
    }
}
