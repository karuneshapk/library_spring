package com.mikhail.holub.dao;

import com.mikhail.holub.model.Book;

import java.util.List;


public interface BookDao {

	Book findById(int id);

	void updateBook(Book book);

	void saveBook(Book book);
	
	void deleteById(int id);
	
	List<Book> findAllBooks();

}

