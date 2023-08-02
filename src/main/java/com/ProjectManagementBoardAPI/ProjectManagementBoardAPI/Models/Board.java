package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "board")
public class Board {
    @Id
    String id;
    String title;

    @ElementCollection
    @CollectionTable(name = "board_sections", joinColumns = @JoinColumn(name = "board_id"))
    @MapKeyColumn(name = "section_id")
    @Column(name = "section_name")
    private Map<Integer, String> sections = new HashMap<>();

    @Transient
    private List<Section> sectionsWithCards;

//    @OneToMany(mappedBy = "board")
//    private List<Card> cards;
}
