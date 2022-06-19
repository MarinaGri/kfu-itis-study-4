package ru.itis.validation;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itis.validation.annotations.CollectionSizeChecker;
import ru.itis.validation.models.Book;
import ru.itis.validation.validators.CollectionSizeValidator;

import javax.validation.ConstraintValidatorContext;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@DisplayName("CollectionSizeValidator is working when ...")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class CollectionSizeValidatorTest {

    @Mock
    private CollectionSizeChecker checker;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private String[] fieldsNames = {"publishingHouses"};

    private Book book;

    private CollectionSizeValidator validator;

    @BeforeEach
    void setUpBefore() {
        when(checker.fieldsNames()).thenReturn(fieldsNames);
        book = new Book();
        validator = new CollectionSizeValidator();
    }


    @Test
    public void returns_true_on_object_with_valid_data() {
        book.setPublishingHouses(Collections.singletonList("1"));
        validator.initialize(checker);
        boolean result = validator.isValid(book, constraintValidatorContext);
        assertTrue(result);
    }

    @Test
    public void returns_false_on_object_with_invalid_data() {
        book.setPublishingHouses(Collections.emptyList());
        validator.initialize(checker);
        boolean result = validator.isValid(book, constraintValidatorContext);
        assertFalse(result);
    }

}
