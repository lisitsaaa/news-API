package com.example.newsapi.repository;

import com.example.newsapi.entity.news.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByTitle(String title);

    List<News> findByText(String text);
}
