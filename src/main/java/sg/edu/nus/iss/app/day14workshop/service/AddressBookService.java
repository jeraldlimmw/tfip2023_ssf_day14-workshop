package sg.edu.nus.iss.app.day14workshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.app.day14workshop.model.Contact;
import sg.edu.nus.iss.app.day14workshop.repository.AddressBookRepository;

@Service
public class AddressBookService {
    @Autowired
    private AddressBookRepository adrBkRepo;

    public void save(final Contact ctc){
        adrBkRepo.save(ctc);
    }

    public Contact findById(final String contactId){
        return adrBkRepo.findById(contactId);
    }

    public List<Contact> findAll(int startIndex){
        return adrBkRepo.findAll(startIndex);
    }
    
}
