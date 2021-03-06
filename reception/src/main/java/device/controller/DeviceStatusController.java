package device.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import location.service.LocationService;
import model.CleanerStatus;
import model.ResultMap;
import model.ReturnCode;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import util.ReceptionConstant;
import utils.Constant;
import vo.location.DeviceCityVo;
import vo.vip.ConsumerVo;
import bean.AirCompareVO;
import bean.CityAqi;
import bean.DeviceCity;
import dao.DeviceAttributeDao;
import device.service.AqiDataUpdateService;
import device.service.DeviceAttributeService;
import device.service.DeviceInitService;
import device.service.DeviceStatusService;

@RequestMapping("/status")
@RestController
public class DeviceStatusController {
	Logger LOG = LoggerFactory.getLogger(DeviceStatusController.class);
	@Autowired
	private DeviceStatusService deviceStatusService;
	public void setDeviceStatusService(DeviceStatusService deviceStatusService) {
		this.deviceStatusService = deviceStatusService;
	}
	
	@Autowired
	private AqiDataUpdateService aqiDataUpdateService;
	public void setAqiDataUpdateService(AqiDataUpdateService aqiDataUpdateService) {
		this.aqiDataUpdateService = aqiDataUpdateService;
	}
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private DeviceAttributeService deviceAttributeService;
	@Autowired
	private DeviceInitService deviceInitService;

	@RequiresAuthentication
	@RequestMapping("/device/{deviceID}")
	public ResultMap getDeviceStatus(@PathVariable("deviceID")String device){
		ResultMap resultMap = new ResultMap();
		CleanerStatus cleanerStatus = deviceStatusService.getCleanerStatus(device);
		//boolean advance = deviceAttributeService.checkDeviceAdvanced(device);
		int velo_max = deviceAttributeService.getDeviceVelocity(device);
		List<String> components = deviceAttributeService.getDeviceComponents(device);
		if (cleanerStatus == null) {
			resultMap.setStatus(ResultMap.STATUS_FAILURE);
			resultMap.setInfo("未找到相应设备状态");
		}else {
			resultMap.setStatus(ResultMap.STATUS_SUCCESS);
			resultMap.addContent(ReceptionConstant.CLEANER_STATUS, cleanerStatus);
			resultMap.addContent(ReceptionConstant.COMPONENTS, components);
			resultMap.addContent(ReceptionConstant.VELO_MAX, velo_max);
		}
		return resultMap;
	}
	
	@RequiresAuthentication
	@RequestMapping("/{deviceID}/power/{power}")
	public ResultMap powerControl(@PathVariable("deviceID")String deviceID, @PathVariable("power")String power){
		ResultMap resultMap = new ResultMap();
		int data = Integer.parseInt(power);
		Subject subject = SecurityUtils.getSubject();
		ConsumerVo current = (ConsumerVo) subject.getPrincipal();
		if(current == null || current.getCustomerId() == null){
			resultMap.setStatus(ResultMap.STATUS_FORBIDDEN);
			return resultMap;
		}
		String userID = current.getCustomerId();
		ReturnCode returnCode = deviceStatusService.deviceControl(data, deviceID, userID, Constant.POWER);
		if (returnCode.equals(ReturnCode.FAILURE)) {
			resultMap.setStatus(ResultMap.STATUS_FAILURE);
			resultMap.setInfo("无法远程控制设备");
		}else if (returnCode.equals(ReturnCode.FORBIDDEN)) {
			resultMap.setStatus(ResultMap.STATUS_FORBIDDEN);
			resultMap.setInfo("没有权限");
		}else if (returnCode.equals(ReturnCode.SUCCESS)) {
			resultMap.setStatus(ResultMap.STATUS_SUCCESS);
		}
		return resultMap;
	}
	
	@RequiresAuthentication
	@RequestMapping("/{deviceID}/velocity/{velocity}")
	public ResultMap velocityControl(@PathVariable("deviceID")String deviceID, @PathVariable("velocity")String velocity){
		ResultMap resultMap = new ResultMap();
		int data = Integer.parseInt(velocity);
		Subject subject = SecurityUtils.getSubject();
		ConsumerVo current = (ConsumerVo) subject.getPrincipal();
		if(current == null || current.getCustomerId() == null){
			resultMap.setStatus(ResultMap.STATUS_FORBIDDEN);
			return resultMap;
		}
		String userID = current.getCustomerId();
		ReturnCode returnCode = deviceStatusService.deviceControl(data, deviceID, userID, Constant.VELOCITY);
		if (returnCode.equals(ReturnCode.FAILURE)) {
			resultMap.setStatus(ResultMap.STATUS_FAILURE);
			resultMap.setInfo("无法远程控制设备");
		}else if (returnCode.equals(ReturnCode.FORBIDDEN)) {
			resultMap.setStatus(ResultMap.STATUS_FORBIDDEN);
			resultMap.setInfo("没有权限");
		}else if (returnCode.equals(ReturnCode.SUCCESS)) {
			resultMap.setStatus(ResultMap.STATUS_SUCCESS);
		}
		return resultMap;
	}
	
	@RequiresAuthentication
	@RequestMapping("/{deviceID}/heat/{heat}")
	public ResultMap heatControl(@PathVariable("deviceID")String deviceID, @PathVariable("heat")String heat){
		ResultMap resultMap = new ResultMap();
		int data = Integer.parseInt(heat);
		Subject subject = SecurityUtils.getSubject();
		ConsumerVo current = (ConsumerVo) subject.getPrincipal();
		if(current == null || current.getCustomerId() == null){
			resultMap.setStatus(ResultMap.STATUS_FORBIDDEN);
			return resultMap;
		}
		String userID = current.getCustomerId();
		ReturnCode returnCode = deviceStatusService.deviceControl(data, deviceID, userID, Constant.HEAT);
		if (returnCode.equals(ReturnCode.FAILURE)) {
			resultMap.setStatus(ResultMap.STATUS_FAILURE);
			resultMap.setInfo("无法远程控制设备");
		}else if (returnCode.equals(ReturnCode.FORBIDDEN)) {
			resultMap.setStatus(ResultMap.STATUS_FORBIDDEN);
			resultMap.setInfo("没有权限");
		}else if (returnCode.equals(ReturnCode.SUCCESS)) {
			resultMap.setStatus(ResultMap.STATUS_SUCCESS);
		}
		return resultMap;
	}
	
	@RequiresAuthentication
	@RequestMapping("/{deviceID}/workmode/{mode}")
	public ResultMap workmodeControl(@PathVariable("deviceID")String deviceID, @PathVariable("mode")String mode){
		ResultMap resultMap = new ResultMap();
		int data = Integer.parseInt(mode);
		Subject subject = SecurityUtils.getSubject();
		ConsumerVo current = (ConsumerVo) subject.getPrincipal();
		if(current == null || current.getCustomerId() == null){
			resultMap.setStatus(ResultMap.STATUS_FORBIDDEN);
			return resultMap;
		}
		String userID = current.getCustomerId();
		ReturnCode returnCode = deviceStatusService.deviceControl(data, deviceID, userID, Constant.WORKMODE);
		if (returnCode.equals(ReturnCode.FAILURE)) {
			resultMap.setStatus(ResultMap.STATUS_FAILURE);
			resultMap.setInfo("无法远程控制设备");
		}else if (returnCode.equals(ReturnCode.FORBIDDEN)) {
			resultMap.setStatus(ResultMap.STATUS_FORBIDDEN);
			resultMap.setInfo("没有权限");
		}else if (returnCode.equals(ReturnCode.SUCCESS)) {
			resultMap.setStatus(ResultMap.STATUS_SUCCESS);
		}
		return resultMap;
	}
	
	@RequiresAuthentication
	@RequestMapping("/{deviceID}/cycle/{cycle}")
	public ResultMap cycleControl(@PathVariable("deviceID")String deviceID, @PathVariable("cycle")String cycle){
		ResultMap resultMap = new ResultMap();
		int data = Integer.parseInt(cycle);
		Subject subject = SecurityUtils.getSubject();
		ConsumerVo current = (ConsumerVo) subject.getPrincipal();
		if(current == null || current.getCustomerId() == null){
			resultMap.setStatus(ResultMap.STATUS_FORBIDDEN);
			return resultMap;
		}
		String userID = current.getCustomerId();
		ReturnCode returnCode = deviceStatusService.deviceControl(data, deviceID, userID, Constant.CYCLE);
		if (returnCode.equals(ReturnCode.FAILURE)) {
			resultMap.setStatus(ResultMap.STATUS_FAILURE);
			resultMap.setInfo("无法远程控制设备");
		}else if (returnCode.equals(ReturnCode.FORBIDDEN)) {
			resultMap.setStatus(ResultMap.STATUS_FORBIDDEN);
			resultMap.setInfo("没有权限");
		}else if (returnCode.equals(ReturnCode.SUCCESS)) {
			resultMap.setStatus(ResultMap.STATUS_SUCCESS);
		}
		return resultMap;
	}
	
	@RequiresAuthentication
	@RequestMapping("/{deviceID}/light/{light}")
	public ResultMap lightControl(@PathVariable("deviceID")String deviceID, @PathVariable("light")String light){
		ResultMap resultMap = new ResultMap();
		int data = Integer.parseInt(light);
		Subject subject = SecurityUtils.getSubject();
		ConsumerVo current = (ConsumerVo) subject.getPrincipal();
		if(current == null || current.getCustomerId() == null){
			resultMap.setStatus(ResultMap.STATUS_FORBIDDEN);
			return resultMap;
		}
		String userID = current.getCustomerId();
		ReturnCode returnCode = deviceStatusService.deviceControl(data, deviceID, userID, Constant.LIGHT);
		if (returnCode.equals(ReturnCode.FAILURE)) {
			resultMap.setStatus(ResultMap.STATUS_FAILURE);
			resultMap.setInfo("无法远程控制设备");
		}else if (returnCode.equals(ReturnCode.FORBIDDEN)) {
			resultMap.setStatus(ResultMap.STATUS_FORBIDDEN);
			resultMap.setInfo("没有权限");
		}else if (returnCode.equals(ReturnCode.SUCCESS)) {
			resultMap.setStatus(ResultMap.STATUS_SUCCESS);
		}
		return resultMap;
	}
	
	@RequiresAuthentication
	@RequestMapping("/{deviceID}/uv/{uv}")
	public ResultMap uvControl(@PathVariable("deviceID")String deviceID, @PathVariable("uv")String uv){
		ResultMap resultMap = new ResultMap();
		int data = Integer.parseInt(uv);
		Subject subject = SecurityUtils.getSubject();
		ConsumerVo current = (ConsumerVo) subject.getPrincipal();
		if(current == null || current.getCustomerId() == null){
			resultMap.setStatus(ResultMap.STATUS_FORBIDDEN);
			return resultMap;
		}
		String userID = current.getCustomerId();
		ReturnCode returnCode = deviceStatusService.deviceControl(data, deviceID, userID, Constant.UV);
		if (returnCode.equals(ReturnCode.FAILURE)) {
			resultMap.setStatus(ResultMap.STATUS_FAILURE);
			resultMap.setInfo("无法远程控制设备");
		}else if (returnCode.equals(ReturnCode.FORBIDDEN)) {
			resultMap.setStatus(ResultMap.STATUS_FORBIDDEN);
			resultMap.setInfo("没有权限");
		}else if (returnCode.equals(ReturnCode.SUCCESS)) {
			resultMap.setStatus(ResultMap.STATUS_SUCCESS);
		}
		return resultMap;
	}
	
	@RequiresAuthentication
	@RequestMapping("/city/info/{deviceID}")
	public ResultMap getDeviceCity(@PathVariable("deviceID")String deviceID){
		ResultMap resultMap = new ResultMap();
		Subject subject = SecurityUtils.getSubject();
		ConsumerVo current = (ConsumerVo) subject.getPrincipal();
		if(current == null || current.getCustomerId() == null){
			resultMap.setStatus(ResultMap.STATUS_FORBIDDEN);
			return resultMap;
		}
		String userID = current.getCustomerId();
		DeviceCity deviceCity = deviceStatusService.getDeviceCity(userID, deviceID);
		if(deviceCity == null){
			resultMap.setStatus(ResultMap.STATUS_FAILURE);
			resultMap.setInfo("未找到对应的城市");
		}else{
			resultMap.setStatus(ResultMap.STATUS_SUCCESS);
			resultMap.addContent(ReceptionConstant.DEVICE_CITY, deviceCity);
		}
		return resultMap;
	}
	
	@RequestMapping(value= "/city/config/{deviceID}/{city}", method= RequestMethod.POST)
	public ResultMap configDeviceCity(@PathVariable("deviceID")String deviceID, @PathVariable("city")String city){
		ResultMap resultMap = new ResultMap();
		Subject subject = SecurityUtils.getSubject();
		ConsumerVo current = (ConsumerVo) subject.getPrincipal();
		if(current == null || current.getCustomerId() == null){
			resultMap.setStatus(ResultMap.STATUS_FORBIDDEN);
			return resultMap;
		}
		String userID = current.getCustomerId();
		ReturnCode returnCode = deviceStatusService.setDeviceCity(userID, deviceID, city);
		if (returnCode.equals(ReturnCode.FAILURE)) {
			resultMap.setStatus(ResultMap.STATUS_FAILURE);
			resultMap.setInfo("无法设置所在城市");
		}else if (returnCode.equals(ReturnCode.FORBIDDEN)) {
			resultMap.setStatus(ResultMap.STATUS_FORBIDDEN);
			resultMap.setInfo("没有权限");
		}else if (returnCode.equals(ReturnCode.SUCCESS)) {
			resultMap.setStatus(ResultMap.STATUS_SUCCESS);
		}
		return resultMap;
	}
	
	@RequiresAuthentication
	@RequestMapping(value= "/city/aqi", method= RequestMethod.POST)
	public ResultMap getCityCurrentAqi(HttpServletRequest request){
		ResultMap resultMap = new ResultMap();
		String city = request.getParameter("city");
		CityAqi cityAqi = deviceStatusService.getCityCurrentAqi(city);
		if(cityAqi == null){
			resultMap.setStatus(ResultMap.STATUS_FAILURE);
			resultMap.setInfo(ResultMap.EMPTY_INFO);
		}else {
			resultMap.setStatus(ResultMap.STATUS_SUCCESS);
			resultMap.addContent(ReceptionConstant.CITY_AQI, cityAqi);
		}
		return resultMap;
	}
	
	@RequiresAuthentication
	@RequestMapping(value= "/{deviceID}/aqi/compare")
	public ResultMap getAirCompareVO(@PathVariable("deviceID") String deviceID){
		ResultMap resultMap = new ResultMap();
		AirCompareVO airCompareVO = deviceStatusService.getAirCompareVO(deviceID);
		if(airCompareVO == null || airCompareVO.getInsides().size() != 7){
			deviceInitService.enrichHistory(deviceID);
			airCompareVO = deviceStatusService.getAirCompareVO(deviceID);
			if (airCompareVO == null) {
				resultMap.setStatus(ResultMap.STATUS_FAILURE);
				resultMap.setInfo(ResultMap.EMPTY_INFO);
			}
		}else{
			resultMap.setStatus(ResultMap.STATUS_SUCCESS);
			resultMap.addContent(ReceptionConstant.AIR_COMPARE, airCompareVO);
		}
		return resultMap;
	}
	
	@RequestMapping(value="/data/{token}")
	public ResultMap getShareData(@PathVariable("token")String token){
		ResultMap resultMap = new ResultMap();
		String deviceID = deviceStatusService.getDeviceIDByToken(token);
		if(deviceID == null){
			resultMap.setStatus(ResultMap.STATUS_FAILURE);
			return resultMap;
		}else{
			try{
				int cityData = 0;
				int deviceData = 0;
				CleanerStatus cleanerStatus = deviceStatusService.getCleanerStatus(deviceID);
				AirCompareVO airCompareVO = deviceStatusService.getAirCompareVO(deviceID);
				DeviceCityVo location = locationService.getDeviceLocation(deviceID);
				if(cleanerStatus.getPm25() != 0){
					deviceData = cleanerStatus.getPm25();
				}
				if(location != null) {
					String cityPinyin = location.getCityPinyin();
					CityAqi cityAqi = deviceStatusService.getCityCurrentAqi(cityPinyin);
					if(cityAqi != null){
						cityData = cityAqi.getPm25();
					}
				}
				resultMap.setStatus(ResultMap.STATUS_SUCCESS);
				resultMap.addContent(ReceptionConstant.CLEANER_STATUS, cleanerStatus);
				resultMap.addContent(ReceptionConstant.AIR_COMPARE, airCompareVO);
				resultMap.addContent("cityData", cityData);
				resultMap.addContent("deviceData", deviceData);
			}catch(Exception e){
				LOG.error("get device status failed",e);
			}

		}
		
		return resultMap;
	}
	
	@RequiresAuthentication
	@RequestMapping(value="/data/share", method = RequestMethod.POST)
	public ResultMap shareData(String deviceID){
		ResultMap resultMap = new ResultMap();
		Subject subject = SecurityUtils.getSubject();
		ConsumerVo current = (ConsumerVo) subject.getPrincipal();
		if(current == null || current.getCustomerId() == null){
			resultMap.setStatus(ResultMap.STATUS_FORBIDDEN);
			return resultMap;
		}
		String token = deviceStatusService.generateDeviceReadToken(deviceID);
		if(token != null){
			resultMap.setStatus(ResultMap.STATUS_SUCCESS);
			resultMap.addContent("token", token);
		}else{
			resultMap.setStatus(ResultMap.STATUS_FAILURE);
		}
		return resultMap;
	}
	
	@RequiresAuthentication
	@RequestMapping(value= "/{deviceID}/aqi/current")
	public ResultMap currentAqiCompare(@PathVariable("deviceID") String deviceID){
		ResultMap resultMap = new ResultMap();
		Subject subject = SecurityUtils.getSubject();
		ConsumerVo current = (ConsumerVo) subject.getPrincipal();
		if(current == null || current.getCustomerId() == null){
			resultMap.setStatus(ResultMap.STATUS_FORBIDDEN);
			return resultMap;
		}
		String userID = current.getCustomerId();
		
		//get device pm2.5
		int deviceData = 0;
		CleanerStatus cleanerStatus = deviceStatusService.getCleanerStatus(deviceID);
		if(cleanerStatus != null){
			deviceData = cleanerStatus.getPm25();
		}
		//get city pm2.5
		int cityData = 0;
		DeviceCity deviceCity = deviceStatusService.getDeviceCity(userID, deviceID);
		if(deviceCity != null) {
			String cityPinyin = deviceCity.getCity();
			CityAqi cityAqi = deviceStatusService.getCityCurrentAqi(cityPinyin);
			if(cityAqi != null){
				cityData = cityAqi.getPm25();
			}
		}
		resultMap.setStatus(ResultMap.STATUS_SUCCESS);
		resultMap.addContent("cityData", cityData);
		resultMap.addContent("deviceData", deviceData);
		return resultMap;
	}
	
	@RequestMapping(value= "/test")
	public String test(){
		deviceStatusService.updateAirCondition();
		return "";
	}
	
	@RequiresAuthentication
	@RequestMapping(value= "/component/{deviceID}")
	public ResultMap getComponentList(@PathVariable("deviceID")String deviceID){
		ResultMap resultMap = new ResultMap();
		List<String> components = deviceAttributeService.getDeviceComponents(deviceID);
		if(components == null){
			resultMap.setStatus(ResultMap.STATUS_FAILURE);
			resultMap.setInfo(ResultMap.EMPTY_INFO);
		}else {
			resultMap.setStatus(ResultMap.STATUS_SUCCESS);
			resultMap.addContent("components", components);
		}
		return resultMap;
	}
}
