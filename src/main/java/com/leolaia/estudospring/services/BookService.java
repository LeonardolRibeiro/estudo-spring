package com.leolaia.estudospring.services;

import com.leolaia.estudospring.controllers.BookController;
import com.leolaia.estudospring.data.vo.v1.BookVO;
import com.leolaia.estudospring.exceptions.RequiredObjectIsNullException;
import com.leolaia.estudospring.exceptions.ResourceNotFoundException;
import com.leolaia.estudospring.mappers.DozerMapper;
import com.leolaia.estudospring.models.Book;
import com.leolaia.estudospring.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {
    private Logger logger = Logger.getLogger(BookService.class.getName());
    @Autowired
    BookRepository repository;
    public List<BookVO> findAll() {
        logger.info("Finding all book!");
        List<BookVO> bookVOS = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
        bookVOS
                .stream().
                forEach(p -> {
                    try {
                        p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        return bookVOS;
    }
    public BookVO findById(Long id) {
        logger.info("Finding one book!");
        Book book = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        BookVO vo = DozerMapper.parseObject(book, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;
    }

    public BookVO create(BookVO book) {

        if(book == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one book!");
        Book entity = DozerMapper.parseObject(book, Book.class);
        BookVO vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO book) {
        if(book == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one book!");
        Book entity = repository.findById(book.getKey()).orElseThrow( () -> new ResourceNotFoundException("No records found for this ID"));
        entity.setAuthor(book.getAuthor());
        entity.setLauchDate(book.getLauchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());
        BookVO vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one book!");
        Book entity = repository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(entity);
    }

}
