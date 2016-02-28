package org.dunquan.framework.util;

import com.google.gson.Gson;

public class JsonUtil {

	private static final Gson gson = new Gson();
	
	public static <T> String toJSON(T data) {
		String json = gson.toJson(data);
		
		return json;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T fromJSON(String json, Class<?> clzz) {
		T t = (T) gson.fromJson(json, clzz);
		
		return t;
	}
}
