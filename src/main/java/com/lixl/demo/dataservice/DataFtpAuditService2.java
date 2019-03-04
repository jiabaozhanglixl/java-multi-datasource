package com.lixl.demo.dataservice;

import com.lixl.demo.entity.DataFtpAudit;

//不同的数据库，不同的DAO，不同的数据操作service。
public interface DataFtpAuditService2 {

	public DataFtpAudit selectDataFtpAudit(String id);
	
	public int addDataFtpAudit(DataFtpAudit info);
	
}
