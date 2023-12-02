-- 添加字段函数，给目标表添加字段，如果字段已存在则不添加
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddFields`(
	IN tableName VARCHAR(255),
	IN columnName VARCHAR(255),
	IN tailSQLCode VARCHAR(255))
BEGIN
	  -- Check if the column exists
    IF NOT EXISTS (
        SELECT *
        FROM information_schema.columns
        WHERE table_name = tableName AND column_name = columnName
    ) THEN
        -- If the column doesn't exist, add it with specified type and default value
        SET @sql = CONCAT('ALTER TABLE ', tableName,' ADD COLUMN ', columnName, ' ', tailSQLCode);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
END IF;
END

-- 给表添加基础字段，包括：delete_flag, create_time, update_time, create_by, update_by
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddBaseFields`(IN `tableName` VARCHAR(255))
BEGIN

-- 调用添加 'delete_flag' 字段
CALL AddFields(tableName, 'delete_flag', 'BIT NOT NULL DEFAULT 0');

-- 调用添加 'create_time' 字段
CALL AddFields(tableName, 'create_time', 'DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP');

-- 调用添加 'update_time' 字段
CALL AddFields(tableName, 'update_time', 'DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP');

-- 调用添加 'create_by' 字段
CALL AddFields(tableName, 'create_by', 'VARCHAR(255) NULL DEFAULT NULL');

-- 调用添加 'update_by' 字段
CALL AddFields(tableName, 'update_by', 'VARCHAR(255) NULL DEFAULT NULL');

END