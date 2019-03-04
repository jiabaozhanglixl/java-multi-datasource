CREATE TABLE data_ftp_audit (
  id varchar(40) NOT NULL DEFAULT '0',
  clearing_date char(8) DEFAULT NULL,
  file_type char(2) DEFAULT NULL,
  file_name char(32) DEFAULT NULL,
  file_size int(10) unsigned DEFAULT NULL,
  file_record_num int(11) DEFAULT NULL,
  file_handle_status char(2) DEFAULT NULL,
  src_file_id varchar(40) DEFAULT NULL,
  reg_date datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
