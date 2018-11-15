package com.sdust.base;

import java.util.List;

public interface IBaseDao<T> {
	void add(T t);
	void delete(T t);
	void update(T t);
	List<T> findAll();
	int count();
}
