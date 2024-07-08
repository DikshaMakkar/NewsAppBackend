package com.example.NewsApp.services;

import com.example.NewsApp.model.Article;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface NewsService {
    List<Article> getArticles(Integer currentPage, String searchKeyword) throws JsonProcessingException;
}
