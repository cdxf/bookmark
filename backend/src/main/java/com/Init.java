package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class Init implements CommandLineRunner {
    final String dropTable = "DROP TABLE IF EXISTS posts";
    final String dropCat = "DROP TABLE IF EXISTS categories";
    final String categoryTable =
            "CREATE TABLE IF NOT EXISTS categories (" +
                    "id serial, " +
                    "name text, " +
                    "description text, " +
                    "sort_order integer, " +
                    "PRIMARY KEY ( id ) " +
                    ");";
    final String postTable =
            "CREATE TABLE IF NOT EXISTS posts (" +
                    "id serial, " +
                    "url text, " +
                    "name text, " +
                    "description text, " +
                    "sort_order integer, " +
                    "cat_id integer REFERENCES categories(id), " +
                    "PRIMARY KEY ( id ) " +
                    ");";

    final private JdbcTemplate jdbcTemplate;
    final private PostDao postDao;
    final private CategoryDao categoryDao;

    @Autowired
    public Init(JdbcTemplate jdbcTemplate, PostDao postDao, CategoryDao categoryDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.postDao = postDao;
        this.categoryDao = categoryDao;
    }

    @Override
    public void run(String... args) throws Exception {
        jdbcTemplate.execute(categoryTable);
        jdbcTemplate.execute(postTable);
        System.out.println(categoryDao.get());
    }
}
