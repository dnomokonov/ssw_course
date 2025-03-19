package da4a.migrations.service;

import da4a.migrations.model.Tags;
import da4a.migrations.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsService {
    private final TagsRepository tagsRepository;

    @Autowired
    public TagsService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public List<Tags> getTags() {
        return tagsRepository.findAll();
    }

    public Tags getTag(Long id) {
        return tagsRepository.findById(id).orElse(null);
    }

    public Tags createTag(Tags tag) {
        return tagsRepository.save(tag);
    }

    public void deleteTag(Long id) {
        tagsRepository.deleteById(id);
    }
}
