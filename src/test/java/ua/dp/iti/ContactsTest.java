package ua.dp.iti;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.dp.iti.jdbc.ContactDao;

/**
 * Created by bot on 10.07.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ContactsTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ContactDao dao;

    @Test
    public void getHello() throws Exception {
        dao.createRandom();
        String filter = "^[ab]*$";
        mvc.perform(MockMvcRequestBuilders.get("/hello/contacts")
                .param("nameFilter", filter)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("name")));
    }

    @Test
    public void getHelloError() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hello/contacts")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void getPatternError() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hello/contacts")
                .param("nameFilter", "]**^^^&")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(409));
    }

    @Test
    public void getHelloWithPagination() throws Exception {
        dao.createRandom();
        String filter = "^[ab]*$";
        mvc.perform(MockMvcRequestBuilders.get("/hello/contacts")
                .param("nameFilter", filter)
                .param("max", "5")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("name")));
    }

    @Test
    public void tryNew() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hello/new").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Ok")));
    }
}
