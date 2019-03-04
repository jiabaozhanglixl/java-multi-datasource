package com.lixl.demo.test;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.lixl.demo.entity.DataFtpAudit;

import lombok.extern.slf4j.Slf4j;

import com.lixl.demo.dataservice.DataFtpAuditService1;
import com.lixl.demo.dataservice.DataFtpAuditService2;

@Slf4j
@Service
public class TestMultiDataSource {

    @Autowired
	private DataFtpAuditService1 dataFtpAuditService1;

    @Autowired
	private DataFtpAuditService2 dataFtpAuditService2;

    @Autowired
    private PlatformTransactionManager transactionManager;
    
    public void doTest() {
    	log.info("start test ...");

    	//手工进行事务管理，更容易管理和控制事务的作用范围，更符合实际业务逻辑处理需要。
		DefaultTransactionDefinition def=new DefaultTransactionDefinition();
		def.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status=transactionManager.getTransaction(def);
    	
		try {
			//在两个数据库的data_ftp_audit中，插入不同的记录。数据库1里面的id号是a1。数据库2里面的id号是a2。进行查询测试。
			//测试数据库1的查询。
	    	System.out.println("test database1");
	    	DataFtpAudit info1=dataFtpAuditService1.selectDataFtpAudit("a1");
	    	if (info1!=null) System.out.println("a1="+info1.getFileName());
	    	else System.out.println("not find a1");
	    	DataFtpAudit info2=dataFtpAuditService1.selectDataFtpAudit("a2");
	    	if (info2!=null) System.out.println("a2="+info2.getFileName());
	    	else System.out.println("not find a2");
	
	    	System.out.println("test database2");
	    	DataFtpAudit info3=dataFtpAuditService2.selectDataFtpAudit("a1");
	    	if (info3!=null) System.out.println("a1="+info3.getFileName());
	    	else System.out.println("not find a1");
	    	DataFtpAudit info4=dataFtpAuditService2.selectDataFtpAudit("a2");
	    	if (info4!=null) System.out.println("a2="+info4.getFileName());
	    	else System.out.println("not find a2");
	    	

	    	//测试分布式事务。向两个数据库分别插入记录。测试提交和回滚。两个数据库的插入，要么一起提交，要么一起回滚。
	    	info1=new DataFtpAudit();
	    	info1.setId("a3");
	    	info1.setClearingDate("a3");
	    	info1.setFileHandleStatus("a3");
	    	info1.setFileRecordNum(30);
	    	info1.setFileSize(30);
	    	info1.setFileType("a3");
	    	info1.setFileName("file3");
	    	info1.setSrcFileId("a3");
	    	info1.setRegDate(new Date());
	    	dataFtpAuditService1.addDataFtpAudit(info1);
	    	
	    	info4=new DataFtpAudit();
	    	info4.setId("a4");
	    	info4.setClearingDate("a4");
	    	info4.setFileHandleStatus("a4");
	    	info4.setFileRecordNum(40);
	    	info4.setFileSize(40);
	    	info4.setFileType("a4");
	    	info4.setFileName("file4");
	    	info4.setSrcFileId("a4");
	    	info4.setRegDate(new Date());
	    	dataFtpAuditService2.addDataFtpAudit(info4);
	    	
	
			try {transactionManager.commit(status);} catch (Exception e2) {log.error(e2.getMessage(),e2);}		//提交
			//try {transactionManager.rollback(status);} catch (Exception e2) {log.error(e2.getMessage(),e2);}		//回滚
		}
		catch (Exception ex) {
			ex.printStackTrace();
			try {transactionManager.rollback(status);} catch (Exception e2) {log.error(e2.getMessage(),e2);}		//回滚
		}
    	
    	log.info("finish test ...");
    }
}
