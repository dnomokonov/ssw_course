package da4a.restservice;

import da4a.restservice.controller.PetController;
import da4a.restservice.model.Category;
import da4a.restservice.model.Pet;
import da4a.restservice.model.Tags;
import da4a.restservice.service.PetService;

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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PetControllerTests {
    @Mock
    private PetService petService;

    @InjectMocks
    private PetController petController;

    private MockMvc mockMvc;
    private Pet testPet;
    private Category category;
    private List<Tags> tags;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
        category = new Category(1L, "Dog");
        tags = List.of(new Tags(1L, "friendly"));
        testPet = new Pet(1L, "Buddy", "available", category, tags);
    }

    @Test
    void createPetTest() throws Exception {
        Mockito.when(petService.createPet(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any())).thenReturn(testPet);

        mockMvc.perform(post("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Buddy\",\"status\":\"available\",\"category\":{\"id\":1,\"name\":\"Dog\"},\"tags\":[{\"id\":1,\"name\":\"friendly\"}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"));

        Mockito.verify(petService, Mockito.times(1)).createPet(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any());
    }

    @Test
    void getPetByIdTest() throws Exception {
        Mockito.when(petService.getPetById(1L)).thenReturn(testPet);

        mockMvc.perform(get("/pet/{id}", 1L)).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Buddy"));

        Mockito.verify(petService, Mockito.times(1)).getPetById(1L);
    }

    @Test
    void updatePetTest() throws Exception {
        Mockito.when(petService.updatePet(Mockito.any(Pet.class))).thenReturn(testPet);

        mockMvc.perform(put("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Buddy\",\"status\":\"available\",\"category\":{\"id\":1,\"name\":\"Dog\"},\"tags\":[{\"id\":1,\"name\":\"friendly\"}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"));

        Mockito.verify(petService, Mockito.times(1)).updatePet(Mockito.any(Pet.class));
    }

    @Test
    void deletePetByIdTest() throws Exception {
        mockMvc.perform(delete("/pet/{id}", 1L))
                .andExpect(status().isOk());

        Mockito.verify(petService, Mockito.times(1)).deletePet(1L);
    }
}
