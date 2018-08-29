package com;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired private PostDao postDao;
    @Autowired private CategoryDao categoryDao;

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
            categoryDao.populate(categories);
            return getCat();
        } catch (DuplicateKeyException e) {
            return "url has already existed";
        }
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
