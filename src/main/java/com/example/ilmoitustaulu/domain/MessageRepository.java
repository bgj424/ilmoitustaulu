package com.example.ilmoitustaulu.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, String> {
	List<Message> findById(int id);
	List<Message> deleteById(int id);
	List<Message> findByCategory(Category category);
}