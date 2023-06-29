package com.leolaia.estudospring.repositories;

import com.leolaia.estudospring.models.Book;
import com.leolaia.estudospring.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
