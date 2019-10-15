package org.dragonli.tools.general;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author threenoodles
 *
 */
public class JSONUtil {

	private static Logger logger = Logger.getLogger(JSONUtil.class);

	private static JSONObject SUCCESS_JSONOBJECT = initSuccessJson();
	public static final JSONObject EMPTY_JSONOBJECT = new JSONObject();


	@SuppressWarnings("unchecked")
	public static <T> List<T> parseList(String content, Class<T> clazz) {
		if (StringUtils.isBlank(content)) {
			return Collections.emptyList();
		}
		List<T> results = null;
		try {
			results = JSON.parseArray(content, clazz);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return (results == null ? Collections.EMPTY_LIST : results);
	}

	public static <T> T parse(String content, Class<T> clazz) {
		if (StringUtils.isBlank(content)) {
			return null;
		}
		T result = null;
		try {
			result = JSON.parseObject(content, clazz);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	public static JSONObject parseObject(String content) {
		if (StringUtils.isBlank(content)) {
			return EMPTY_JSONOBJECT;
		}
		JSONObject result = null;
		try {
			result = JSON.parseObject(content);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result == null ? EMPTY_JSONOBJECT : result;
	}

	public static String toJSONString(Object object) {
		if (object == null) {
			return EMPTY_JSONOBJECT.toJSONString();
		}
		String result = null;
		try {
			result = JSON.toJSONString(object);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result == null ? EMPTY_JSONOBJECT.toJSONString() : result;
	}

	private static JSONObject initSuccessJson() {
		JSONObject successJson = new JSONObject();
		successJson.put("code", 0);
		successJson.put("msg", StringUtils.EMPTY);
		return successJson;
	}

	public static JSONObject getSuccessJson() {
		return SUCCESS_JSONOBJECT;
	}

	public static JSONObject getFailuerJson(int code, String msg) {
		JSONObject failureJson = new JSONObject();
		failureJson.put("code", code);
		failureJson.put("msg", msg);
		return failureJson;
	}
	
}
