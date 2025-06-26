-- 店舗テーブル
CREATE TABLE storeData (
    store_id INT PRIMARY KEY AUTO_INCREMENT,
    store_name VARCHAR(100) NOT NULL
);

-- ユーザーテーブル
CREATE TABLE userData (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    store_id INT NOT NULL,
    FOREIGN KEY (store_id) REFERENCES storeData(store_id)
);

-- 勤怠テーブル
CREATE TABLE attendanceData (
    attendance_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    date DATE NOT NULL,
    start_time TIME,
    end_time TIME,
    break_time DOUBLE DEFAULT 0,
    work_time TIME,
    status VARCHAR(50) NOT NULL DEFAULT '未出勤',
    remark VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES userData(user_id)
);

-- シフトテーブル
CREATE TABLE shift(
	shift_id int PRIMARY KEY AUTO_INCREMENT,
	staff_id int FOREIGN KEY REFERENCES userData(user_id),
	shift_date DATE,
	start_time TIME,
	end_time TIME,
	status VARCHAR
	
);