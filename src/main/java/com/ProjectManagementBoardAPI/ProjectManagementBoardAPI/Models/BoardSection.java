//package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class BoardSection {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//    @ManyToOne
//    @JoinColumn(name = "board_id")
//    private Board board;
//
//    @ManyToOne
//    @JoinColumn(name = "section_id")
//    private Section section;
//
//    public BoardSection() {
//    }
//    public BoardSection(Board board, Section section) {
//        this.board = board;
//        this.section = section;
//    }
//}
