package org.example.bookservice.util.mapper;

import lombok.RequiredArgsConstructor;
import org.example.bookservice.persistence.entity.Book;
import org.example.bookservice.web.request.BookRequest;
import org.example.bookservice.web.response.BookResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {
  private final ModelMapper modelMapper;

  public BookResponse toResponse(final Book book) {
    return modelMapper.map(book, BookResponse.class);
  }

  public BookRequest toRequest(final Book book) {
    return modelMapper.map(book, BookRequest.class);
  }

  public Book requestToEntity(final BookRequest bookRequest) {
    return modelMapper.map(bookRequest, Book.class);
  }

  public Book responseToEntity(final BookResponse bookResponse) {
    return modelMapper.map(bookResponse, Book.class);
  }
}
