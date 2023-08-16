package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Setter
@Getter
@MappedSuperclass
public class BaseEntity {
    Boolean isActive;
    @CreatedDate
    Date createdDate;
    @UpdateTimestamp
    Date updatedDate;
}
