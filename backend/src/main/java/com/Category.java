package com;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    public Long id;
    @NotNull
    @NotNull
    public String name;
    public String description;
    public Long order = 0L;
    public List<Post> posts = new ArrayList<>();
    public Category( @NotNull String name, String description) {
        this.name = name;
        this.description = description;
    }
}

