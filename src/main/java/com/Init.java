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

    @Autowired JdbcTemplate jdbcTemplate;
    @Autowired PostDao postDao;
    @Autowired CategoryDao categoryDao;

    @Override
    public void run(String... args) throws Exception {
        jdbcTemplate.execute(dropTable);
        jdbcTemplate.execute(dropCat);
        jdbcTemplate.execute(categoryTable);
        jdbcTemplate.execute(postTable);

        Long id = postDao.insertPost(new Post("hehehe", "gsdgsdg", "sdfsdfsd"));
        System.out.println(id);
        Long id2 = postDao.insertPost(new Post("fsafsa", "gsdgsdg", "sdfsdfsd"));
        System.out.println(id2);
        Long id3 = postDao.insertPost(new Post("fsfasafsa", "gsdgsdg", "sdfsdfsd"));
        System.out.println(id3);
        Long cat_id = categoryDao.insert(new Category("Name Cat", "Dessadasda"));
        postDao.modifyCat(id,cat_id);
        postDao.modifyCat(id2,cat_id);
        postDao.modifyCat(id3,cat_id);
        Long id4 = postDao.insertPost(new Post("he fsfa hehe", "gsdgsdg", "sdfsdfsd"));
        System.out.println(id);
        Long id5 = postDao.insertPost(new Post("fsafs  fsa assa", "gsdgsdg", " fas fas as fa"));
        System.out.println(id2);
        Long id6 = postDao.insertPost(new Post("fsfafa fas safsa", "fasfas", "sdfs fas fadfsd"));
        System.out.println(id3);
        Long cat_id2 = categoryDao.insert(new Category("fasfas Cat", "fasfas"));
        System.out.println(cat_id);
        postDao.modifyCat(id4,cat_id2);
        postDao.modifyCat(id5,cat_id2);
        postDao.modifyCat(id6,cat_id2);
        System.out.println(categoryDao.get());
    }
}
