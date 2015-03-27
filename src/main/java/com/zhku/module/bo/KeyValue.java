package com.zhku.module.bo;
/**
 * 包装一些键值对
 * @author JackCan
 *
 */
public class KeyValue {
	private String key;
	private String value;
	public KeyValue(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
