package com.mikhail.holub.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="APP_BOOK")
public class Book implements Serializable{

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty
	@Column(name="BOOK_NAME", unique=true, nullable=false)
	private String bookName;
	
	@NotEmpty
	@Column(name="FIRST_NAME_AUGHTOR", nullable=false)
	private String aghtorNameF;
		
	@NotEmpty
	@Column(name="LAST_NAME_AUGHTOR", nullable=false)
	private String aghtorNameL;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAghtorNameF() {
		return aghtorNameF;
	}

	public void setAghtorNameF(String aghtorNameF) {
		this.aghtorNameF = aghtorNameF;
	}

	public String getAghtorNameL() {
		return aghtorNameL;
	}

	public void setAghtorNameL(String aghtorNameL) {
		this.aghtorNameL = aghtorNameL;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Book)) return false;

		Book book = (Book) o;

		if (getId() != null ? !getId().equals(book.getId()) : book.getId() != null) return false;
		if (getBookName() != null ? !getBookName().equals(book.getBookName()) : book.getBookName() != null) return false;
		if (getAghtorNameF() != null ? !getAghtorNameF().equals(book.getAghtorNameF()) : book.getAghtorNameF() != null)
			return false;
		return getAghtorNameL() != null ? getAghtorNameL().equals(book.getAghtorNameL()) : book.getAghtorNameL() == null;

	}

	@Override
	public int hashCode() {
		int result = getId() != null ? getId().hashCode() : 0;
		result = 31 * result + (getBookName() != null ? getBookName().hashCode() : 0);
		result = 31 * result + (getAghtorNameF() != null ? getAghtorNameF().hashCode() : 0);
		result = 31 * result + (getAghtorNameL() != null ? getAghtorNameL().hashCode() : 0);
		return result;
	}
}
