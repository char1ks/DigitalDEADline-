package controller;

import model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.personService;

import javax.validation.Valid;

@Controller
@RequestMapping("/person")
public class personController {
    private personService operations;

    @Autowired
    public void setOperations(personService operations) {
        this.operations = operations;
    }
    //ELEMENTS
    @GetMapping
    public String mainPage(Model model){
        model.addAttribute("allPeople",operations.getAllPeople());
        return "person/mainPage";
    }
    @GetMapping("/{id}")
    public String concretePage(@PathVariable("id")int id,
                               Model model){
        model.addAttribute("concretPerson",operations.getConcretePerson(id));
        return "person/concretPage";
    }
    //ADD
    @GetMapping("/new")
    public String newPage(@ModelAttribute("newPerson")Person person){
        return "person/newPage";
    }
    @PostMapping
    public String addInDB(@ModelAttribute("newPerson") @Valid Person person,
                          BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "person/newPage";
        operations.savePerson(person);
        return "redirect:/book/"+person.getId()+"/chooseBook";
    }
    //EDIT
    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id")int id,
                           Model model){
        model.addAttribute("concretPerson",operations.getConcretePerson(id));
        return "person/editPage";
    }
    @PatchMapping("/{id}")
    public String editInDB(@ModelAttribute("concretPerson") @Valid Person person,
                           BindingResult bindingResult,@PathVariable("id")int id){
        if(bindingResult.hasErrors())
            return "person/editPage";
        operations.updatePerson(id,person);
        return "redirect:/person";
    }
    //DELETE
    @DeleteMapping("/{id}")
    public String deleteInDB(@PathVariable("id")int id){
        operations.deletePerson(id);
        return "redirect:/person";
    }
}
