package hello.webservice;

import hello.model.Contact;
import hello.processor.ContactBetweenSelecter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Assares on 15.12.2015.
 */
@RestController
public class ContactController{
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(
            value = "hello/contacts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<Contact>> hello(@RequestParam(value="nameFilter") String name) {
        //get result list from DB
        ContactBetweenSelecter cs = new ContactBetweenSelecter(name, 10);
        List<Contact> result = new LinkedList<>();
        while(cs.available()){
            result.addAll(cs.getFilteredList());
        }

        if(result == null){
            return new ResponseEntity<Collection<Contact>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Contact>>(result,HttpStatus.NOT_FOUND);
    }
}
