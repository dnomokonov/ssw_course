package da4a.migrations.controller;

import da4a.migrations.model.Tags;
import da4a.migrations.service.TagsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagsController {
    private final TagsService tagsService;

    @GetMapping
    public ResponseEntity<List<Tags>> getTags() {
        return ResponseEntity.ok(tagsService.getTags());
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<Tags> getTag(@PathVariable Long tagId) {
        return ResponseEntity.ok(tagsService.getTag(tagId));
    }

    @PostMapping
    public Tags createTag(@RequestBody Tags tags) {
        return tagsService.createTag(tags);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long tagId) {
        tagsService.deleteTag(tagId);
        return ResponseEntity.noContent().build();
    }
}
