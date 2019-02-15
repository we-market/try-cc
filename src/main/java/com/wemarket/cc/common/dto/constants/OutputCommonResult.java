package com.wemarket.cc.common.dto.constants;

/**
 * description:通用输出返回值
 *
 * @author lynndu
 * @version 1.0
 *
 */
public enum OutputCommonResult {
	/**
	 * 正常
	 */
	NORMAL(0),
	/**
	 * 参数错误
	 */
	WRONGPARAM(1),
	/**
	 * 系统错误
	 */
	ERROR(2),
	/**
	 * 拒绝操作
	 */
	MODIFYREFUSED(3),
	/**
	 * 数据重复错误
	 */
	DATAREDUP(11),
	/**
	 * 数据找不到
	 */
	DATANOTEXIST(12);

	private int result;

	private OutputCommonResult(int result) {
		this.result = result;
	}

	public int getResult() {
		return result;
	}
}
