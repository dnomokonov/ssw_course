package da4a.migrations.service;

import da4a.migrations.model.Pet;
import da4a.migrations.model.Category;
import da4a.migrations.model.Tags;
import da4a.migrations.repository.PetRepository;
import da4a.migrations.repository.CategoryRepository;
import da4a.migrations.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final CategoryRepository categoryRepository;
    private final TagsRepository tagsRepository;

    @Autowired
    public PetService(PetRepository petRepository, CategoryRepository categoryRepository, TagsRepository tagsRepository) {
        this.petRepository = petRepository;
        this.categoryRepository = categoryRepository;
        this.tagsRepository = tagsRepository;
    }

    public Pet getPet(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public Pet createPet(Pet pet) {
        if (pet.getCategory() == null) {
            throw new IllegalArgumentException("Category is required");
        }

        // Проверяем и сохраняем Category
        Category category = pet.getCategory();
        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory.isPresent()) {
            pet.setCategory(existingCategory.get());
        } else if (category.getId() == null) {
            pet.setCategory(categoryRepository.save(category));
        }

        // Обрабатываем Tags
        List<Tags> tags = pet.getTags();
        if (tags != null) {
            List<Tags> updatedTags = new ArrayList<>();
            for (Tags tag : tags) {
                Optional<Tags> existingTag = tagsRepository.findByName(tag.getName());
                if (existingTag.isPresent()) {
                    updatedTags.add(existingTag.get());
                } else if (tag.getId() == null) {
                    updatedTags.add(tagsRepository.save(tag));
                } else {
                    updatedTags.add(tag);
                }
            }
            pet.setTags(updatedTags);
        }

        return petRepository.save(pet);
    }

    public Pet updatePet(Pet pet) {
        if (pet.getCategory() == null) {
            throw new IllegalArgumentException("Category is required");
        }

        Category category = pet.getCategory();
        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory.isPresent()) {
            pet.setCategory(existingCategory.get());
        } else if (category.getId() == null) {
            pet.setCategory(categoryRepository.save(category));
        }

        List<Tags> tags = pet.getTags();
        if (tags != null) {
            List<Tags> updatedTags = new ArrayList<>();
            for (Tags tag : tags) {
                Optional<Tags> existingTag = tagsRepository.findByName(tag.getName());
                if (existingTag.isPresent()) {
                    updatedTags.add(existingTag.get());
                } else if (tag.getId() == null) {
                    updatedTags.add(tagsRepository.save(tag));
                } else {
                    updatedTags.add(tag);
                }
            }
            pet.setTags(updatedTags);
        }

        return petRepository.save(pet);
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }
}