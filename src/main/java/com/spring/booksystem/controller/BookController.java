package com.spring.booksystem.controller;

import com.spring.booksystem.domain.book.Book;
import com.spring.booksystem.domain.book.BookInsertDTO;
import com.spring.booksystem.domain.book.BookType;
import com.spring.booksystem.domain.book.BookUpdateDTO;
import com.spring.booksystem.service.book.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @ModelAttribute("bookTypes")
    public BookType[] bookTypes() {
        return BookType.values();
    }

    @GetMapping("/register")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "/book/addBookForm";
    }

    @PostMapping("/register")
    public String addBook(@Validated @ModelAttribute("book") BookInsertDTO bookInsertDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/book/addBookForm";
        }

        Book book = new Book();
        book.setTitle(bookInsertDTO.getTitle());
        book.setPrice(bookInsertDTO.getPrice());
        book.setBookType(bookInsertDTO.getBookType());
        bookService.join(book);

        return "redirect:/book/books";
    }

    @GetMapping("/{bookId}/edit")
    public String editBookForm(@PathVariable Long bookId, Model model) {
        model.addAttribute("book", bookService.findBook(bookId));
        return "/book/editBookForm";
    }

    @PostMapping("/{bookId}/edit")
    public String editBook(@Validated @ModelAttribute("book") BookUpdateDTO bookUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/book/editBookForm";
        }

        Book book = new Book();
        book.setId(bookUpdateDTO.getId());
        book.setTitle(bookUpdateDTO.getTitle());
        book.setPrice(bookUpdateDTO.getPrice());
        book.setBookType(bookUpdateDTO.getBookType());
        bookService.editBook(bookUpdateDTO.getId(), book);
        return "redirect:/book/books";
    }

    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("books", bookService.findAllBook());
        return "/book/booksForm";
    }

    @GetMapping("/{bookId}/delete")
    public String deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return "redirect:/book/books";
    }
}
