package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class CategoryDao {
    @Autowired private PostDao postDao;
    @Autowired private JdbcTemplate jdbcTemplate;
    public int populate(List<Category> categories){
        jdbcTemplate.update("DELETE FROM posts" );
        jdbcTemplate.update("DELETE FROM categories" );
        categories.forEach(category -> {
            Long id = insert(category);
            category.posts.forEach(post->{
                var post_id = postDao.insertPost(post);
                postDao.modifyCat(post_id, id);
            });
        });
        return 0;
    }
    public Long insert(Category cat) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            var prepateStatement = connection.prepareStatement("INSERT INTO categories (name, description, sort_order) VALUES (?,?,?)", new String[]{"id"});
            prepateStatement.setString(1, cat.getName());
            prepateStatement.setString(2, cat.getDescription());
            prepateStatement.setLong(3, cat.getOrder() == null ? 0L : cat.getOrder());
            return prepateStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<Category> get() {
        var posts = jdbcTemplate
                .query("SELECT cat.id,cat.name,cat.description,cat.sort_order," +
                        "posts.id, posts.url, posts.name, posts.description, posts.sort_order FROM categories as cat " +
                        "LEFT JOIN posts ON cat.id = posts.cat_id " +
                        "ORDER BY cat.sort_order, posts.sort_order", rse -> {
                    Map<Long, Category> categories = new HashMap<>();
                    while (rse.next()) {
                        var id = rse.getLong(1);
                        Post post = new Post();
                        post.setId(rse.getLong(5));
                        post.setUrl(rse.getString(6));
                        post.setName(rse.getString(7));
                        post.setDescription(rse.getString(8));
                        post.setOrder(rse.getLong(9));
                        Category cat = null;
                        if (categories.containsKey(id)) {
                            cat = categories.get(id);
                        } else {
                            cat = new Category();
                            cat.setId(id);
                            cat.setName(rse.getString(2));
                            cat.setDescription(rse.getString(3));
                            cat.setOrder(rse.getLong(4));
                            categories.put(id, cat);
                        }
                        cat.posts.add(post);
                    }
                    return List.copyOf(categories.values());
                });
        return posts;
    }

    public static Category mapper(ResultSet rs) throws SQLException {
        Category cat = new Category();
        Post post = new Post();
        cat.setId(rs.getLong(1));
        cat.setName(rs.getString(2));
        cat.setDescription(rs.getString(3));
        cat.setOrder(rs.getLong(4));
        post.setId(rs.getLong(5));
        post.setUrl(rs.getString(6));
        post.setName(rs.getString(7));
        post.setDescription(rs.getString(8));
        post.setOrder(rs.getLong(9));

        return cat;
    }

    public Category get(Long id) {
        var category = jdbcTemplate
                .query("SELECT id,name,description,sort_order FROM categories WHERE id = ? LIMIT 1", new Object[]{id}, rs -> {
                    rs.next();
                    return mapper(rs);
                });
        return category;
    }

    public Long count() {
        Long count = jdbcTemplate.queryForObject("SELECT count(*) FROM categories", Long.class);
        return count;
    }

    public boolean modify(Long id, Category cat) {
        var rowAffected = jdbcTemplate.update("UPDATE categories SET " +
                "name = ?,description = ?, sort_order = ? " +
                "where categories.id = ?", cat.getName(), cat.getDescription(), cat.getOrder(), id);
        return rowAffected > 0;
    }

    public boolean delete(Long id) {
        var rowAffected = jdbcTemplate.update("DELETE FROM categories where posts.id = ?", id);
        return rowAffected > 0;
    }
}
