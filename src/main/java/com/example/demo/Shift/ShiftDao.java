package com.example.demo.Shift;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShiftDao {

	private JdbcTemplate jdbc;
	
	// 全シフト取得
    public List<ShiftEntity> findAll() {
        String sql = "SELECT * FROM shift ORDER BY shift_date";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(ShiftEntity.class));
    }

    // 新規登録（※更新機能を分けるならinsert専用）
    public void insert(ShiftEntity shift) {
        String sql = "INSERT INTO shift (staff_id, shift_date, start_time, end_time, status) VALUES (?, ?, ?, ?, ?)";
        jdbc.update(sql,
            shift.getId(),
            shift.getShiftDate(),
            shift.getStartTime(),
            shift.getEndTime(),
            shift.getStatus()
        );
    }

    // ID指定で取得（編集用）
    public ShiftEntity findById(Long id) {
        String sql = "SELECT * FROM shift WHERE id = ?";
        return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(ShiftEntity.class), id);
    }

    // 更新
    public void update(ShiftEntity shift) {
        String sql = "UPDATE shift SET staff_id = ?, shift_date = ?, start_time = ?, end_time = ?, status = ? WHERE id = ?";
        jdbc.update(sql,
            shift.getId(),
            shift.getShiftDate(),
            shift.getStartTime(),
            shift.getEndTime(),
            shift.getStatus(),
            shift.getId()
        );
    }

    // 削除
    public void delete(Long id) {
        String sql = "DELETE FROM shift WHERE id = ?";
        jdbc.update(sql, id);
    }
}
