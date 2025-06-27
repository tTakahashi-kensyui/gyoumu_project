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
		String sql = "INSERT INTO shift (staff_id, shift_date,time_slot, status) VALUES ( ?, ?, ?, ?)";
		jdbc.update(sql,
				shift.getStaffId(),
				shift.getShiftDate(),
				shift.getTimeSlot(),
				shift.getStatus());
	}

	// ID指定で取得（編集用）
	public ShiftEntity findById(Long id) {
		String sql = "SELECT * FROM shift WHERE shift_id = ?";
		return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(ShiftEntity.class), id);
	}
	
	public List<ShiftEntity> findByMonth(int year,int month){
	    String sql = "SELECT * FROM shift WHERE YEAR(shift_date) = ? AND MONTH(shift_date) = ? ORDER BY staff_id, shift_date";
	    return jdbc.query(sql, new BeanPropertyRowMapper<>(ShiftEntity.class), year, month);
		
	}

	// 更新
	public void update(ShiftEntity shift) {
		String sql = "UPDATE shift SET staff_id = ?, shift_date = ?, time_slot = ?, status = ? WHERE shift_id = ?";
		jdbc.update(sql,
				shift.getShiftId(),
				shift.getStaffId(),
				shift.getShiftDate(),
				shift.getTimeSlot(),
				shift.getStatus());
	}

	// 削除
	public void delete(Long id) {
		String sql = "DELETE FROM shift WHERE shift_id = ?";
		jdbc.update(sql, id);
	}

	//スタッフ名付きシフト一覧
	public List<ShiftEntity> findAllWithStaffName() {
		String sql = """
				SELECT s.shift_id AS shift_id, s.staff_id, st.user_name AS staff_name, s.shift_date, s.time_slot, s.`status`
				FROM shift s
				JOIN userdata st ON s.staff_id = st.user_id
				ORDER BY s.shift_date, s.staff_id
				        """;

		return jdbc.query(sql, (rs, rowNum) -> {
			ShiftEntity shift = new ShiftEntity();
			shift.setShiftId(rs.getLong("shift_id"));
			shift.setStaffId(rs.getLong("staff_id"));
			shift.setStaffName(rs.getString("staff_name")); // ←追加
			shift.setShiftDate(rs.getDate("shift_date").toLocalDate());
			shift.setTimeSlot(rs.getString("time_slot"));
			shift.setStatus(rs.getString("status"));
			return shift;
		});
	}

}
