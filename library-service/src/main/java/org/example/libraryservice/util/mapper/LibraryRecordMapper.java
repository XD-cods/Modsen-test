package org.example.libraryservice.util.mapper;

import lombok.RequiredArgsConstructor;
import org.example.libraryservice.persistence.entity.LibraryRecord;
import org.example.libraryservice.web.request.LibraryRecordRequest;
import org.example.libraryservice.web.response.LibraryRecordResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LibraryRecordMapper {
  private final ModelMapper modelMapper;

  public LibraryRecordResponse toResponse(final LibraryRecord record) {
    return modelMapper.map(record, LibraryRecordResponse.class);
  }

  public LibraryRecordRequest toRequest(final LibraryRecord record) {
    return modelMapper.map(record, LibraryRecordRequest.class);
  }

  public LibraryRecord responseToEntity(final LibraryRecordResponse response) {
    return modelMapper.map(response, LibraryRecord.class);
  }

  public LibraryRecord requestToEntity(final LibraryRecordRequest request) {
    return modelMapper.map(request, LibraryRecord.class);
  }
}
