package da4a.independentwork2;

import da4a.independentwork2.Controller.PersonController;
import da4a.independentwork2.Model.Person;
import da4a.independentwork2.Service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTests {
    @Mock
    PersonService personService;

    @InjectMocks
    PersonController personController;

    private MockMvc mockMvc;
    private List<Person> personsTest;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
        personsTest = Arrays.asList(
                new Person(1L, "Name 1", 25),
                new Person(2L, "Name 2", 30)
        );
    }

    @Test
    public void getAllPersonsTest() throws Exception {
        Mockito.when(personService.getPersons()).thenReturn(personsTest);

        mockMvc.perform(get("/persons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Name 1"))
                .andExpect(jsonPath("$[0].age").value(25))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Name 2"))
                .andExpect(jsonPath("$[1].age").value(30));

        Mockito.verify(personService, Mockito.times(1)).getPersons();
    }

    @Test
    public void getStatusCodeTest() throws Exception {
        mockMvc.perform(post("/persons")).andExpect(status().isOk());
    }
}
