package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "sections")
    @JsonIgnore
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "section")
    @JsonIgnore
    private List<Card> cards = new ArrayList<>();
}
