package repository;

import model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface bookRepository  extends JpaRepository<Book,Integer> {
    List<Book> findByPersonIdIsNull();
    List<Book> findByPersonIdIsNullAndNominationContainingIgnoreCase(String nomination);
}
