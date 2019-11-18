package com.hampcode.articlesapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hampcode.articlesapp.model.Article;

public interface ArticleService extends CrudService<Article, Long> {

	Page<Article> findAll(Pageable pageable);
}