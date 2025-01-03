package com.sideproject.bookReader.service;

import com.sideproject.bookReader.model.Book;
import com.sideproject.bookReader.model.dto.book.CreateBookDto;
import com.sideproject.bookReader.repository.BookRepository;
import javax.swing.text.html.parser.Entity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book saveBook(CreateBookDto createBookDto){
        return bookRepository.findById(createBookDto.getIsbn()).orElseGet(() -> {
            Book newBook = Book.builder()
                .title(createBookDto.getTitle())
                .author(createBookDto.getAuthor())
                .isbn(createBookDto.getIsbn())
                .imageUrl(createBookDto.getImageUrl())
                .build();
            return bookRepository.save(newBook); // 저장된 객체 반환
        });
    }

    public boolean existBook(String isbn){
        return bookRepository.findById(isbn).isPresent();
    }

    public Book retrieveBookForService(String isbn){
        return bookRepository.findById(isbn)
            .orElseThrow(() -> new RuntimeException());
    }
}
