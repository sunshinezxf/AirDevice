package menu;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class MenuConfig {
	private final static String wechat_appid = "wxe8ca0dc55e5f81fd";
	private final static String wechat_secret = "f059ce8503d18423654d6c1d7a8129e2";

	/**
	 * 调用此方法需传入appid和secret，现默认不使用该方法获取
	 *
	 * @param appId
	 * @param secret
	 * @return
	 */
	public static String queryAccessToken(String appId, String secret) {
		String result = "";
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret="
				+ secret;
		try {
			URL address = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) address.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
			System.setProperty("sun.net.client.defaultReadTimeout", "30000");
			connection.connect();
			InputStream is = connection.getInputStream();
			int size = is.available();
			byte[] bytes = new byte[size];
			is.read(bytes);
			String message = new String(bytes, "UTF-8");
			JSONObject object = JSON.parseObject(message);
			result = object.getString("access_token");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String createMenu(String token) {
		//设备块
		JSONObject mine_device = new JSONObject();
		mine_device.put("name", "我的设备");
		mine_device.put("type", "view");
		try {
			mine_device.put("url", "http://commander.qingair.net/reception/www/index.html#/home/device");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject new_device = new JSONObject();
		new_device.put("name", "发现设备");
		new_device.put("type", "scancode_push");
		new_device.put("key", "discover");
		
		JSONArray device_button = new JSONArray();
		device_button.add(mine_device);
		device_button.add(new_device);
		
		JSONObject deivce = new JSONObject();
		deivce.put("name", "设备");
		deivce.put("sub_button", device_button);
		
		//会员块
		JSONObject vip_bonus = new JSONObject();
		vip_bonus.put("name", "积分商城");
		vip_bonus.put("type", "view");
		try {
			vip_bonus.put("url", "http://commander.qingair.net/reception/www/templates/bonus.html");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject vip_code = new JSONObject();
		vip_code.put("name", "优惠码");
		vip_code.put("type", "view");
		try {
			vip_code.put("url", "http://commander.qingair.net/reception/www/templates/coupon-share.html");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONArray vip_button = new JSONArray();
		vip_button.add(vip_bonus);
		vip_button.add(vip_code);
		
		JSONObject vip = new JSONObject();
		vip.put("name", "我的");
		vip.put("sub_button", vip_button);
		
	
		//关于产品
		JSONObject consumer_case = new JSONObject();
		consumer_case.put("name", "用户案例");
		consumer_case.put("type", "view");
		try {
			consumer_case.put("url", "http://commander.qingair.net/reception/www/index.html#/home/device");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject gm_recognize = new JSONObject();
		gm_recognize.put("name", "认识果麦");
		gm_recognize.put("type", "view");
		try {
			gm_recognize.put("url", "http://commander.qingair.net/reception/www/index.html#/home/device");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject product_guide = new JSONObject();
		product_guide.put("name", "新风指南");
		product_guide.put("type", "view");
		try {
			product_guide.put("url", "http://commander.qingair.net/reception/www/index.html#/home/device");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject product_effect = new JSONObject();
		product_effect.put("name", "净化效果");
		product_effect.put("type", "view");
		try {
			product_effect.put("url", "http://commander.qingair.net/reception/www/index.html#/home/device");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONArray prod_button = new JSONArray();
		prod_button.add(consumer_case);
		prod_button.add(gm_recognize);
		prod_button.add(product_guide);
		prod_button.add(product_effect);
		
		JSONObject prod = new JSONObject();
		prod.put("name", "关于产品");
		prod.put("sub_button", prod_button);
		

		
		JSONArray buttons = new JSONArray();
		buttons.add(deivce);
		buttons.add(vip);
		buttons.add(prod);
		JSONObject menu = new JSONObject();
		menu.put("button", buttons);
		System.out.println(JSON.toJSONString(menu));
		String link = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + token;
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            connection.connect();
            OutputStream os = connection.getOutputStream();
            os.write(menu.toString().getBytes());
            os.flush();
            os.close();

            InputStream is = connection.getInputStream();
            int size = is.available();
            byte[] bytes = new byte[size];
            is.read(bytes);
            String message = new String(bytes, "UTF-8");
            return "返回信息" + message;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "创建菜单失败";
		
		
	}
	
	   public static String deleteMenu(String token) {
	        String link = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + token;
	        try {
	            URL url = new URL(link);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("GET");
	            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	            connection.setDoOutput(true);
	            connection.setDoInput(true);
	            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
	            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
	            connection.connect();
	            OutputStream os = connection.getOutputStream();
	            os.flush();
	            os.close();
	            InputStream is = connection.getInputStream();
	            int size = is.available();
	            byte[] bytes = new byte[size];
	            is.read(bytes);
	            String message = new String(bytes, "UTF-8");
	            return "返回信息:" + message;
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return "删除菜单失败";
	    }
	
    public static void main(String[] args) {
        String token = "UkOlTh3bYzRpiApsRrjK8R5PnJgTtiG7fJNYMNDyribcf317dOuGWkFRMuIk460y4fpQ7O8VbURX8x_4irCR5ax_mU-ftYgMaBBI7puFfZ0Swci8bBLkJWuwp6L-6o68YVEjAHAURA";
        String deleteMessage = MenuConfig.deleteMenu(token);
        System.out.println("删除操作: " + deleteMessage);
        String createMessage = MenuConfig.createMenu(token);
        System.out.println("创建操作" + createMessage);
    }
}
