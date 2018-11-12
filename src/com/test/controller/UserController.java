package com.test.controller;

import java.util.List;

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
import com.test.utils.MD5Util;
import com.test.utils.TextUtils;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	UserDao userdao;

	@ResponseBody
	@RequestMapping("/addUser")
	public BaseModel addUser(HttpSession session, UserModel user) {
		if (user == null) {
			return makeModel(ERROR_CODE, "用户不能为空");
		} else {
			if (user.getUsername() == null || user.getPassword() == null) {
				return makeModel(ERROR_CODE, "用户名或者密码不能为空");
			}

			if (userdao.getUserByName(user.getUsername()) == null) {
				user.setEditTime(DateUtils.getDate());
				user.setPassword(MD5Util.encode(user.getPassword()));
				int code = userdao.add(user);
				if (code == 0) {
					return makeModel(code, MSG_ADD_ERROR);
				} else {
					return makeModel(code, MSG_ADD_SUCC);
				}
			} else {
				return makeModel(ERROR_CODE, "用户名已存在");
			}

		}
	}

	@ResponseBody
	@RequestMapping("/updateUser")
	public BaseModel updateUser(HttpSession session, UserModel user) {
		if (!isLogin(session)) {
			return makeModel(NO_LOGIN, MSG_NO_LOGIN);
		}

		if (user == null) {
			return makeModel(ERROR_CODE, "用户不能为空");
		} else {
			List<UserModel> models = userdao.getUser(user.getUserId());
			if (models == null || models.size() == 0) {
				return makeModel(ERROR_CODE, "用户不存在");
			} else {
				user.setEditTime(DateUtils.getDate());
				if (TextUtils.isEmpty(user.getPassword())) {
					user.setPassword(MD5Util.encode(user.getPassword()));
				}
				int code = userdao.update(user);
				if (code == 0) {
					return makeModel(ERROR_CODE, MSG_UPDATE_ERROR);
				} else {
					return makeModel(SUCC_CODE, MSG_UPDATE_SUCC);
				}
			}

		}
	}

	@ResponseBody
	@RequestMapping("/getUser")
	public BaseModel getUser(HttpSession session, String userId) {
		if (!isLogin(session)) {
			return makeModel(NO_LOGIN, MSG_NO_LOGIN);
		}
		List<UserModel> models = userdao.getUser(userId);
		if (models == null || models.size() == 0) {
			return makeModel(ERROR_CODE, "用户不存在");
		}
		return makeModel(SUCC_CODE, MSG_SUCC, models);
	}

	@ResponseBody
	@RequestMapping("/deleteUser")
	public BaseModel deleteUser(HttpSession session, String userId) {

		if (!isLogin(session)) {
			return makeModel(NO_LOGIN, MSG_NO_LOGIN);
		}

		if (TextUtils.isEmpty(userId)) {
			return makeModel(ERROR_CODE, "用户id 不能为空");
		} else {
			int code = userdao.delete(userId);
			if (code == 0) {
				return makeModel(code, MSG_DELETE_ERROR);
			} else {
				return makeModel(code, MSG_DELETE_SUCC);
			}

		}
	}

	@ResponseBody
	@RequestMapping("/login")
	public BaseModel login(HttpSession session, String username, String password) {
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			return makeModel(ERROR_CODE, "用户名和密码不能为空");
		} else {

			UserModel userModel = userdao.getUserByName(username);
			if (userModel == null) {
				return makeModel(ERROR_CODE, "用户不存在");
			}
			if (userModel.getPassword().equals(MD5Util.encode(password))) {
				session.setAttribute(Global.USER_SESSION_KEY, userModel);

				return makeModel(SUCC_CODE, MSG_SUCC, userModel);
			} else {
				return makeModel(ERROR_CODE, "用户名或者密码不正确");
			}

		}
	}

	@ResponseBody
	@RequestMapping("/loginout")
	public BaseModel loginout(HttpSession session) {
		if (session.getAttribute(Global.USER_SESSION_KEY) == null) {
			return makeModel(NO_LOGIN, MSG_NO_LOGIN);
		} else {
			session.removeAttribute(Global.USER_SESSION_KEY);
			return makeModel(SUCC_CODE, "退出成功");
		}
	}

}
