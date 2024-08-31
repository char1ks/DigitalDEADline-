package controller;

import model.Book;
import model.Person;
import model.returnBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.bookService;
import service.personService;

import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class bookController {
    private bookService operations;
    private personService operationsToPerson;
    @Autowired
    public void setOperations(bookService operations) {
        this.operations = operations;
    }
    @Autowired
    public void setOperationsToPerson(personService operationsToPerson) {
        this.operationsToPerson = operationsToPerson;
    }

    //ELEMENTS
    @GetMapping
    public String mainPage(Model model){
        model.addAttribute("allBooks",operations.getAllBooks());
        return "books/mainPage";
    }
    @GetMapping("/find")
    public String findPage(@RequestParam(value = "nomination") String nomination,
                           Model model) {
        model.addAttribute("allBooks", operations.foundBooks(nomination));
        return "books/mainPage";
    }
    @GetMapping("/{pageNum}/{booksPerPage}")
    public String paginationPage(@PathVariable("pageNum")int pageNum,
                           @PathVariable("booksPerPage")int booksPerPage,Model model){
        model.addAttribute("allBooks",operations.paginationBooks(pageNum,booksPerPage));
        return "books/mainPage";
    }
    @GetMapping("/clearDeadLines")
    public String deadLinPage(Model model){
        model.addAttribute("allBooks",operations.deadLineSort("timeToBack",Sort.Direction.DESC));
        return "books/deadLines";
    }
    @GetMapping("/backToDef")
    public String defaultSettings() {
        operations.deadLineSort("id", Sort.Direction.ASC); // Сортируем по id от меньшего к большему
        return "redirect:/book"; // Перенаправляем на страницу со списком книг
    }
    @GetMapping("/{id}")
    public String concretPage(@PathVariable("id")int id,
                              Model model){
        model.addAttribute("concretBook",operations.getConcreteBook(id));
        return "books/concretPage";
    }
    //ADD
    @GetMapping("/new")
    public String newPage(@ModelAttribute("newBook")Book book){
        return "books/newPage";
    }
    @PostMapping
    public String newInDB(@ModelAttribute("newBook") @Valid Book book,
                          BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "books/newPage";
        operations.saveBook(book);
        return "redirect:/book";
    }
    //EDIT
    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id")int id,Model model){
        model.addAttribute("editBook",operations.getConcreteBook(id));
        return "books/editPage";
    }
    @PatchMapping("/{id}")
    public String editInDB(@ModelAttribute("editBook") @Valid Book book,
                           BindingResult bindingResult
                            ,@PathVariable("id")int id){
        if(bindingResult.hasErrors())
            return "books/editPage";
        operations.updateBook(id,book);
        return "redirect:/book";
    }
    //DELETE
    @DeleteMapping("/{id}")
    public String deleteInDB(@PathVariable("id") int id){
        operations.deleteBook(id);
        return "redirect:/book";
    }
    //MORE FUNCTIONS
    @GetMapping("/{personId}/chooseBook")
    public String choosePage(@PathVariable("personId")int personId,Model model){
        model.addAttribute("personId",personId);
        model.addAttribute("allBooksWithoutOwner",operations.getAllBooksWherePersonNULL());
        return "books/choosePage";
    }
    @PatchMapping("/{personId}/{bookId}/chooseBook")
    public String choiceAgree(@PathVariable("personId")int personId,
                              @PathVariable("bookId")int bookId){
        Person person=operationsToPerson.getConcretePerson(personId);
        person.setReturnBook(returnBook.YES);
        Book book=operations.getConcreteBook(bookId);
        book.setPerson(person);
        operations.updateBook(bookId,book);
        operationsToPerson.updatePerson(personId,person);
        return "redirect:/person";
    }
}
