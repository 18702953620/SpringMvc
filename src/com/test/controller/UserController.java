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
	 * 返回json
	 * 
	 * @return
	 */
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
				user.setCreateTime(DateUtils.getDate());
				int code = userdao.add(user);
				if (code == 0) {
					return makeModel(code, "添加失败");
				} else {
					return makeModel(code, "添加成功");
				}
			} else {
				return makeModel(ERROR_CODE, "用户已存在");
			}

		}
	}

	@ResponseBody
	@RequestMapping("/updateUser")
	public BaseModel updateUser(HttpSession session, UserModel user) {
		if (!isLogin(session)) {
			return makeModel(NO_LOGIN, "用户未登录，请先登录");
		}

		if (user == null) {
			return makeModel(ERROR_CODE, "用户不能为空");
		} else {

			if (userdao.getUserById(user.getId()) == null) {
				return makeModel(ERROR_CODE, "用户不存在");
			} else {
				user.setEditTime(DateUtils.getDate());
				int code = userdao.update(user);
				if (code == 0) {
					return makeModel(ERROR_CODE, "更新失败");
				} else {
					return makeModel(SUCC_CODE, "更新成功");
				}
			}

		}
	}

	@ResponseBody
	@RequestMapping("/getUser")
	public BaseModel getUser(HttpSession session, String id) {
		if (!isLogin(session)) {
			return makeModel(NO_LOGIN, "用户未登录，请先登录");
		}

		if (TextUtils.isEmpty(id)) {
			return makeModel(SUCC_CODE, SUCC_MSG, userdao.getAllUsers());
		} else {
			UserModel model = userdao.getUserById(id);
			if (model == null) {
				return makeModel(ERROR_CODE, "用户不存在");
			}
			return makeModel(SUCC_CODE, SUCC_MSG, model);
		}
	}

	@ResponseBody
	@RequestMapping("/deleteUser")
	public BaseModel deleteUser(HttpSession session, String id) {
		
		if (!isLogin(session)) {
			return makeModel(NO_LOGIN, "用户未登录，请先登录");
		}
		
		if (TextUtils.isEmpty(id)) {
			return makeModel(ERROR_CODE, "用户id不能为空");
		} else {
			int code = userdao.delete(id);
			if (code == 0) {
				return makeModel(code, "删除失败");
			} else {
				return makeModel(code, "删除成功");
			}

		}
	}

	@ResponseBody
	@RequestMapping("/login")
	public BaseModel login(HttpSession session, String username, String password) {
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			return makeModel(ERROR_CODE, "用户名或密码不能为空");
		} else {

			UserModel userModel = userdao.getUserByName(username);
			if (userModel == null) {
				return makeModel(ERROR_CODE, "用户不存在");
			}
			if (userModel.getPassword().equals(password)) {
				session.setAttribute(Global.USER_SESSION_KEY, userModel);

				System.out.println("user login=" + session.getAttribute(Global.USER_SESSION_KEY));
				return makeModel(SUCC_CODE, SUCC_MSG, userModel);
			} else {
				return makeModel(ERROR_CODE, "密码不正确");
			}

		}
	}

	@ResponseBody
	@RequestMapping("/loginout")
	public BaseModel loginout(HttpSession session) {
		if (session.getAttribute(Global.USER_SESSION_KEY) == null) {
			return makeModel(ERROR_CODE, "退出失败，未登录");
		} else {
			session.removeAttribute(Global.USER_SESSION_KEY);
			return makeModel(SUCC_CODE, "退出成功");
		}
	}

}
