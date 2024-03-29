package com.FinalProject.controller;

import com.FinalProject.dto.AuthorsDto;
import com.FinalProject.dto.BookDto;
import com.FinalProject.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/author/new")
    public String createAuthorForm(Model model) {
        AuthorsDto author = new AuthorsDto();
        model.addAttribute("author", author);
        return "authors/author-create";
    }

    @PostMapping("/author/new")
    public String saveAuthor(@ModelAttribute("author") AuthorsDto author,
                             BindingResult result,
                             @Valid Model model) {
        if (result.hasErrors()) {
            model.addAttribute("author", author);
            return "authors/author-create";
        }
        authorService.createAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping("/authors")
    public String listAuthor(Model model) {

        List<AuthorsDto> authors = authorService.getAuthors();
        model.addAttribute("authors", authors);
        return "authors/author-list";
    }

    @GetMapping("/{authorId}/delete")
    public String deleteAuthor(@PathVariable("authorId") Long authorId) {
        authorService.deleteAuthor(authorId);
        return "redirect:/authors";
    }

    @GetMapping("/author/search")
    public String searchAuthor(@RequestParam(value = "query") String query,
                               Model model) {
        List<AuthorsDto> authors = authorService.searchBook(query);
        model.addAttribute("authors", authors);
        return "authors/author-list";
    }

    @GetMapping("/author/{authorId}/edit")
    public String editAuthorForm(@PathVariable("authorId") Long authorId, Model model) {
        AuthorsDto author = authorService.viewAuthor(authorId);
        model.addAttribute("author", author);
        return "authors/author-update";
    }

    @PostMapping("/author/{authorId}/edit")
    public String updateAuthor(@PathVariable("authorId") Long authorId,
                               @Valid @ModelAttribute("author") AuthorsDto author,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("author", author);
            return "authors/author-update";
        }
        authorService.updateAuthor(authorId, author);
        return "redirect:/authors";
    }

    @GetMapping("/authorBooks/{authorId}")
    public String authorBooks(@PathVariable("authorId") Long authorId, Model model) {
        List<BookDto> book = authorService.showAuthorBooks(authorId);
        model.addAttribute("books", book);
        return "book-list";
    }
}
