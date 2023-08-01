package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "board")
public class Board extends BaseEntity {
    @Id
    String id;
    String title;
}
