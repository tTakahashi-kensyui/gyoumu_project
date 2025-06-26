package com.example.demo.Attendance;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AttendanceDao {

	private final JdbcTemplate jdbcTemplate;

	public AttendanceDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// 勤怠一覧
	public List<AttendanceEntity> findAll() {
		String sql = "SELECT * FROM attendanceData";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AttendanceEntity.class));
	}

	// 出勤処理
	public void startAttendance(int userId) {
		// 現在時刻
		String nowTime = LocalTime.now().toString();
		String today = LocalDate.now().toString();

		String sql = """
				UPDATE attendanceData
				SET status = '出勤中',
				    start_time = ?
				WHERE user_id = ? AND date = ?
				""";
		jdbcTemplate.update(sql, nowTime, userId, today);
	}

	// 退勤処理
	public void endAttendance(int userId) {
		// 現在時刻
		String nowTime = LocalTime.now().toString();
		String today = LocalDate.now().toString();

		String sql = """
				UPDATE attendanceData
				SET status = '退勤済み',
				    end_time = ?
				WHERE user_id = ? AND date = ?
				""";
		jdbcTemplate.update(sql, nowTime, userId, today);
	}
	
	// 勤怠一覧（店舗名付き）
		public List<AttendanceWithStoreEntity> findAllWithStore() {
			String sql = """
				SELECT
					a.attendance_id,
					a.user_id,
					a.date,
					a.status,
					a.start_time,
					a.end_time,
					a.break_time,
					a.work_time,
					a.remark,
					s.store_name
				FROM
					attendanceData a
				JOIN userData u ON a.user_id = u.user_id
				JOIN storeData s ON u.store_id = s.store_id
			""";

			return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AttendanceWithStoreEntity.class));
		}
}