-- 店舗データ
INSERT INTO storeData (store_id, store_name) VALUES (1, '浦安市');
INSERT INTO storeData (store_id, store_name) VALUES (2, '市川市');
INSERT INTO storeData (store_id, store_name) VALUES (3, '松戸市');

-- ユーザーデータ
INSERT INTO userData (user_id, user_name, email, store_id) VALUES (1, '山田太郎', 'yamada@example.com', 1);
INSERT INTO userData (user_id, user_name, email, store_id) VALUES (2, '佐藤花子', 'sato@example.com', 3);
INSERT INTO userData (user_id, user_name, email, store_id) VALUES (3, '鈴木一郎', 'suzuki@example.com', 2);
INSERT INTO userData (user_id, user_name, email, store_id) VALUES (4, '髙橋翼'  , 'takahashi@example.com', 2);

-- 勤怠データ（2025-06-25）
INSERT INTO attendanceData (attendance_id, user_id, date, start_time, end_time, break_time, work_time, status, remark)
VALUES 
(1, 1, CURRENT_DATE, NULL, NULL, 0, NULL, '未出勤', NULL),
(2, 2, CURRENT_DATE, '09:00:00', NULL, 0, NULL, '出勤中', NULL),
(3, 4, CURRENT_DATE, CURRENT_TIME, NULL, 0, NULL, '出勤中', NULL);

-- シフトデータ
INSERT INTO shift (staff_id,shift_date,time_slot,status) VALUES (1,'2025-07-01','午前','希望');
INSERT INTO shift (staff_id,shift_date,time_slot,status) VALUES (3,'2025-07-01','午後','確定');
INSERT INTO shift (staff_id,shift_date,time_slot,status) VALUES (2,'2025-07-02','両方','確定');
