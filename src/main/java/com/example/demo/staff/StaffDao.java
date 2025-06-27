package com.example.demo.staff;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StaffDao {

    private final JdbcTemplate jdbc;

    public StaffDao(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public List<StaffEntity> findAll() {
        String sql = "SELECT * FROM userdata ORDER BY user_id";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(StaffEntity.class));
    }

    public StaffEntity findById(Long id) {
        String sql = "SELECT * FROM userdata WHERE user_id = ?";
        return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(StaffEntity.class), id);
    }

    public void insert(StaffEntity staff) {
        String sql = "INSERT INTO userdata (user_name, email, store_id) VALUES (?, ?, ?)";
        jdbc.update(sql, staff.getUser_name(), staff.getEmail(), staff.getStoreId());
    }

    public void update(StaffEntity staff) {
        String sql = "UPDATE userdata SET user_name = ?, email = ?, store_id = ? WHERE user_id = ?";
        jdbc.update(sql, staff.getUser_name(), staff.getEmail(), staff.getStoreId(), staff.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM userdata WHERE user_id = ?";
        jdbc.update(sql, id);
    }
}
