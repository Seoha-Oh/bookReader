package com.sideproject.bookReader.repository;

import com.sideproject.bookReader.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {

}
