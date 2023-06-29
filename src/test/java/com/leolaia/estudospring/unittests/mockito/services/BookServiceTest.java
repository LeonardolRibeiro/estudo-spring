package com.leolaia.estudospring.unittests.mockito.services;

import com.leolaia.estudospring.data.vo.v1.BookVO;
import com.leolaia.estudospring.exceptions.RequiredObjectIsNullException;
import com.leolaia.estudospring.models.Book;
import com.leolaia.estudospring.repositories.BookRepository;
import com.leolaia.estudospring.services.BookService;
import com.leolaia.estudospring.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    MockBook input;

    @InjectMocks
    private BookService service;

    @Mock
    BookRepository repository;
    @BeforeEach
    void setUpMocks() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {

        List<Book> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);

        List<BookVO> books = service.findAll();

        assertNotNull(books);
        assertEquals(14, books.size());

        BookVO bookOne = books.get(1);
        assertNotNull(bookOne);
        assertNotNull(bookOne.getKey());
        assertNotNull(bookOne.getLinks());
        assertNotNull(bookOne.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Some Author1", bookOne.getAuthor());
        assertEquals("Some Title1", bookOne.getTitle());
        assertEquals(25D, bookOne.getPrice());
        assertNotNull(bookOne.getLaunchDate());

        BookVO bookFour = books.get(4);
        assertNotNull(bookFour);
        assertNotNull(bookFour.getKey());
        assertNotNull(bookFour.getLinks());
        assertNotNull(bookFour.toString().contains("links: [</api/book/v1/4>;rel=\"self\"]"));
        assertEquals("Some Author4", bookFour.getAuthor());
        assertEquals("Some Title4", bookFour.getTitle());
        assertEquals(25D, bookFour.getPrice());
        assertNotNull(bookFour.getLaunchDate());

        BookVO bookSeven = books.get(7);
        assertNotNull(bookSeven);
        assertNotNull(bookSeven.getKey());
        assertNotNull(bookSeven.getLinks());
        assertNotNull(bookSeven.toString().contains("links: [</api/book/v1/7>;rel=\"self\"]"));
        assertEquals("Some Author7", bookSeven.getAuthor());
        assertEquals("Some Title7", bookSeven.getTitle());
        assertEquals(25D, bookSeven.getPrice());
        assertNotNull(bookSeven.getLaunchDate());
    }

    @Test
    void findById() {
        Book book = input.mockEntity(1);
        book.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(book));

        BookVO result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertNotNull(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Some Author1", result.getAuthor());
        assertEquals("Some Title1", result.getTitle());
        assertEquals(25D, result.getPrice());
        assertNotNull(result.getLaunchDate());

    }

    @Test
    void create() {
        Book book = input.mockEntity(1);

        Book savedBook = book;
        savedBook.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(book)).thenReturn(savedBook);

        BookVO result = service.create(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertNotNull(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Some Author1", result.getAuthor());
        assertEquals("Some Title1", result.getTitle());
        assertEquals(25D, result.getPrice());
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void createWithNullBook() {

        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
         service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void update() {
        Book book = input.mockEntity(1);
        book.setId(1L);

        Book savedBook = book;
        savedBook.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(repository.save(book)).thenReturn(savedBook);

        BookVO result = service.update(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertNotNull(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Some Author1", result.getAuthor());
        assertEquals("Some Title1", result.getTitle());
        assertEquals(25D, result.getPrice());
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void updateWithNullBook() {

        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void delete() {
        Book book = input.mockEntity(1);
        book.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(book));

        try {
            service.delete(1L);
        } catch (Exception e) {
            fail();
        }
    }
}