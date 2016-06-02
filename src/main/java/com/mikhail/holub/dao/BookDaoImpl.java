package com.mikhail.holub.dao;

import com.mikhail.holub.model.Book;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("bookDao")
public class BookDaoImpl extends AbstractDao<Integer, Book> implements BookDao {

    static final Logger logger = LoggerFactory.getLogger(BookDaoImpl.class);

    @SuppressWarnings("unchecked")
    public List<Book> findAllBooks() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
        List<Book> books = (List<Book>) criteria.list();
        return books;
    }

    @Override
    public Book findById(int id) {
        Book book = getByKey(id);
        return book;
    }

    @Override
    public void updateBook(Book book) {

    }

    public void saveBook(Book book) {
        persist(book);
    }

    public void deleteById(int id) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id", id));
        Book book = (Book) criteria.uniqueResult();
        delete(book);
    }

}
