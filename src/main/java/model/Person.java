package model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "people")
public class Person {
    @Id
    @Column(name = "personId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fullName")
    private String FullName;

    @Column(name = "birthDate")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @Column(name = "returnBook")
    @Enumerated(EnumType.STRING)
    private returnBook returnBook;

    @OneToOne(mappedBy = "person")
    private Book book;

    public Person() {}

    public Person(String fullName, Date birthDate) {
        FullName = fullName;
        this.birthDate = birthDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public model.returnBook getReturnBook() {
        return returnBook;
    }

    public void setReturnBook(model.returnBook returnBook) {
        this.returnBook = returnBook;
    }

    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    //ANYMORE
    public String getBookName() {
        return book.getNomination();
    }
    public Date getBackBookTo(){
        return book.getTimeToBack();
    }
}
