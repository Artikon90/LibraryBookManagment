package all.controller;

import all.dao.PersonDAO;
import all.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDAO personDAO;

    @Autowired
    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping("/add")
    public String addNewUserPage(@ModelAttribute("userToAdd") Person person) {
        return "person/newUserPage";
    }

    @PostMapping()
    public String addNewUser(@ModelAttribute("userToAdd") @Valid Person person,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "person/newUserPage";
        personDAO.addNewUser(person);
        return "redirect:/people";
    }

    @GetMapping()
    public String viewAllPerson(Model model) {
        model.addAttribute("allPerson", personDAO.getAllPeople());
        return "person/allPersonPage";
    }

    @GetMapping("/{id}")
    public String viewUserById(@PathVariable("id") int id, Model model) {
        model.addAttribute("userById", personDAO.getPersonById(id));
        model.addAttribute("userBooks", personDAO.getBookByPerson(id));
        return "person/userPage";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        personDAO.deleteUser(id);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editUserPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("userToEdit", personDAO.getPersonById(id));
        return "person/editUserPage";
    }

    @PatchMapping("/{id}")
    public String editUser(@Valid @ModelAttribute("userToEdit") Person person, BindingResult bindingResult,
                           @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "person/editUserPage";

        personDAO.editUser(id, person);
        return "redirect:/people";
    }
}
