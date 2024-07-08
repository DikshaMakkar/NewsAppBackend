package com.example.NewsApp;

import com.example.NewsApp.controller.NewsController;
import com.example.NewsApp.model.Article;
import com.example.NewsApp.services.GaurdianNewsService;
import com.example.NewsApp.services.TimesNewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import static junit.framework.TestCase.assertEquals;

import java.util.List;

@SpringBootTest
class NewsAppApplicationTests {

	@Mock
	private GaurdianNewsService gaurdianNewsService;

	@Mock
	private TimesNewsService timesNewsService;

	private NewsController newsController;

	@BeforeEach
	public void setUp() {
		newsController = new NewsController(gaurdianNewsService, timesNewsService);
	}

	@Test
	public void testGetAllNewsArticles_NoSearchKeyword() throws Exception {
		Integer currentPage = 1;
		List<Article> gaurdianArticles = new ArrayList<>();
		Article gaurdianArticle = new Article();
		gaurdianArticle.setArticleTitle("Gaurdian Article 1");
		gaurdianArticles.add(gaurdianArticle);
		Mockito.when(gaurdianNewsService.getArticles(currentPage,null)).thenReturn(gaurdianArticles);

		List<Article> timesArticles = new ArrayList<>();
		Article timesArticle = new Article();
		timesArticle.setArticleTitle("Times Article 1");
		timesArticles.add(timesArticle);
		Mockito.when(timesNewsService.getArticles(currentPage,null)).thenReturn(timesArticles);

		List<Article> allArticles = newsController.getAllNewsArticles(currentPage,null);

		assertEquals(2, allArticles.size());
		assertEquals("Gaurdian Article 1", allArticles.get(0).getArticleTitle());
		assertEquals("Times Article 1", allArticles.get(1).getArticleTitle());
	}

	@Test
	public void testGetAllNewsArticles_WithSearchKeyword() throws Exception {
		Integer currentPage = 1;
		String searchKeyword = "test";
		List<Article> gaurdianArticles = new ArrayList<>();
		Article gaurdianArticle = new Article();
		gaurdianArticle.setArticleTitle("Gaurdian Article with test");
		gaurdianArticles.add(gaurdianArticle);
		Mockito.when(gaurdianNewsService.getArticles(currentPage, searchKeyword)).thenReturn(gaurdianArticles);

		List<Article> timesArticles = new ArrayList<>();
		Article timesArticle = new Article();
		timesArticle.setArticleTitle("Times Article about test");
		timesArticles.add(timesArticle);
		Mockito.when(timesNewsService.getArticles(currentPage, searchKeyword)).thenReturn(timesArticles);

		List<Article> allArticles = newsController.getAllNewsArticles(currentPage, searchKeyword);

		assertEquals(2, allArticles.size());
		assertEquals("Gaurdian Article with test", allArticles.get(0).getArticleTitle());
		assertEquals("Times Article about test", allArticles.get(1).getArticleTitle());
	}
}
