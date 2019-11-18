package com.hampcode.articlesapp.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hampcode.articlesapp.model.Article;

@Repository
public interface ArticleRepository extends 
   PagingAndSortingRepository<Article, Long> {

	Page<Article> findAll(Pageable pageable);
}
