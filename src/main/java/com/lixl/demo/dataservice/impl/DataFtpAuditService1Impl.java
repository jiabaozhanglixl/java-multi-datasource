package com.lixl.demo.dataservice.impl;

import com.lixl.demo.dataservice.DataFtpAuditService1;
import com.lixl.demo.entity.DataFtpAudit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lixl.demo.dao1.DataFtpAuditDao1;

@Service
public class DataFtpAuditService1Impl implements DataFtpAuditService1 {

    @Autowired  
    private DataFtpAuditDao1 dataFtpAuditDao1;
	
	@Override
	public DataFtpAudit selectDataFtpAudit(String id) {
		return dataFtpAuditDao1.selectDataFtpAudit(id);
	}

	@Override
	public int addDataFtpAudit(DataFtpAudit info) {
		return dataFtpAuditDao1.addDataFtpAudit(info);
	}

}
