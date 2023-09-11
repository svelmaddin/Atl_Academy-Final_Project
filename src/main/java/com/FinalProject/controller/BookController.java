package com.FinalProject.controller;

import com.FinalProject.dto.BookDto;
import com.FinalProject.request.BookRequest;
import com.FinalProject.service.AuthorService;
import com.FinalProject.service.BookService;
import com.FinalProject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    @PostMapping("/{isbn}/update")
    public String update(@PathVariable("isbn") String isbn,
                         @ModelAttribute("bookRequest") BookRequest bookRequest) {
        bookService.update(isbn, bookRequest);
        return "redirect:/book";
    }

    @GetMapping("/{isbn}/update")
    public String updatePage(
            @PathVariable("isbn") String isbn,
            @ModelAttribute("bookRequest") BookRequest bookRequest,
            Model model) {
        BookDto book = bookService.findByIsbn(isbn);
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("authors", authorService.getAuthors());
        return "books/book-update";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") BookRequest bookRequest, Model model) {
        if (bookRequest == null) {
            return "books/book-create";
        }
        bookService.create(bookRequest);

        model.addAttribute("book", bookService.findAll());
        return "redirect:/book";
    }

    @GetMapping("/add")
    public String bookForm(Model model) {
        model.addAttribute("bookRequest", new BookRequest());
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("authors", authorService.getAuthors());
        return "books/book-create";
    }

    @GetMapping
    public String findAll(Model model) {
        List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("authors", authorService.getAuthors());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "books/book-list";
    }

    @GetMapping("/{isbn}/remove")
    public String delete(@PathVariable String isbn, Model model) {
        bookService.delete(isbn);
        model.addAttribute("category", bookService.findAll());
        return "redirect:/book";
    }

    @GetMapping("/search")
    public String searchBooks(
            @RequestParam(name = "isbn", required = false) String isbn,
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "authorId", required = false) Long authorId,
            Model model) {

        List<BookDto> books = bookService.searchBooks(isbn, categoryId, authorId);

        model.addAttribute("books", books);
        model.addAttribute("authors", authorService.getAuthors());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "books/book-list";
    }




}
