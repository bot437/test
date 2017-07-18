package ua.dp.iti.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ua.dp.iti.data.Contact;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

@Repository
public class ContactDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createRandom() {
        jdbcTemplate.update("INSERT INTO contacts (name) VALUES (?)", Contact.randomString());
    }

    public List<Contact> list(Pattern pattern) {
        List<Contact> contacts = new LinkedList<>();
        jdbcTemplate.setFetchSize(100);
        jdbcTemplate.query("SELECT id,name FROM contacts", resultSet -> {
            if (!pattern.matcher(resultSet.getString(2)).matches()) {
                contacts.add(new Contact(resultSet.getLong(1), resultSet.getString(2)));
            }
        });
        return contacts;
    }

    public List<Contact> list(Pattern pattern, int skip, int max) {
        int count = 0;
        List<Contact> contacts = new LinkedList<>();
        jdbcTemplate.setFetchSize(100);
        SqlRowSet rows = jdbcTemplate.queryForRowSet("SELECT id,name FROM contacts");
        while ( skip > 0 && rows.next()) {
            if (!pattern.matcher(rows.getString(2)).matches())
                skip--;
        }
        while (rows.next()) {
            if(!pattern.matcher(rows.getString(2)).matches())
                contacts.add(new Contact(rows.getLong(1), rows.getString(2)));
            count++;
            if (count == max) break;
        }
        return contacts;
    }

}
