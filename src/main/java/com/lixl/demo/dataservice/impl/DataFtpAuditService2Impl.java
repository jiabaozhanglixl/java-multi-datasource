package com.lixl.demo.dataservice.impl;

import com.lixl.demo.dataservice.DataFtpAuditService2;
import com.lixl.demo.entity.DataFtpAudit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lixl.demo.dao2.DataFtpAuditDao2;

@Service
public class DataFtpAuditService2Impl implements DataFtpAuditService2 {

    @Autowired  
    private DataFtpAuditDao2 dataFtpAuditDao2;
	
	@Override
	public DataFtpAudit selectDataFtpAudit(String id) {
		return dataFtpAuditDao2.selectDataFtpAudit(id);
	}

	@Override
	public int addDataFtpAudit(DataFtpAudit info) {
		return dataFtpAuditDao2.addDataFtpAudit(info);
	}

}
