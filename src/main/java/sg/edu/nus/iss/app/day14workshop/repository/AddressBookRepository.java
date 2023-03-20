package sg.edu.nus.iss.app.day14workshop.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.app.day14workshop.model.Contact;

@Repository
public class AddressBookRepository {
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private static final String CONTACT_LIST = "contactList";
    
    // save method stores contacts in two ways - list and map
    public void save(final Contact ctc){
        // stores data in a list - "contactList : ctcId"
        redisTemplate.opsForList()
                .leftPush(CONTACT_LIST, ctc.getId());
        // stores data in a map - "contactList {contactId : Contact object}"
        redisTemplate.opsForHash()
                .put(CONTACT_LIST + "_Map", ctc.getId(), ctc);
    }

    // method to get the contact object from the map using contact ID as key
    public Contact findById(final String contactId){
        Contact result = (Contact) redisTemplate.opsForHash()
                .get(CONTACT_LIST + "_Map", contactId);
        return result;
    }

    public List<Contact> findAll(int startIndex){
        // add to list contact IDs from start to index 9
        List<Object> fromContactList = redisTemplate.opsForList()
                .range("contactList", startIndex, 10);

        // add to list Contact objs from map, using the list of IDs as keys
        List<Contact> ctcs = redisTemplate.opsForHash()
                .multiGet("contactList" + "_Map", fromContactList)
                .stream()
                .filter(Contact.class::isInstance) 
                //remove objects that are not instances of Contact class
                .map(Contact.class::cast)
                //maps the remaining objects to Contact class
                .toList();
        
        return ctcs;
    }
}
