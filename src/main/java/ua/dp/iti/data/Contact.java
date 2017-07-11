package ua.dp.iti.data;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by bot on 10.07.17.
 */
public class Contact {
    Long id;
    String name;

    public Contact(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    static private SecureRandom random = new SecureRandom();
    static public String randomString() {
        return new BigInteger(130, random).toString(32);
    }
}
