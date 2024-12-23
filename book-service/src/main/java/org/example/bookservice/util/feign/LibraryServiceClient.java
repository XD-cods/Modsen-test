package org.example.bookservice.util.feign;

import org.example.bookservice.web.request.BookRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "library-service")
public interface LibraryServiceClient {
  @PostMapping("/api/record")
  void addLibraryRecord(@RequestBody BookRequest bookRequest, @RequestParam("book_id") Long bookId);
}
