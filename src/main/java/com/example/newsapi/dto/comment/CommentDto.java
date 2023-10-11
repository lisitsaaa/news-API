package com.example.newsapi.dto.comment;

import com.example.newsapi.entity.news.News;
import com.example.newsapi.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor @AllArgsConstructor
public class CommentDto {
    private String text;
    private LocalDate creationDate;
    private LocalDate lastEditDate;
    private User insertedById;
    private News news;
}
