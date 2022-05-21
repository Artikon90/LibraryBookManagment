package all.controller;

import all.dao.BookDAO;
import all.dao.PersonDAO;
import all.models.Book;
import all.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String viewAllBook(Model model) {
        model.addAttribute("allBook", bookDAO.getAllBook());
        return "book/allBookPage";
    }
    @GetMapping("/{id}")
    public String viewBookById(@PathVariable("id") int id, Model model,
                               @ModelAttribute("person") Person person) {
        model.addAttribute("bookById", bookDAO.getBookById(id));
        Optional<Person> bookOwner = bookDAO.getBookOwner(id);
        if (bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        } else {
            model.addAttribute("people", personDAO.getAllPeople());
        }
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
    @PatchMapping("/{id}/free")
    public String freeBook(@PathVariable("id") int id) {
        bookDAO.freeBook(id);
        return "redirect:/book/" + id;
    }
    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id, @ModelAttribute("people") Person person) {
        bookDAO.assignBook(id, person);
        return "redirect:/book/" + id;
    }



    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/book";
    }
}
