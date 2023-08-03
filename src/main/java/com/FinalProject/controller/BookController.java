package com.FinalProject.controller;

import com.FinalProject.dto.BookDto;
import com.FinalProject.dto.BookRequest;
import com.FinalProject.service.AuthorService;
import com.FinalProject.service.BookService;
import com.FinalProject.service.CategoryService;
import com.FinalProject.service.impl.FileServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final AuthorService authorService;
    private final BookService bookService;
    private final CategoryService categoryService;
    private final FileServiceImpl fileService;

//    @PostMapping("/{isbn}/update")
//    public String update(@PathVariable("isbn") String isbn,
//                         @ModelAttribute("bookRequest") BookRequest bookRequest) {
//        bookService.update(isbn, bookRequest);
//        return "redirect:/book";
//    }

    @GetMapping("/{isbn}/update")
    public String updatePage(@PathVariable("isbn") String isbn, @ModelAttribute("bookRequest") BookRequest bookRequest) {
        return "book-update";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") BookRequest bookRequest, Model model) {
        if (bookRequest == null) {
            return "book-create";
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
        return "book-create";
    }


    @GetMapping
    public String findAll(Model model) {
        List<BookDto> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);
        return "book-list";
    }

    @GetMapping("/{isbn}/remove")
    public String delete(@PathVariable String isbn, Model model) {
        bookService.delete(isbn);
        model.addAttribute("category", bookService.findAll());
        return "redirect:/book";
    }


    @GetMapping("/search")
    public String findByIsbn(@RequestParam String isbn, Model model) {
        List<BookDto> bookList = bookService.findAll();
        List<BookDto> findBooks =
                bookList.stream().
                        filter(bookDto -> bookDto.getIsbn().equals(isbn))
                        .collect(Collectors.toList());
        model.addAttribute("bookList", findBooks);
        return "book-list";
    }

    @GetMapping("/search/category")
    public String findByCategory(@RequestParam String category, Model model) {
        List<BookDto> bookList = bookService.findAll();
        List<BookDto> findBooks =
                bookList.stream().
                        filter(bookDto -> bookDto.getCategory().contains(category))
                        .collect(Collectors.toList());
        model.addAttribute("bookList", findBooks);
        return "book-list";
    }

    @GetMapping("/search/author")
    public String findByAuthor(@RequestParam String author, Model model) {
        List<BookDto> bookList = bookService.findAll();
        List<BookDto> findBooks =
                bookList.stream().
                        filter(bookDto -> bookDto.getAuthorName().contains(author))
                        .collect(Collectors.toList());
        model.addAttribute("bookList", findBooks);
        return "book-list";
    }


}
