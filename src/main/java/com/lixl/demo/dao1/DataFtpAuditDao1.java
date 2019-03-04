package com.lixl.demo.dao1;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.lixl.demo.entity.DataFtpAudit;

//两个数据库都有相同的表，读写SQL都一样。但是表数据不同，DAO不同，用来区分测试不同数据库。
@Mapper
public interface DataFtpAuditDao1 {

	@Select( "select id,clearing_date,file_type,file_name,file_size,file_record_num,file_handle_status,src_file_id,reg_date from data_ftp_audit where id=#{id}" )
	public DataFtpAudit selectDataFtpAudit(String id);

	@Insert( "INSERT INTO data_ftp_audit(id,clearing_date,file_type,file_name,file_size,file_record_num,file_handle_status,src_file_id,reg_date) VALUES(" + 
			"#{id},#{clearingDate},#{fileType},#{fileName},#{fileSize},#{fileRecordNum},#{fileHandleStatus},#{srcFileId},#{regDate})" )
	public int addDataFtpAudit(DataFtpAudit info);
	
}
