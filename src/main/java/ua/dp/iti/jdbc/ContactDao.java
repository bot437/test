package ua.dp.iti.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ua.dp.iti.data.Contact;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by bot on 10.07.17.
 */
@Repository
public class ContactDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createRandom() {
        jdbcTemplate.update("INSERT INTO contacts (name) VALUES (?)", Contact.randomString());
    }

    public List<Contact> list(String regex) {
        List<Contact> contacts = new LinkedList<>();
        jdbcTemplate.query("SELECT id,name FROM contacts", resultSet -> {
            if (resultSet.getString(2).matches(regex)) {
                contacts.add(new Contact(resultSet.getLong(1), resultSet.getString(2)));
            }
        });
        return contacts;
    }

    public List<Contact> list(String regex, int skip, int max) {
        int count = 0;
        List<Contact> contacts = new LinkedList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet("SELECT id,name FROM contacts");
        while (rows.next() && skip > 0) {
            if (rows.getString(2).matches(regex))
                if (skip > 0) skip--;
        }
        while (rows.next()) {
            contacts.add(new Contact(rows.getLong(1), rows.getString(2)));
            count++;
            if (count == max) return contacts;
        }
        return contacts;
    }

}
