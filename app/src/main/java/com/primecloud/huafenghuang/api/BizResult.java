package com.primecloud.huafenghuang.api;

import java.io.Serializable;

public class BizResult implements Serializable {

	private String data;
	private String message;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "BizResult{" +
				"data='" + data + '\'' +
				", message='" + message + '\'' +
				'}';
	}
}

