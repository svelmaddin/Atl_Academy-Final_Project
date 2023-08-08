package com.FinalProject.mapper;

import com.FinalProject.dto.BookDto;
import com.FinalProject.model.Book;
import com.FinalProject.request.BookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookMapper {


    public List<BookDto> entityListToDtoList(List<Book> books) {
        return books.stream().map(book -> new BookDto

                (
                        book.getId(),
                        book.getName(),
                        book.getIsbn(),
                        book.getStock(),
                        book.getAuthor().getFullName(),
                        book.getCategory().getName(),
                        book.getImage())).toList();
    }

    public BookDto entityToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .stock(book.getStock())
                .authorName(book.getAuthor().getFullName())
                .category(book.getCategory().getName())
                .isbn(book.getIsbn())
                .name(book.getName())
                .image(book.getImage())
                .build();
    }

    public Book requestToEntity(BookRequest bookRequest) {
        return Book.builder()
                .isbn(bookRequest.getIsbn())
                .name(bookRequest.getName())
                .stock(bookRequest.getStock())
                .image(bookRequest.getFile().getOriginalFilename())
                .build();
    }

}