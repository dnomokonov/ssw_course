package da4a.restservice.repository;

import da4a.restservice.model.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PetRepository {
    private final Map<Long, Pet> pets = new HashMap<>();
    private long currentId = 1;

    public List<Pet> findAll() {
        return new ArrayList<>(pets.values());
    }

    public Pet findById(long id) {
        return pets.get(id);
    }

    public Pet save(Pet pet) {
        currentId = pet.getId();
        pet = new Pet(pet.getId(), pet.getName(), pet.getStatus(), pet.getCategory(), pet.getTags());
        pets.put(currentId, pet);

        return pet;
    }

    public Pet update(Pet pet) {
        return pets.put(pet.getId(), pet);
    }

    public Pet delete(long id) {
        return pets.remove(id);
    }
}
