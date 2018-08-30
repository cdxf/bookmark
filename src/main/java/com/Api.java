package com;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@CrossOrigin
public class Api {

    private PostDao postDao;
    private CategoryDao categoryDao;
    private DbService dbService;

    public Api(PostDao postDao, CategoryDao categoryDao, DbService dbService) {
        this.postDao = postDao;
        this.categoryDao = categoryDao;
        this.dbService = dbService;
    }

    @GetMapping("/posts")
    public List<Post> getPosts() {
        return postDao.getPosts();
    }


    @GetMapping
    public List<Category> getCat() {
        return categoryDao.get();
    }

    @GetMapping("/count")
    public Long getCount() {
        return postDao.count();
    }

    @PostMapping("/add")
    public Object add(@Validated Post post, BindingResult result) {
        if (result.hasErrors()) return result.getFieldErrors();
        try {

            Long id = postDao.insertPost(post);
            return postDao.getPost(id);
        } catch (DuplicateKeyException e) {
            return "url has already existed";
        }

    }

    @PostMapping("/populate")
    public Object add(@RequestBody @Validated List<Category> categories, BindingResult result) {
        if (result.hasErrors()) return result.getFieldErrors();
        try {
            dbService.populate(categories);
            return getCat();
        } catch (DuplicateKeyException e) {
            return "url has already existed";
        }
    }

    @GetMapping("sampleDatabase")
    public String sampleDatabase() {
        dbService.sampleDatabase();
        return "Completed";
    }

    @PostMapping("/modify/{id}")
    public boolean modifyPost(@PathVariable("id") Long id, Post post) {
        return postDao.modifyPost(id, post);
    }

    @PostMapping("/delete/{id}")
    public boolean deletePost(@PathVariable("id") Long id) {
        return postDao.deletePost(id);
    }
}
