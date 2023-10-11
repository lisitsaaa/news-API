package com.example.newsapi.mapper;

import com.example.newsapi.dto.news.NewsDto;
import com.example.newsapi.dto.news.PostDto;
import com.example.newsapi.entity.news.News;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewsMapper {
    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    News postDtoToNews(PostDto postDto);
    NewsDto newsToNewsDto(News news);
}
