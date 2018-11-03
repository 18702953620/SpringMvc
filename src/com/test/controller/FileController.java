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
	 * �ļ��ϴ�
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
			return makeModel(ERROR_CODE, "�ļ�������");
		}

		String fileName = file.getOriginalFilename();
		File targetFile = new File("D:/ch", TextUtils.getTemFileName(fileName));
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// ����
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return makeModel(SUCC_CODE, "�ϴ��ɹ�");

	}

	/**
	 * �ļ��ϴ�1
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/uploadspring")
	public BaseModel uploadspring(HttpServletRequest request) {
		// ����ǰ�����ĳ�ʼ���� CommonsMutipartResolver (�ಿ�ֽ�����)
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// ���form���Ƿ���enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// ��request��ɶ�request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// ��ȡmultiRequest�����е��ļ���
			Iterator<?> iter = multiRequest.getFileNames();

			while (iter.hasNext()) {
				// һ�α������е��ļ�
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					// �ϴ�
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

					return makeModel(SUCC_CODE, "�ϴ��ɹ�");
				}

				return makeModel(ERROR_CODE, "�ļ�������");
			}
			return makeModel(ERROR_CODE, "�ļ�������");
		} else {
			return makeModel(ERROR_CODE, "�ļ�������");
		}

	}

}
