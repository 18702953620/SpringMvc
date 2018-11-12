package com.test.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.mapper.ArticleDao;
import com.test.model.ArticleModel;
import com.test.model.BaseModel;
import com.test.model.UserModel;
import com.test.utils.DateUtils;
import com.test.utils.Global;
import com.test.utils.TextUtils;

@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {
	@Autowired
	private ArticleDao articleDao;

	@RequestMapping("")
	public String hello() {
		return "article";
	}

	@ResponseBody
	@RequestMapping("/getArticle")
	public BaseModel getArticle(String articleId) {
		List<ArticleModel> models = articleDao.getArticle(articleId);
		if (models == null || models.size() == 0) {
			return makeModel(ERROR_CODE, "文章不存在");
		}
		return makeModel(SUCC_CODE, MSG_SUCC, models);

	}

	@ResponseBody
	@RequestMapping("/addArticle")
	public BaseModel addArticle(HttpSession session, ArticleModel model) {

		if (!isLogin(session)) {
			return makeModel(NO_LOGIN, MSG_NO_LOGIN);
		}
		if (model == null) {
			return makeModel(ERROR_CODE, "文章不能为空");
		} else {
			if (model.getTitle() == null || model.getLink() == null) {
				return makeModel(ERROR_CODE, "文章或者链接不能为空");
			}

			UserModel userModel = (UserModel) session.getAttribute(Global.USER_SESSION_KEY);
			if (userModel != null) {
				model.setCreateUser(userModel.getUserId());
			}
			model.setCreateTime(DateUtils.getDate());
			int code = articleDao.add(model);
			if (code == 0) {
				return makeModel(code, MSG_ADD_ERROR);
			} else {
				return makeModel(code, MSG_ADD_SUCC);
			}

		}
	}

	@ResponseBody
	@RequestMapping("/updateArticle")
	public BaseModel updateArticle(HttpSession session, ArticleModel model) {
		if (!isLogin(session)) {
			return makeModel(NO_LOGIN, MSG_NO_LOGIN);
		}

		if (model == null) {
			return makeModel(ERROR_CODE, "文章不能为空");
		} else {
			int code = articleDao.update(model);
			if (code == 0) {
				return makeModel(ERROR_CODE, MSG_UPDATE_ERROR);
			} else {
				return makeModel(SUCC_CODE, MSG_UPDATE_SUCC);
			}

		}
	}

	@ResponseBody
	@RequestMapping("/deleteArticle")
	public BaseModel deleteArticle(HttpSession session, String articleId) {

		if (!isLogin(session)) {
			return makeModel(NO_LOGIN, MSG_NO_LOGIN);
		}

		if (TextUtils.isEmpty(articleId)) {
			return makeModel(ERROR_CODE, "文章id 不能为空");
		} else {
			int code = articleDao.delete(articleId);
			if (code == 0) {
				return makeModel(code, MSG_DELETE_ERROR);
			} else {
				return makeModel(code, MSG_DELETE_SUCC);
			}

		}
	}

}
