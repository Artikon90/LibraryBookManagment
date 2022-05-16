package all.controller;

import all.dao.BookDAO;
import all.models.Book;
import all.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookDAO bookDAO;

    @Autowired
    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String viewAllBook(Model model) {
        model.addAttribute("allBook", bookDAO.getAllBook());
        return "book/allBookPage";
    }
    @GetMapping("/{id}")
    public String viewBookById(@PathVariable("id") int id, Model model) {
        model.addAttribute("bookById", bookDAO.getBookById(id));
        return "book/bookPage";
    }
    @GetMapping("/add")
    public String addNewBookPage(@ModelAttribute("bookToAdd") Book book) {
        return "book/newBookPage";
    }

    @PostMapping()
    public String addNewBook(@ModelAttribute("bookToAdd") @Valid Book book,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "book/newBookPage";
        bookDAO.addNewBook(book);
        return "redirect:/book";
    }
    @GetMapping("/{id}/edit")
    public String editBookPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("bookToEdit", bookDAO.getBookById(id));
        return "book/editBookPage";
    }
    @PatchMapping("/{id}")
    public String editBook(@Valid @ModelAttribute("bookToEdit") Book book, BindingResult bindingResult,
                           @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "person/editUserPage";

        bookDAO.editBook(id, book);
        return "redirect:/book";
    }





    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/book";
    }
}
