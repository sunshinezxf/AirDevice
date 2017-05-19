package dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import bean.DeviceName;
import bean.DeviceShareCode;
import bean.DeviceStatus;
import bean.UserDevice;

@Repository
public class DeviceVipDao extends BaseDaoImpl{
	public List<DeviceStatus> getUserDevice(String userID){
		return sqlSession.selectList("userVip.getUserDevice", userID);
	}
	
	public DeviceName getDeviceName(String deviceID){
		return sqlSession.selectOne("userVip.selectDeviceName", deviceID);
	}
	
	public boolean updateDeviceName(DeviceName deviceName){
		return sqlSession.update("userVip.updateDeviceName", deviceName) > 0;
	}
	
	public boolean insertDeviceName(DeviceName deviceName){
		return sqlSession.insert("userVip.insertDeviceName", deviceName) > 0;
	}
	
	public DeviceShareCode getDeviceShareCode(String token){
		return sqlSession.selectOne("userVip.getDeviceShareCode", token);
	}
	
	public boolean insertDeviceShareCode(DeviceShareCode deviceShareCode){
		return sqlSession.insert("userVip.insertDeviceShareCode", deviceShareCode) > 0;
	}
	
	public boolean updateDeviceShareCode(DeviceShareCode deviceShareCode){
		return sqlSession.update("userVip.updateDeviceShareCode", deviceShareCode) > 0;
	}
	
	public boolean insertUserDevice(UserDevice userDevice){
		return sqlSession.insert("userVip.insertNewUserDevice", userDevice) > 0;
	}
}