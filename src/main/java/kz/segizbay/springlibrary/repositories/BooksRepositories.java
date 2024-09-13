package kz.segizbay.springlibrary.repositories;

import kz.segizbay.springlibrary.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepositories extends JpaRepository<Book, Integer> {
    public List<Book> findBookByTitleStartingWith(String startWith);

}
