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

public class TimesNewsService implements NewsService{

    private final String TIMES_API_URL;

    private final String TIMES_API_KEY;

    public TimesNewsService(@Value("${times.api.url}")String timesApiUrl,
                            @Value("${times.api.key}")String timesApiKey){
        this.TIMES_API_URL = timesApiUrl;
        this.TIMES_API_KEY = timesApiKey;
    }

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public List<Article> getArticles(Integer currentPage, String searchKeyword) throws JsonProcessingException {
        String apiURL;
        apiURL = String.format("%s/svc/search/v2/articlesearch.json?q=%s&api-key=%s&page=%d",
                TIMES_API_URL,
                searchKeyword != null ? searchKeyword : "",
                TIMES_API_KEY,
                currentPage);
        System.out.println((apiURL));
        WebClient.Builder builder = WebClient.builder();

        Mono<String> responseMono = builder.build()
                .get()
                .uri(apiURL)
                .retrieve()
                .bodyToMono(String.class);
        String response = responseMono.block();
        List<Article> newsArticles = articleMapper.mapTimesResponseToArticles(response);
        return newsArticles;
    }
}
