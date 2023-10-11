package com.example.newsapi.dto.news;

import com.example.newsapi.entity.news.Comment;
import com.example.newsapi.entity.news.News;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
public class NewsAndCommentDto {
    private News news;
    private List<Comment> comments;
}
