package com.chuangqi.tool.util;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 响应结构
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultUtil<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	// 响应业务状态
	private Integer code;

	// 响应消息
	private String msg;

	// 响应中的数据
	private T data;

	public ResultUtil(T data) {
		this.code = 200;
		this.msg = "ok";
		this.data = data;
	}

	public ResultUtil(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
		this.data = null;
	}

	public ResultUtil(Integer code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	/**
	 * ok
	 * 
	 * @return code=200, msg=ok
	 */
	public static <T> ResultUtil<T> ok() {
		return new ResultUtil<T>(200, "ok");
	}

	/**
	 * 正常返回数据
	 * 
	 * @param data
	 *            数据
	 * @return code=200, msg=ok, data=data
	 */
	public static <T> ResultUtil<T> ok(T data) {
		return new ResultUtil<T>(data);
	}
	
	/**
	 * 正常返回数据
	 * 
	 * @param code
	 * 			 	状态码
	 * @param data
	 *          	数据
	 * @return code=code, data=data
	 */
	public static <T> ResultUtil<T> ok(Integer code, T data) {
		return new ResultUtil<T>(code, null, data);
	}

	/**
	 * 自定义返回结果
	 * 
	 * @param code
	 *            状态码
	 * @param msg
	 *            相应消息
	 * @param data
	 *            相应具体内容
	 * 
	 * @return code=code, msg=msg, data=data
	 */
	public static <T> ResultUtil<T> result(Integer code, String msg, T data) {
		return new ResultUtil<T>(code, msg, data);
	}

	/**
	 * 返回500
	 * 
	 * @param msg
	 *            相应消息
	 * @return code=500
	 */
	public static <T> ResultUtil<T> error() {
		return new ResultUtil<T>(500, null, null);
	}

	/**
	 * 自定义错误状态
	 * 
	 * @param code
	 *            状态码
	 * @param msg
	 *            错误信息
	 * @return
	 */
	public static <T> ResultUtil<T> error(Integer code, String msg) {
		return new ResultUtil<T>(code, msg);
	}

	public Integer getcode() {
		return code;
	}

	public void setcode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResultUtil [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}

}