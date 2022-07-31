package com.ahhmet.bankingSystem.models;

import org.apache.ibatis.type.Alias;

@Alias("LogModel")
public class LogModel {
	private String log;

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}
}
