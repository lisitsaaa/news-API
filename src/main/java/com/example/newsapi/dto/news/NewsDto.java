package com.example.newsapi.dto.news;

import com.example.newsapi.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor @AllArgsConstructor
public class NewsDto {
    private String title;
    private String text;
    private LocalDate creationDate;
    private LocalDate lastEditDate;
    private User insertedByUserId;
    private User updatedByUserId;
}
