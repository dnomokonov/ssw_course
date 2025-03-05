package da4a.restservice.model;

import java.util.List;

public class Pet {
    private long id;
    private String name;
    private String status;
    private Category category;
    private List<Tags> tags;

    public Pet(long id, String name, String status, Category category, List<Tags> tags) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.category = category;
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTag(List<Tags> tags) {
        this.tags = tags;
    }
}
