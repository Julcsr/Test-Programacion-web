package com.hampcode.articlesapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hampcode.articlesapp.exception.ResourceNotFoundException;
import com.hampcode.articlesapp.model.Articulo;
import com.hampcode.articlesapp.repository.ArticuloRepository;
import com.hampcode.articlesapp.service.ArticuloService;

@Service
public class ArticuloServiceImpl implements ArticuloService{
	@Autowired
	private ArticuloRepository articleRepository;

	@Override
	public List<Articulo> getAll() {
		List<Articulo> articles = new ArrayList<>();
		articleRepository.findAll().iterator().forEachRemaining(articles::add);
		return articles;
	}

	@Override
	public Articulo create(Articulo entity) {
		Articulo newArticulo;
		newArticulo = articleRepository.save(entity);
		return newArticulo;
	}

	@Override
	public Articulo update(Long id, Articulo entity) {
		Articulo article = findById(id);
		articleRepository.save(article);
		return article;
	}

	@Override
	public void delete(Long id) {
		articleRepository.delete(findById(id));
	}

	@Override
	public Articulo findById(Long id) {
		Optional<Articulo> article = articleRepository.findById(id);

		if (!article.isPresent()) {
			throw new ResourceNotFoundException("There is no Articulo with ID = " + id);
		}

		return article.get();

	}

	@Override
	public Page<Articulo> findAll(Pageable pageable) {
		return articleRepository.findAll(pageable);
	}
}
