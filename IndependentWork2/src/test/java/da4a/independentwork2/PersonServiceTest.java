package da4a.independentwork2;

import da4a.independentwork2.Model.Person;
import da4a.independentwork2.Service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @InjectMocks
    PersonService personService;

    private List<Person> personsTest;

    @BeforeEach
    public void setup() {
        personsTest = Arrays.asList(
                new Person(1L, "Name 1", 25),
                new Person(2L, "Name 2", 30),
                new Person(3L, "Name 3", 35)
        );
    }

    @Test
    public void getAllPersonsTest() throws Exception {
        List<Person> result = personService.getPersons();

        assertNotNull(result);
        assertEquals(personsTest.size(), result.size());
        assertEquals(personsTest.getFirst().getName(), result.getFirst().getName());
        assertEquals(personsTest.getFirst().getAge(), result.getFirst().getAge());
        assertEquals(personsTest.getFirst().getId(), result.getFirst().getId());
        assertEquals(personsTest.get(1).getName(), result.get(1).getName());
        assertEquals(personsTest.get(2).getName(), result.get(2).getName());
    }
}
