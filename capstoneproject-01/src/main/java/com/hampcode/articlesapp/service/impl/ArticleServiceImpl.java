package com.hampcode.articlesapp.service.impl;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hampcode.articlesapp.exception.ResourceNotFoundException;
import com.hampcode.articlesapp.model.Article;
import com.hampcode.articlesapp.repository.ArticleRepository;
import com.hampcode.articlesapp.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	@Override
	public List<Article> getAll() {
		List<Article> articles = new ArrayList<>();
		articleRepository.findAll().iterator().forEachRemaining(articles::add);
		return articles;
	}

	@Override
	public Article create(Article entity) {
		Article newArticle;
		newArticle = articleRepository.save(entity);
		return newArticle;
	}

	@Override
	public Article update(Long id, Article entity) {
		Article article = findById(id);

		article.setCategory(entity.getCategory());
		article.setTitle(entity.getTitle());
		article.setAuthor(entity.getAuthor());
		article.setDescription(entity.getDescription());
		article.setContent(entity.getContent());

		articleRepository.save(article);
		return article;
	}

	@Override
	public void delete(Long id) {
		articleRepository.delete(findById(id));
	}

	@Override
	public Article findById(Long id) {
		Optional<Article> article = articleRepository.findById(id);

		if (!article.isPresent()) {
			throw new ResourceNotFoundException("There is no Article with ID = " + id);
		}

		return article.get();

	}

	@Override
	public Page<Article> findAll(Pageable pageable) {
		return articleRepository.findAll(pageable);
	}
}