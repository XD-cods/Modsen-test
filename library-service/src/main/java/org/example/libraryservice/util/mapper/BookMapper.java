package org.example.libraryservice.util.mapper;

import lombok.RequiredArgsConstructor;
import org.example.libraryservice.persistence.entity.Book;
import org.example.libraryservice.web.request.BookRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {
  private final ModelMapper modelMapper;


  public BookRequest toRequest(final Book book) {
    return modelMapper.map(book, BookRequest.class);
  }

  public Book requestToEntity(final BookRequest bookRequest) {
    return modelMapper.map(bookRequest, Book.class);
  }

}
