package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Services;

import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models.Section;
import com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            System.out.println("Cannot create card " + e.getMessage());
        }
        return null;
    }

    /*******  Get Section by id  ******/
    public Section getSectionById(Integer id) {
        try {
            return sectionRepository.findById(id).get();
        } catch (Exception e) {
            System.out.println("Cannot get Section with this id " + e.getMessage());
            return null;
        }
    }
}
