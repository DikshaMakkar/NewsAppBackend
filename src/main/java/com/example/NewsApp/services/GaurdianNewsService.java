package com.example.NewsApp.services;

import com.example.NewsApp.mapper.ArticleMapper;
import com.example.NewsApp.model.Article;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GaurdianNewsService implements NewsService {

    private final String GUARDIAN_API_URL;

    private final String GUARDIAN_API_KEY;

    public GaurdianNewsService(@Value("${guardian.api.url}")String guardianApiUrl,
                               @Value("${guardian.api.key}")String guardianApiKey) {
        this.GUARDIAN_API_URL = guardianApiUrl;
        this.GUARDIAN_API_KEY = guardianApiKey;

    }

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public List<Article> getArticles(Integer currentPage, String searchKeyword) throws JsonProcessingException {
        String apiURL;
        apiURL = String.format("%s/search?q=%s&api-key=%s&page=%d",
                GUARDIAN_API_URL,
                searchKeyword != null ? searchKeyword : "",
                GUARDIAN_API_KEY,
                currentPage);
        System.out.println((apiURL));
        WebClient.Builder builder = WebClient.builder();

        Mono<String> responseMono = builder.build()
                .get()
                .uri(apiURL)
                .retrieve()
                .bodyToMono(String.class);
        String response = responseMono.block();
        List<Article> newsArticles = articleMapper.mapGuardianResponseToArticles(response);
        return newsArticles;
    }
}
