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

		String today = LocalDate.now().toString();
		String nowTime = LocalTime.now().toString();

		//既に今日のデータがあるかを確認する
		AttendanceEntity entity = jdbcTemplate.queryForObject(
				"SELECT * FROM attendanceData WHERE user_id = ? AND date = ?",
				new BeanPropertyRowMapper<>(AttendanceEntity.class),
				userId, today);

		if (entity != null) {
			//あるならUPDATE
			String updateSql = """
					UPDATE attendanceData
					SET status = '出勤中',
						start_time = ?,
						end_time = NULL,
						work_time = NULL,
						remark = NULL
						WHERE user_id = ? AND date = ?
					""";
			jdbcTemplate.update(updateSql, nowTime, userId, today);
		} else {
			//	ないならINSERT
			String insertSql = """
					INSERT INTO attendanceData(user_id,date,start_time,status)
					VALUES (?,?,?,'出勤中')
					""";
			jdbcTemplate.update(insertSql, nowTime, userId, today);
		}
	}

	// 退勤処理
	public void endAttendance(int userId) {

		String today = LocalDate.now().toString();

		//出勤中のデータを取得
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

		//勤務時間計算
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

	//勤怠一覧（ユーザー名付き）
	public List<AttendanceWithUserEntity> findAttendanceWithUser() {
		String sql = """
				    SELECT
				        a.attendance_id,
				        a.user_id,
				        u.user_name,
				        a.date,
				        a.start_time,
				        a.end_time,
				        a.break_time,
				        a.work_time,
				        a.status,
				        a.remark
				    FROM attendanceData a
				    JOIN userData u ON a.user_id = u.user_id
				    ORDER BY a.attendance_id
				""";

		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AttendanceWithUserEntity.class));
	}
}