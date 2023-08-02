package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "board")
public class Board {
    @Id
    String id;
    String title;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardSection> sections = new ArrayList<>();

    @PostPersist
    public void createSections() {
        Section toDo = new Section("To Do", 1);
        Section inProgress = new Section("In Progress", 2);
        Section done = new Section("Done", 3);

        BoardSection toDoSection = new BoardSection(this, toDo);
        BoardSection inProgressSection = new BoardSection(this, inProgress);
        BoardSection doneSection = new BoardSection(this, done);

        sections.add(toDoSection);
        sections.add(inProgressSection);
        sections.add(doneSection);
    }
}
