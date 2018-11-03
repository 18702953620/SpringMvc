package com.test.controller;

import javax.servlet.http.HttpSession;

import com.test.model.BaseModel;
import com.test.utils.Global;

public class BaseController {

	public static final String SUCC_MSG = "����ɹ�";
	public static final String ERROR_MSG = "����ʧ��";
	/**
	 * �ɹ�
	 */
	public static final int SUCC_CODE = 1;
	/**
	 * ʧ��
	 */
	public static final int ERROR_CODE = 0;
	/**
	 * ��¼ʧЧ
	 */
	public static final int NO_LOGIN = 101;

	/**
	 * 
	 * @param code
	 * @return
	 */
	public BaseModel makeModel(int code) {
		BaseModel model = new BaseModel();
		if (code == SUCC_CODE) {
			model.setMsg(SUCC_MSG);
		} else if (code == ERROR_CODE) {
			model.setMsg(ERROR_MSG);
		}
		model.setCode(code);
		return model;
	}

	/**
	 * 
	 * @param code
	 * @param msg
	 * @return
	 */
	public BaseModel makeModel(int code, String msg) {
		BaseModel model = new BaseModel();
		model.setCode(code);
		model.setMsg(msg);
		return model;
	}

	/**
	 * 
	 * @param code
	 * @param msg
	 * @param data
	 * @return
	 */
	public BaseModel makeModel(int code, String msg, Object data) {
		BaseModel model = new BaseModel();
		model.setCode(code);
		model.setData(data);
		model.setMsg(msg);
		return model;
	}

	/**
	 * �Ƿ��¼
	 * 
	 * @param session
	 * @return
	 */
	public boolean isLogin(HttpSession session) {
		if (session == null) {
			return false;
		}

		if (session.getAttribute(Global.USER_SESSION_KEY) != null) {
			return true;
		}

		return false;
	}

}
