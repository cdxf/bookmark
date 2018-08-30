package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbService {
    private JdbcTemplate jdbcTemplate;
    private CategoryDao categoryDao;
    private PostDao postDao;

    @Autowired
    public DbService(JdbcTemplate jdbcTemplate, CategoryDao categoryDao, PostDao postDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.categoryDao = categoryDao;
        this.postDao = postDao;
    }

    public int populate(List<Category> categories) {
        removeAll();
        categories.forEach(category -> {
            Long id = categoryDao.insert(category);
            category.posts.forEach(post -> {
                var post_id = postDao.insertPost(post);
                postDao.modifyCat(post_id, id);
            });
        });
        return 0;
    }
    public void removeAll(){
        jdbcTemplate.update("DELETE FROM posts");
        jdbcTemplate.update("DELETE FROM categories");
    }
    public void dropTable() {
        final String dropTable = "DROP TABLE IF EXISTS posts";
        final String dropCat = "DROP TABLE IF EXISTS categories";
        jdbcTemplate.execute(dropTable);
        jdbcTemplate.execute(dropCat);
    }

    public void sampleDatabase() {
        var posts = List.of(new Post("https://github.com/DozerMapper/dozer", "Dozer", "Mapper that copies data from one object to another using annotations and API or XML configuration."),
                new Post("https://github.com/mapstruct/mapstruct", "MapStruct", "Code generator that simplifies mappings between different bean types, based on a convention-over-configuration approach."));
        var cat = new Category(null,"Bean Mapping", "Frameworks that ease bean mapping.", 0L, posts);
        populate(List.of(cat));
    }
}
