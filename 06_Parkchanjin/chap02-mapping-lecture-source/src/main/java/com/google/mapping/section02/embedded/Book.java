package com.google.mapping.section02.embedded;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity // 이름 지정 안하면 클래스의 소문자로 지정이 됨
@Table(name = "tbl_bookbook")
public class Book {

    @Id
    @Column(name = "book_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookNo;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "author")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Embedded // @Embeddable이 작성된 클래스를 해당 필드에 내장
    private Price price;

    protected Book() {}

    public Book(
	    String bookTitle, String author, String publisher, 
	    LocalDate publishedDate, Price price
	  ) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookNo=" + bookNo +
                ", bookTitle='" + bookTitle + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publishedDate=" + publishedDate +
                ", price=" + price +
                '}';
    }
}