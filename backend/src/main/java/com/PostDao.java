package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
@Transactional
public class PostDao {

    @Autowired private JdbcTemplate jdbcTemplate;

    public Long insertPost(Post post) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            var prepateStatement = connection.prepareStatement("INSERT INTO posts (url, name, description, sort_order, cat_id) VALUES (?,?,?,?,?)", new String[]{"id"});
            prepateStatement.setString(1, post.getUrl());
            prepateStatement.setString(2, post.getName());
            prepateStatement.setString(3, post.getDescription());
            prepateStatement.setLong(4, post.getOrder() == null ? 0L : post.getOrder());
            if (post.getCatId() == null) {
                prepateStatement.setNull(5, Types.INTEGER);
            } else {
                prepateStatement.setLong(5, post.getCatId());
            }

            return prepateStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<Post> getPosts() {
        List<Post> posts = jdbcTemplate
                .query("SELECT id,url,name,description,sort_order FROM posts ORDER BY sort_order", (rs, rowNum) -> mapper(rs));
        return posts;
    }

    public static Post mapper(ResultSet rs) throws SQLException {
        Post post = new Post();
        post.setId(rs.getLong(1));
        post.setUrl(rs.getString(2));
        post.setName(rs.getString(3));
        post.setDescription(rs.getString(4));
        post.setOrder(rs.getLong(5));
        post.setCatId(rs.getLong(6));
        return post;
    }

    public Post getPost(Long id) {
        Post post = jdbcTemplate
                .query("SELECT id,url,name,description,sort_order FROM posts WHERE id = ? LIMIT 1", new Object[]{id}, rs -> {
                    rs.next();
                    return mapper(rs);
                });
        return post;
    }

    public Long count() {
        Long count = jdbcTemplate.queryForObject("SELECT count(*) FROM posts", Long.class);
        return count;
    }

    public boolean modifyCat(Long id, Long catID) {
        var rowAffected = jdbcTemplate.update("UPDATE posts SET " +
                "cat_id = ? " +
                "where posts.id = ?", catID, id);
        return rowAffected > 0;
    }

    public boolean modifyPost(Long id, Post post) {
        var rowAffected = jdbcTemplate.update("UPDATE posts SET " +
                "url = ?,name = ?,description = ?, sort_order = ?, cat_id = ? " +
                "where posts.id = ?", post.getUrl(), post.getName(), post.getDescription(), post.getOrder(), post.getCatId(), id);
        return rowAffected > 0;
    }

    public boolean deletePost(Long id) {
        var rowAffected = jdbcTemplate.update("DELETE FROM posts where posts.id = ?", id);
        return rowAffected > 0;
    }
}
