package com.example.NewsApp.mapper;

import com.example.NewsApp.model.Article;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ArticleMapper {
    @Autowired
    private ObjectMapper objectMapper;
    public List<Article> mapGuardianResponseToArticles(String response) throws JsonProcessingException {
        List<Article> articles = new ArrayList<>();
        JsonNode rootNode = objectMapper.readValue(response, JsonNode.class);
        JsonNode resultsNode = rootNode.get("response").get("results");
        if(resultsNode.isArray()){
            for(JsonNode articleNode: resultsNode) {
                Article article = new Article();
                article.setArticleId(articleNode.get("id").asText());
                article.setArticleTitle(articleNode.get("webTitle").asText());
                article.setArticleType(articleNode.get("pillarName").asText());
                article.setArticleUrl(articleNode.get("webUrl").asText());
                articles.add(article);
            }
        }
        return articles;
    }

    public List<Article> mapTimesResponseToArticles(String response) throws JsonProcessingException {
        List<Article> articles = new ArrayList<>();
        JsonNode rootNode = objectMapper.readValue(response, JsonNode.class);
        JsonNode resultsNode = rootNode.get("response").get("docs");
        if(resultsNode.isArray()){
            for(JsonNode articleNode: resultsNode) {
                Article article = new Article();
                article.setArticleId(articleNode.get("_id").asText());
                article.setArticleTitle(articleNode.get("headline").get("main").asText());
                article.setArticleType(Objects.nonNull(articleNode.get("type")) ? articleNode.get("type").asText() : null);
                article.setArticleUrl(articleNode.get("web_url").asText());
                articles.add(article);
            }
        }
        return articles;
    }
}
