package com.server.moabook.page.repository;

import com.server.moabook.book.domain.Book;
import com.server.moabook.page.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {

    Optional<Page> findByBookIdAndPageNumber(Long bookId, Long pageNumber);

    @Query("SELECT COALESCE(MAX(p.pageNumber), 0) FROM Page p WHERE p.book = :book")
    Optional<Long> findLastPageNumberByBook(Book book);
}
