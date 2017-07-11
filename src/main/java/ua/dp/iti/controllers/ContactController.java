package ua.dp.iti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.dp.iti.data.Contact;
import ua.dp.iti.data.ErrorMessage;
import ua.dp.iti.jdbc.ContactDao;

import java.util.List;

/**
 * Created by bot on 10.07.17.
 */

@RestController
@RequestMapping("/hello")
public class ContactController {

    @Autowired
    ContactDao dao;

    @RequestMapping("/contacts")
    public List<Contact> list(
            @RequestParam("nameFilter") String regex,
            @RequestParam(value = "skip", defaultValue = "0") Integer skip,
            @RequestParam(value = "max", required = false) Integer max
    ) {
        if(max!=null) {
            return dao.list(regex,skip,max);
        }
        return dao.list(regex);
    }

    public static class Message {
        String text;

        public Message(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    @RequestMapping("/new")
    public Message create(
            @RequestParam(value = "count", defaultValue = "1") Integer count
    ) {
        try {
            for (int i = 0; i < count; i++) dao.createRandom();
            return new Message("Ok, add "+count);
        } catch (Exception e) {
            return new Message("Error: "+e.getMessage());
        }
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorMessage> noParameter(MissingServletRequestParameterException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CannotGetJdbcConnectionException.class)
    public ResponseEntity<ErrorMessage> databaseDown(CannotGetJdbcConnectionException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
