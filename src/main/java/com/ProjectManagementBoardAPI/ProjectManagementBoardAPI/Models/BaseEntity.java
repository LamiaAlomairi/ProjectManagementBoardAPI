package com.ProjectManagementBoardAPI.ProjectManagementBoardAPI.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Muscat")
    Date createdDate;
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Muscat")
    Date updatedDate;
}
