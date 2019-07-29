package com.primecloud.primecloudpaysdk.version2_0;
/**
 * 通过反射获取到字段和值，保存的到这个bean中
 * @author zy
 *
 */
public class SensorData {

	private String sensorId;
	private Object sensorValue;

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public Object getSensorValue() {
		return sensorValue;
	}

	public void setSensorValue(Object sensorValue) {
		this.sensorValue = sensorValue;
	}

	@Override
	public String toString() {
		return "SensorData [sensorId=" + sensorId + ", sensorValue=" + sensorValue + "]";
	}
}
