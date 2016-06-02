package com.mikhail.holub.service;

import com.mikhail.holub.dao.BookDao;
import com.mikhail.holub.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("bookService")
@Transactional
public class BookServiceImpl implements BookService{

	@Autowired
	private BookDao dao;

	public Book findById(int id) {
		return dao.findById(id);
	}

	public void saveBook(Book book) {
		dao.saveBook(book);
	}

	@Override
	public void updateBook(Book book) {
		Book entity = dao.findById(book.getId());
		if(entity!=null){
			entity.setAghtorNameF(book.getAghtorNameF());
			entity.setAghtorNameL(book.getAghtorNameL());
			entity.setBookName(book.getBookName());
		}
	}

	public void deleteBookById(int id) {
		dao.deleteById(id);
	}

	public List<Book> findAllBooks() {
		return dao.findAllBooks();
	}
	
}
