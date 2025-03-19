package da4a.migrations.controller;

import da4a.migrations.service.PetService;
import da4a.migrations.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public Pet createPet(@RequestBody Pet pet) {
        return petService.createPet(pet);
    }

    @GetMapping("/{petId}")
    public Pet getPet(@PathVariable Long petId) {
        return petService.getPet(petId);
    }

    @GetMapping
    public List<Pet> getPets() {
        return petService.getPets();
    }

    @PutMapping
    public Pet updatePet(@RequestBody Pet pet) {
        return petService.updatePet(pet);
    }

    @DeleteMapping("/{petId}")
    public void deletePet(@PathVariable Long petId) {
        petService.deletePet(petId);
    }
}
