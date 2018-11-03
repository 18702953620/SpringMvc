package com.test.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.mapper.UserDao;
import com.test.model.BaseModel;
import com.test.model.UserModel;
import com.test.utils.DateUtils;
import com.test.utils.Global;
import com.test.utils.TextUtils;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	UserDao userdao;

	/**
	 * ����json
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addUser")
	public BaseModel addUser(HttpSession session, UserModel user) {
		if (user == null) {
			return makeModel(ERROR_CODE, "�û�����Ϊ��");
		} else {
			if (user.getUsername() == null || user.getPassword() == null) {
				return makeModel(ERROR_CODE, "�û����������벻��Ϊ��");
			}

			if (userdao.getUserByName(user.getUsername()) == null) {
				user.setCreateTime(DateUtils.getDate());
				int code = userdao.add(user);
				if (code == 0) {
					return makeModel(code, "���ʧ��");
				} else {
					return makeModel(code, "��ӳɹ�");
				}
			} else {
				return makeModel(ERROR_CODE, "�û��Ѵ���");
			}

		}
	}

	@ResponseBody
	@RequestMapping("/updateUser")
	public BaseModel updateUser(HttpSession session, UserModel user) {
		if (!isLogin(session)) {
			return makeModel(NO_LOGIN, "�û�δ��¼�����ȵ�¼");
		}

		if (user == null) {
			return makeModel(ERROR_CODE, "�û�����Ϊ��");
		} else {

			if (userdao.getUserById(user.getId()) == null) {
				return makeModel(ERROR_CODE, "�û�������");
			} else {
				user.setEditTime(DateUtils.getDate());
				int code = userdao.update(user);
				if (code == 0) {
					return makeModel(ERROR_CODE, "����ʧ��");
				} else {
					return makeModel(SUCC_CODE, "���³ɹ�");
				}
			}

		}
	}

	@ResponseBody
	@RequestMapping("/getUser")
	public BaseModel getUser(HttpSession session, String id) {
		if (!isLogin(session)) {
			return makeModel(NO_LOGIN, "�û�δ��¼�����ȵ�¼");
		}

		if (TextUtils.isEmpty(id)) {
			return makeModel(SUCC_CODE, SUCC_MSG, userdao.getAllUsers());
		} else {
			UserModel model = userdao.getUserById(id);
			if (model == null) {
				return makeModel(ERROR_CODE, "�û�������");
			}
			return makeModel(SUCC_CODE, SUCC_MSG, model);
		}
	}

	@ResponseBody
	@RequestMapping("/deleteUser")
	public BaseModel deleteUser(HttpSession session, String id) {
		
		if (!isLogin(session)) {
			return makeModel(NO_LOGIN, "�û�δ��¼�����ȵ�¼");
		}
		
		if (TextUtils.isEmpty(id)) {
			return makeModel(ERROR_CODE, "�û�id����Ϊ��");
		} else {
			int code = userdao.delete(id);
			if (code == 0) {
				return makeModel(code, "ɾ��ʧ��");
			} else {
				return makeModel(code, "ɾ���ɹ�");
			}

		}
	}

	@ResponseBody
	@RequestMapping("/login")
	public BaseModel login(HttpSession session, String username, String password) {
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			return makeModel(ERROR_CODE, "�û��������벻��Ϊ��");
		} else {

			UserModel userModel = userdao.getUserByName(username);
			if (userModel == null) {
				return makeModel(ERROR_CODE, "�û�������");
			}
			if (userModel.getPassword().equals(password)) {
				session.setAttribute(Global.USER_SESSION_KEY, userModel);

				System.out.println("user login=" + session.getAttribute(Global.USER_SESSION_KEY));
				return makeModel(SUCC_CODE, SUCC_MSG, userModel);
			} else {
				return makeModel(ERROR_CODE, "���벻��ȷ");
			}

		}
	}

	@ResponseBody
	@RequestMapping("/loginout")
	public BaseModel loginout(HttpSession session) {
		if (session.getAttribute(Global.USER_SESSION_KEY) == null) {
			return makeModel(ERROR_CODE, "�˳�ʧ�ܣ�δ��¼");
		} else {
			session.removeAttribute(Global.USER_SESSION_KEY);
			return makeModel(SUCC_CODE, "�˳��ɹ�");
		}
	}

}
