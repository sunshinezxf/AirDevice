package bean;

public class DeviceCity {
	private String deviceID;
	private String city;
	private int status;
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public DeviceCity() {
		super();
	}
	public DeviceCity(String deviceID, String city, int status) {
		super();
		this.deviceID = deviceID;
		this.city = city;
		this.status = status;
	}
	
}
