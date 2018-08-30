package com;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    public Long id;
    @NotNull
    @URL
    public String url;
    @NotNull
    public String name;
    public String description;
    public Long order = 0L;
    public Long catId = null;

    public Post(@NotNull @URL String url, @NotNull String name, String description, Long order, Long cat_id) {
        this.url = url;
        this.name = name;
        this.description = description;
        this.order = order;
        this.catId = cat_id;
    }

    public Post(@NotNull @URL String url, @NotNull String name, String description) {
        this.url = url;
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
