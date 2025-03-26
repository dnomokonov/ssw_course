package da4a.migrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import da4a.migrations.controller.PetController;
import da4a.migrations.model.Category;
import da4a.migrations.model.Pet;
import da4a.migrations.model.Status;
import da4a.migrations.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PetControllerTest {
    @Mock
    private PetService petService;

    @InjectMocks
    private PetController petController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }

    @Test
    void createPetTest() throws Exception {
        Pet pet = new Pet(1L, "Pippo", new Category(1L, "Dogs"), List.of(), Status.AVAILABLE);

        when(petService.createPet(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(post("/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pet)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Pippo"));
    }

    @Test
    void updatePetTest() throws Exception {
        Pet pet = new Pet(1L, "Pippo", new Category(1L, "Dogs"), List.of(), Status.AVAILABLE);

        when(petService.updatePet(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(put("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pippo"));
    }

    @Test
    void getPetTest() throws Exception {
        Pet pet = new Pet(1L, "Pippo", new Category(1L, "Dogs"), List.of(), Status.AVAILABLE);

        when(petService.getPet(1L)).thenReturn(pet);

        mockMvc.perform(get("/pet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Pippo"));
    }

    @Test
    void getAllPetsTest() throws Exception {
        List<Pet> pets = List.of(
                new Pet(1L, "Poppy", new Category(1L, "Dogs"), List.of(), Status.AVAILABLE),
                new Pet(2L, "Chery", new Category(2L, "Cats"), List.of(), Status.PENDING)
        );

        when(petService.getPets()).thenReturn(pets);

        mockMvc.perform(get("/pet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Poppy"))
                .andExpect(jsonPath("$[1].name").value("Chery"));
    }
}
