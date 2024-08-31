package service;

import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.bookRepository;

import java.util.List;

@Service
@Transactional
public class bookService {
    private bookRepository operations;
    @Autowired
    public void setOperations(bookRepository operations) {
        this.operations = operations;
    }

    public List<Book> getAllBooks(){
        return operations.findAll();
    }
    public List<Book> paginationBooks(int pageNumber,int booksPerPage){
        return operations.findAll(PageRequest.of(pageNumber,booksPerPage)).getContent();
    }
    public List<Book> deadLineSort(String column, Sort.Direction direction) {
        return operations.findAll(Sort.by(direction, column)); // Используем Sort.by с направлением
    }
    public List<Book> foundBooks(String prompt){
        return operations.findByPersonIdIsNullAndNominationContainingIgnoreCase(prompt);
    }
    public Book getConcreteBook(int id){
        return operations.findById(id).orElse(null);
    }
    public void saveBook(Book book){
        operations.save(book);
    }
    public void updateBook(int id,Book book){
        book.setId(id);
        operations.save(book);
    }
    public void deleteBook(int id){
        operations.deleteById(id);
    }
    public List<Book>getAllBooksWherePersonNULL(){
        return operations.findByPersonIdIsNull();
    }
}
