package com.lixl.demo.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

//简化测试。不同的数据库，具有一个相同的表，但是不同数据库里面的数据不同。
@Getter
@Setter
public class DataFtpAudit {
	private String id;
	private String clearingDate;
	private String fileType;
	private String fileName;
	private int fileSize;
	private int fileRecordNum;
	private String fileHandleStatus;
	private String srcFileId;
	private Date regDate;
}
