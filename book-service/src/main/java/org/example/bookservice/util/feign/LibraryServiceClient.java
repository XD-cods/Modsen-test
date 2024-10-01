package org.example.bookservice.util.feign;

import org.example.bookservice.web.request.BookRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "library-service")
public interface LibraryServiceClient {
  @PutMapping("/api/record")
  void addBookToLibraryRecord(@RequestBody BookRequest bookRequest, @RequestParam Long bookId);
}
