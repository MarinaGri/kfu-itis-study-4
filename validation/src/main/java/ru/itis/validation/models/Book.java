package ru.itis.validation.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.validation.annotations.CollectionSizeChecker;

import java.util.List;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@CollectionSizeChecker(fieldsNames = "publishingHouses")
public class Book {

  @NotNull
  @Size(min = 5, max = 128)
  private String name;
  @Size(max = 1024)
  private String description;

  private List<String> publishingHouses;

}
