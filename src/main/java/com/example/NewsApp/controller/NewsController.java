package com.example.NewsApp.controller;

import com.example.NewsApp.model.Article;
import com.example.NewsApp.services.GaurdianNewsService;
import com.example.NewsApp.services.TimesNewsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/news")
public class NewsController {
    @Autowired
    private final GaurdianNewsService gaurdianNewsService;

    @Autowired
    private final TimesNewsService timesNewsService;

    public NewsController(GaurdianNewsService gaurdianNewsService, TimesNewsService timesNewsService) {
        this.gaurdianNewsService = gaurdianNewsService;
        this.timesNewsService = timesNewsService;
    }

    @GetMapping
    public List<Article> getAllNewsArticles(@RequestParam(required = true) Integer page, @RequestParam(required = false) String searchKeyword) throws JsonProcessingException {
        List<Article> gaurdianNewsArticles = gaurdianNewsService.getArticles(page, searchKeyword);
        List<Article> timesNewsArticles = timesNewsService.getArticles(page, searchKeyword);

        List<Article> allArticles = new ArrayList<>();
        allArticles.addAll(gaurdianNewsArticles);
        allArticles.addAll(timesNewsArticles);

        return allArticles;
    }

    @GetMapping("/guardian-news")
    public List<Article> getGuardianNewsArticles(@RequestParam(required = true) Integer page, @RequestParam(required = false) String searchKeyword) throws JsonProcessingException {
        List<Article> gaurdianNewsArticles = gaurdianNewsService.getArticles(page, searchKeyword);

        return gaurdianNewsArticles;
    }

    @GetMapping("/times-news")
    public List<Article> getTimesNewsArticles(@RequestParam(required = true) Integer page, @RequestParam(required = false) String searchKeyword) throws JsonProcessingException {
        List<Article> timesNewsArticles = timesNewsService.getArticles(page, searchKeyword);

        return timesNewsArticles;
    }
}
