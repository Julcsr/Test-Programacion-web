package com.hampcode.articlesapp.service;

import java.util.List;

public interface CrudService<T, ID> {

	List<T> getAll();

	T create(T entity);

	T update(ID id, T entity);

	void delete(ID id);

	T findById(ID id);

}
