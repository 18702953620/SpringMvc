package com.test.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.test.model.BaseModel;
import com.test.utils.TextUtils;

/**
 * �ļ����
 * 
 * @author ch
 *
 */
@Controller
@RequestMapping("/file")
public class FileController extends BaseController {

	@RequestMapping("")
	public String index() {
		return "upload";
	}

	/**
	 * 单个文件
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upload")
	public BaseModel upload(@RequestParam(value = "file_03", required = false) MultipartFile file,
			HttpServletRequest request) {
		if (file == null || file.isEmpty()) {
			return makeModel(ERROR_CODE, "文件不存在");
		}

		String fileName = file.getOriginalFilename();
		File targetFile = new File("D:/ch", TextUtils.getTemFileName(fileName));
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return makeModel(SUCC_CODE, "上传成功");

	}

	/**
	 * 多个文件
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/uploadspring")
	public BaseModel uploadspring(HttpServletRequest request) {
		// CommonsMutipartResolver
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 遍历
			Iterator<?> iter = multiRequest.getFileNames();

			while (iter.hasNext()) {
				//
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					//
					File targetFile = new File("D:/ch", TextUtils.getTemFileName(file.getOriginalFilename()));
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					try {
						file.transferTo(targetFile);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					return makeModel(SUCC_CODE, "上传成功");
				}

				return makeModel(ERROR_CODE, "文件不存在");
			}
			return makeModel(ERROR_CODE, "文件不存在");
		} else {
			return makeModel(ERROR_CODE, "文件不存在");
		}

	}

}
