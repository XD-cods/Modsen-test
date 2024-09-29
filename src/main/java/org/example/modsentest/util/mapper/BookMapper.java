package org.example.modsentest.util.mapper;

import lombok.RequiredArgsConstructor;
import org.example.modsentest.persistence.entity.Book;
import org.example.modsentest.web.request.BookRequest;
import org.example.modsentest.web.response.BookResponse;
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
