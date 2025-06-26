package com.example.demo.Attendance;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

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
		// 出勤中のデータを探す
		String selectSql = """
				    SELECT * FROM attendanceData
				    WHERE user_id = ? AND status = '出勤中'
				""";

		List<Map<String, Object>> list = jdbcTemplate.queryForList(selectSql, userId);

		if (list.isEmpty()) {
			System.out.println("出勤中のデータがありません");
			return;
		}

		Map<String, Object> attendance = list.get(0);
		int attendanceId = (int) attendance.get("attendance_id");
		Time startTimeSql = (Time) attendance.get("start_time");
		LocalTime startTime = startTimeSql.toLocalTime();

		LocalTime endTime = LocalTime.now();

		// 勤務時間計算
		Duration duration = Duration.between(startTime, endTime);
		long hours = duration.toHours();
		long minutes = duration.toMinutesPart();
		long seconds = duration.toSecondsPart();

		LocalTime workTime = LocalTime.of((int) hours, (int) minutes, (int) seconds);

		// SQL UPDATE
		String updateSql = """
				    UPDATE attendanceData
				    SET end_time = ?, work_time = ?, status = '退勤済み'
				    WHERE attendance_id = ?
				""";

		jdbcTemplate.update(updateSql, endTime, workTime, attendanceId);
	}

	//	// 勤怠一覧（店舗名付き）
	//	public List<AttendanceWithStoreEntity> findAllWithStore() {
	//		String sql = """
	//					SELECT
	//						a.attendance_id,
	//						a.user_id,
	//						a.date,
	//						a.status,
	//						a.start_time,
	//						a.end_time,
	//						a.break_time,
	//						a.work_time,
	//						a.remark,
	//						s.store_name
	//					FROM
	//						attendanceData a
	//					JOIN userData u ON a.user_id = u.user_id
	//					JOIN storeData s ON u.store_id = s.store_id
	//				""";
	//
	//		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AttendanceWithStoreEntity.class));
	//	}

	 //勤怠一覧（店舗名、ユーザー名付き）
	public List<AttendanceWithStoreAndUserEntity> findAttendanceWithStoreAndUser() {
		String sql = """
				    SELECT
				        a.attendance_id,
				        a.user_id,
				        u.user_name,
				        s.store_id,
				        s.store_name,
				        a.date,
				        a.start_time,
				        a.end_time,
				        a.break_time,
				        a.work_time,
				        a.status,
				        a.remark
				    FROM attendanceData a
				    JOIN userData u ON a.user_id = u.user_id
				    JOIN storeData s ON u.store_id = s.store_id
				    ORDER BY a.attendance_id
				""";

		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			AttendanceWithStoreAndUserEntity entity = new AttendanceWithStoreAndUserEntity();
			entity.setAttendanceId(rs.getInt("attendance_id"));
			entity.setUserId(rs.getInt("user_id"));
			entity.setUserName(rs.getString("user_name"));
			entity.setStoreId(rs.getInt("store_id"));
			entity.setStoreName(rs.getString("store_name"));
			entity.setDate(rs.getDate("date").toLocalDate());
			entity.setStartTime(rs.getTime("start_time") != null ? rs.getTime("start_time").toLocalTime() : null);
			entity.setEndTime(rs.getTime("end_time") != null ? rs.getTime("end_time").toLocalTime() : null);
			entity.setBreakTime(rs.getDouble("break_time"));
			entity.setWorkTime(rs.getString("work_time"));
			entity.setStatus(rs.getString("status"));
			entity.setRemark(rs.getString("remark"));
			return entity;
		});
	}
	
	public AttendanceWithStoreAndUserEntity findByUserIdAndToday(int userId) {
	    String sql = "SELECT * FROM attendanceData WHERE user_id = ? AND date = CURRENT_DATE";
	    return jdbcTemplate.query(sql, new Object[]{userId}, rs -> {
	        if (rs.next()) {
	            AttendanceWithStoreAndUserEntity entity = new AttendanceWithStoreAndUserEntity();
	            entity.setAttendanceId(rs.getInt("attendance_id"));
	            entity.setStatus(rs.getString("status"));
	            // 必要なら他のデータもセット
	            return entity;
	        }
	        return null;
	    });
	}
}