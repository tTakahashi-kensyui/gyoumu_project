package com.example.demo.Shift;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShiftDao {

	@Autowired
	private JdbcTemplate jdbc;
	
	// 全シフト取得
    public List<ShiftEntity> findAll() {
        String sql = "SELECT * FROM shift ORDER BY shift_date";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(ShiftEntity.class));
    }

    // 新規登録（※更新機能を分けるならinsert専用）
    public void insert(ShiftEntity shift) {
        String sql = "INSERT INTO shift (staff_id, shift_date,time_slot, status) VALUES (?, ?, ?, ?, ?)";
        jdbc.update(sql,
            shift.getShiftId(),
            shift.getStaffId(),
            shift.getShiftDate(),
            shift.getTimeSlot(),
            shift.getStatus()
        );
    }

    // ID指定で取得（編集用）
    public ShiftEntity findById(Long id) {
        String sql = "SELECT * FROM shift WHERE shift_id = ?";
        return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(ShiftEntity.class), id);
    }

    // 更新
    public void update(ShiftEntity shift) {
        String sql = "UPDATE shift SET staff_id = ?, shift_date = ?, time_slot = ?, status = ? WHERE shift_id = ?";
        jdbc.update(sql,
                shift.getShiftId(),
                shift.getStaffId(),
                shift.getShiftDate(),
                shift.getTimeSlot(),
                shift.getStatus()
        );
    }

    // 削除
    public void delete(Long id) {
        String sql = "DELETE FROM shift WHERE shift_id = ?";
        jdbc.update(sql, id);
    }
}
