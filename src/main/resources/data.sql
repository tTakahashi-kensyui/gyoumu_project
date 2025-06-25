-- 店舗データ
INSERT INTO storeData (store_id, store_name) VALUES (1, '浦安市');
INSERT INTO storeData (store_id, store_name) VALUES (2, '市川市');
INSERT INTO storeData (store_id, store_name) VALUES (3, '松戸市');

-- ユーザーデータ
INSERT INTO userData (user_id, name, email, store_id) VALUES (1, '山田太郎', 'yamada@example.com', 1);
INSERT INTO userData (user_id, name, email, store_id) VALUES (2, '佐藤花子', 'sato@example.com', 3);
INSERT INTO userData (user_id, name, email, store_id) VALUES (3, '鈴木一郎', 'suzuki@example.com', 2);

-- 勤怠データ（2025-06-25）
INSERT INTO attendanceData (
    attendance_id, user_id, date, start_time, end_time, break_time, work_time, status, remark
) VALUES (
    1, 1, '2025-06-25', '09:00:00', '18:00:00', 1.0, 8.0, '出勤', ''
);

INSERT INTO attendanceData (
    attendance_id, user_id, date, start_time, end_time, break_time, work_time, status, remark
) VALUES (
    2, 2, '2025-06-25', NULL, NULL, NULL, NULL, '欠勤', '体調不良'
);

INSERT INTO attendanceData (
    attendance_id, user_id, date, start_time, end_time, break_time, work_time, status, remark
) VALUES (
    3, 3, '2025-06-25', '10:00:00', '19:00:00', 1.0, 8.0, '出勤', ''
);