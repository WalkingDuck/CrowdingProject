package com.wtw.crowd.util;

/**
 * 服务器响应的规范类
 */
public class ResultEntity<T> {

	public static final String SUCCESS = "SUCCESS";
	public static final String FAILED = "FAILED";
	
	// 用来表示当前请求的结果是成功还是失败
	private String Result;

	// 请求处理时的错误信息
	private String message;

	// 要返回的数据
	private T data;

	public ResultEntity() {
		super();
	}

	public ResultEntity(String result, String message, T data) {
		super();
		Result = result;
		this.message = message;
		this.data = data;
	}

	//请求成功且不需要返回数据
	public static <E> ResultEntity<E> successWithoutData() {
		return new ResultEntity<E>(SUCCESS, null, null);
	}
	
	public static <E> ResultEntity<E> successWithData(E data) {
		return new ResultEntity<E>(SUCCESS, null, data);
	}

	public static <E> ResultEntity<E> failed(String message) {
		return new ResultEntity<E>(FAILED, message, null);
	}

	public String getResult() {
		return Result;
	}

	public void setResult(String result) {
		Result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResultEntity [Result=" + Result + ", message=" + message + ", data=" + data + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Result == null) ? 0 : Result.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResultEntity other = (ResultEntity) obj;
		if (Result == null) {
			if (other.Result != null)
				return false;
		} else if (!Result.equals(other.Result))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}

}
