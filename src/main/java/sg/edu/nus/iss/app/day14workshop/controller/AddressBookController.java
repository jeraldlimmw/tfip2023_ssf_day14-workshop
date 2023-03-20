package sg.edu.nus.iss.app.day14workshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import sg.edu.nus.iss.app.day14workshop.model.Contact;
import sg.edu.nus.iss.app.day14workshop.service.AddressBookService;

@Controller
@RequestMapping(path="/contact")
public class AddressBookController {

    @Autowired
    private AddressBookService adrBkSvc;

    @GetMapping
    public String showAddressBookForm(Model model){
        model.addAttribute("contact", new Contact());
        return "addressbook";
    }

    @PostMapping
    public String saveContact(@Valid Contact contact, BindingResult binding
            , Model model) {
        if (binding.hasErrors()){
            return "addressbook";
        }
        adrBkSvc.save(contact);
        return "showcontact";
    }

    @GetMapping(path="{contactId}")
    public String getContactId(Model model, @PathVariable String contactId){
        Contact ctc = adrBkSvc.findById(contactId);
        model.addAttribute("contact", ctc);
        return "showcontact";
    }

    @GetMapping(path="/list")
    public String getAllContacts(Model model, @RequestParam (defaultValue="0") Integer startIndex) {
        List<Contact> ctcs = adrBkSvc.findAll(startIndex);
        model.addAttribute("contacts", ctcs);
        return "contacts";
    }
}
