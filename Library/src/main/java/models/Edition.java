package models;

import java.util.ArrayList;
import java.util.List;

public class Edition {
    private int id;
    private Book book;
    private List<Borrowing> borrowings;

    public Edition(Book book) {
        this.book = book;
        borrowings = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<Borrowing> getBorrowings() {
        return borrowings;
    }

    public void setBorrowings(List<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }
}
