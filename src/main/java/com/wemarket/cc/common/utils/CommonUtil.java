package com.wemarket.cc.common.utils;

import com.wemarket.cc.common.utils.page.PageInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * description:公用工具
 *
 * @author lynndu
 * @version 1.0
 *
 */
public class CommonUtil {
	/**
	 * 获取数据库创建时间格式
	 * 
	 * @return 数据库创建时间格式
	 */
	public static DateFormat getCreateTimeDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	public static DateFormat getCreateDateFormat(){
		return new SimpleDateFormat("yyyy-MM-dd");
	}
	
	/**
	 * 整理分页信息
	 * @param map 分页信息
	 * pageSize=-1时显示最多99999条记录
	 */
	public static void handlePageMap(Map<String, Object> map) {
		if (map.containsKey("pageSize") && (int) map.get("pageSize") == -1) {
			map.put(PageInfo.MAP_KEY, new PageInfo(1, 99999));	
		} else if (map.containsKey("currentPage") && map.containsKey("pageSize")) {
			map.put(PageInfo.MAP_KEY, new PageInfo((int) map.get("currentPage"), (int) map.get("pageSize")));	
		} else {
			map.put(PageInfo.MAP_KEY, new PageInfo());
		}
	}
	
	public static PageInfo getPageInfo (Map<String, Object> map) {
		if (map.containsKey(PageInfo.MAP_KEY)) {
			return (PageInfo) map.get(PageInfo.MAP_KEY);
		} else {
			return null;
		}
	}
	
	public static String getCurrentTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String dateStr = sdf.format(new Date(System.currentTimeMillis()));
		return dateStr;
	}
}
