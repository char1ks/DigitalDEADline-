package model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "bookId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "bookNomination")
    private String nomination;

    @Column(name = "timeToBack")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date timeToBack;

    @OneToOne
    @JoinColumn(name = "personId",referencedColumnName = "personId")
    private Person person;

    public Book() {}

    public Book(String nomination, Date timeToBack) {
        this.nomination = nomination;
        this.timeToBack = timeToBack;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomination() {
        return nomination;
    }

    public void setNomination(String nomination) {
        this.nomination = nomination;
    }

    public Date getTimeToBack() {
        return timeToBack;
    }

    public void setTimeToBack(Date timeToBack) {
        this.timeToBack = timeToBack;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
