package com.mikhail.holub.service;

import com.mikhail.holub.model.Book;

import java.util.List;


public interface BookService {

	Book findById(int id);
	
	void saveBook(Book book);

	void updateBook(Book book);

	void deleteBookById(int id);

	List<Book> findAllBooks();
}