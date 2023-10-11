package com.example.newsapi.service;

import com.example.newsapi.entity.news.News;
import com.example.newsapi.entity.user.User;
import com.example.newsapi.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;

    public News save(News news){
        return newsRepository.save(news);
    }
}
