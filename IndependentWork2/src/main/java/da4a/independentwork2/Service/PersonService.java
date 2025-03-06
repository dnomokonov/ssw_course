package da4a.independentwork2.Service;

import da4a.independentwork2.Model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {
    private List<Person> persons;

    public PersonService() {
        persons = new ArrayList<>();
        persons.add(new Person(1L, "Name 1", 25));
        persons.add(new Person(2L, "Name 2", 30));
        persons.add(new Person(3L, "Name 3", 35));
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public void addPerson(Person person) {
        persons.add(person);
    }
}
