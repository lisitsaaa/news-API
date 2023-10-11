package com.example.newsapi.service;

import com.example.newsapi.entity.news.News;
import com.example.newsapi.exception.NotFoundException;
import com.example.newsapi.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    @Value("Not found") private String NOT_FOUND_MESSAGE;
    @Value("There is no news") private String NO_NEWS;

    public News save(News news){
        return newsRepository.save(news);
    }

    public void remove(long id){
        newsRepository.delete(findById(id));
    }

    public News update(News news){
        return save(news);
    }

    @Transactional(readOnly = true)
    public List<News> findAllWithPagination(PageRequest pageRequest){
        return newsRepository.findAll(pageRequest).getContent();
    }

    @Transactional(readOnly = true)
    public News findById(long id) {
       return getCheckedNews(newsRepository.findById(id));
    }

    @Transactional(readOnly = true)
    public List<News> findByTitle(String title) {
        return getCheckedNewsList(newsRepository.findByTitle(title));
    }

    @Transactional(readOnly = true)
    public List<News> findByText(String text) {
        return getCheckedNewsList(newsRepository.findByText(text));
    }

    private List<News> getCheckedNewsList(List<News> news) {
        if (news.isEmpty()) {
            throw new NotFoundException(NO_NEWS);
        }
        return news;
    }

    private News getCheckedNews(Optional<News> news){
        if (news.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        return news.get();
    }
}
