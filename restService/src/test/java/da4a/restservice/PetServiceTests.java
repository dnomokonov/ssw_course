package da4a.restservice;

import da4a.restservice.model.Category;
import da4a.restservice.model.Pet;
import da4a.restservice.model.Tags;
import da4a.restservice.repository.PetRepository;
import da4a.restservice.service.PetService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PetServiceTests {
    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    private Pet testPet;
    private Category category;
    private List<Tags> tags;

    @BeforeEach
    void setup() {
        category = new Category(1L, "Dog");
        tags = List.of(new Tags(1L, "friendly"));
        testPet = new Pet(1L, "Buddy", "available", category, tags);
    }

    @Test
    void findAllPetsTest() {
        List<Pet> pets = List.of(testPet);
        Mockito.when(petRepository.findAll()).thenReturn(pets);

        List<Pet> result = petService.getAllPets();

        assertEquals(1, result.size());
        assertEquals(testPet, result.getFirst());
        Mockito.verify(petRepository, Mockito.times(1)).findAll();
    }

    @Test
    void findPetByIdTest() {
        Mockito.when(petRepository.findById(1L)).thenReturn(testPet);

        Pet result = petService.getPetById(1L);

        assertEquals(testPet, result);
        Mockito.verify(petRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void createPetTest() {
        Mockito.when(petRepository.save(Mockito.any(Pet.class))).thenReturn(testPet);

        Pet result = petService.createPet(1L, "Buddy", "available", category, tags);

        assertNotNull(result);
        assertEquals(testPet, result);
        Mockito.verify(petRepository, Mockito.times(1)).save(Mockito.any(Pet.class));
    }

    @Test
    void updatePetTest() {
        Mockito.when(petRepository.update(testPet)).thenReturn(testPet);

        Pet result = petService.updatePet(testPet);

        assertNotNull(result);
        assertEquals(testPet, result);
        Mockito.verify(petRepository, Mockito.times(1)).update(testPet);
    }

    @Test
    void deletePetTest() {
        Mockito.when(petRepository.delete(1L)).thenReturn(testPet);

        petService.deletePet(1L);

        Mockito.verify(petRepository, Mockito.times(1)).delete(1L);
    }
}
