package da4a.restservice.service;

import da4a.restservice.model.*;
import da4a.restservice.repository.PetRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PetService {
    private final PetRepository repository;

    PetService(PetRepository repository) {
        this.repository = repository;
    }

    public List<Pet> getAllPets() {
        return repository.findAll();
    }

    public Pet getPetById(long id) {
        return repository.findById(id);
    }

    public Pet createPet(Long id, String name, String status, Category category, List<Tags> tags) {
        return repository.save(new Pet(id, name, status, category, tags));
    }

    public Pet updatePet(Pet pet) {
        return repository.update(pet);
    }

    public void deletePet(long id) {
        repository.delete(id);
    }
}
