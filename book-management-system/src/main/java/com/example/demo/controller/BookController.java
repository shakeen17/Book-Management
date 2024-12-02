package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.entity.BookEntity;
import com.example.demo.repository.BookRespository;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    public BookRespository book;

    // Retrieve all books (GET)
    @GetMapping("/get")
    public List<BookEntity> getAllBooks() {
        try {
            return book.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve a book by ID (GET)
    @GetMapping("/get/{id}")
    public BookEntity getBookById(@PathVariable int id) {
        try {
            return book.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id " + id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Create a new book (POST)
    @PostMapping("/post")
    public BookEntity createBook(@RequestBody BookEntity bookEntity) {
        try {
            return book.save(bookEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update an existing book (PUT)
    @PutMapping("/update/{id}")
    public ResponseEntity<BookEntity> updateBook(@PathVariable int id, @RequestBody BookEntity updatedBook) {
        try {
            BookEntity existingBook = book.findById(id)
                    .orElseThrow(() -> new RuntimeException("Book not found with id:" + id));

            // Update fields
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setGenre(updatedBook.getGenre());
            existingBook.setPrice(updatedBook.getPrice());
            existingBook.setLanguage(updatedBook.getLanguage());
            existingBook.setEdition(updatedBook.getEdition());
            existingBook.setBookFormat(updatedBook.getBookFormat());
            existingBook.setDescription(updatedBook.getDescription());
            existingBook.setBookCoverImage(updatedBook.getBookCoverImage());

            BookEntity savedBook = book.save(existingBook);
            return ResponseEntity.ok(savedBook);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Delete a book (DELETE)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        try {
            BookEntity bookToDelete = book.findById(id)
                    .orElseThrow(() -> new RuntimeException("Book not found with id " + id));

            book.delete(bookToDelete);
            return ResponseEntity.ok("Book deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete book");
        }
    }
}
