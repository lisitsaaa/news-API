package com.example.newsapi.repository;

import com.example.newsapi.entity.news.Comment;
import com.example.newsapi.entity.news.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByNews(PageRequest pageRequest, News news);
}
