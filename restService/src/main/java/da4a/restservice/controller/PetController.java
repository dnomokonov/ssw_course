package da4a.restservice.controller;

import da4a.restservice.model.Pet;
import da4a.restservice.service.PetService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public @ResponseBody Pet createPet(@RequestBody Pet pet) {
        return petService.createPet(pet.getId(), pet.getName(), pet.getStatus(), pet.getCategory(), pet.getTag());
    }

    @PutMapping
    public @ResponseBody Pet updatePet(@RequestBody Pet pet) {
        return petService.updatePet(pet);
    }

    @GetMapping("/{petId}")
    public @ResponseBody Pet getPet(@PathVariable long petId) {
        return petService.getPetById(petId);
    }

    @PostMapping("/{petId}")
    public void updatePetParams(@PathVariable long petId, @RequestBody String name, @RequestBody String status) {
        Pet pet = petService.getPetById(petId);

        if (pet != null) {
            if (name != null) pet.setName(name);
            if (status != null) pet.setStatus(status);

            petService.updatePet(pet);
        }
    }

    @DeleteMapping("/{petId}")
    public void deletePet(@PathVariable long petId) {
        petService.deletePet(petId);
    }
 }
