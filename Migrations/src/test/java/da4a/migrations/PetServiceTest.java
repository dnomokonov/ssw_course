package da4a.migrations;

import da4a.migrations.model.*;
import da4a.migrations.repository.CategoryRepository;
import da4a.migrations.repository.PetRepository;
import da4a.migrations.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {
    private Pet testPet;
    private Pet testPet2;

    @Mock
    private PetRepository petRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private da4a.migrations.repository.TagsRepository tagsRepository;

    @InjectMocks
    private PetService petService;

    @BeforeEach
    void setUp() {
        testPet = new Pet(1L, "Poppy", new Category(1L, "Dogs"), List.of(), Status.AVAILABLE);
        testPet2 = new Pet(2L, "Mittens", new Category(2L, "Cats"), List.of(), Status.PENDING);
    }

    @Test
    void createPetTest() throws Exception {
        when(categoryRepository.findByName("Dogs")).thenReturn(Optional.of(new Category(1L, "Dogs")));
        when(petRepository.save(any(Pet.class))).thenReturn(testPet);

        Pet createdPet = petService.createPet(testPet);
        assertNotNull(createdPet);
        assertEquals("Poppy", createdPet.getName());
        verify(petRepository).save(testPet);
    }

    @Test
    void testGetAllPets() {
        List<Pet> expectedPets = List.of(testPet, testPet2);
        when(petRepository.findAll()).thenReturn(expectedPets);

        List<Pet> actualPets = petService.getPets();

        assertNotNull(actualPets, "The returned list should not be null");
        assertEquals(2, actualPets.size(), "The list should contain 2 pets");
        assertEquals(expectedPets, actualPets, "The returned list should match the expected pets");
        assertEquals("Poppy", actualPets.get(0).getName(), "The first pet's name should be Poppy");
        assertEquals("Mittens", actualPets.get(1).getName(), "The second pet's name should be Mittens");

        verify(petRepository, times(1)).findAll();
    }

}
