package com.example.ilmoitustaulu.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Message {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String title;
	private String text;
	private String author;
	private Date date;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="categoryid")
	private Category category;
	
	public Message() {
	}
	
	public Message(int id, String title, String text, String author, Date date, Category category) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.text = text;
		this.date = date;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setDepartment(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", title=" + title + ", text=" + text + ", author=" + author + ", date=" + date
				+ ", category=" + category + "]";
	}



	
}
