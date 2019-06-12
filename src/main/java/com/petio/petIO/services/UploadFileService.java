package com.petio.petIO.services;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {

	@Value("${file.host}")
	private String host;
	@Value("${file.port}")
	private String port;

	@Value("${file.imagePath}")
	private String imagePath;

	private String http = "http://";

	public String getUploadFilePath(MultipartFile file, String fileName) {
		if (file.isEmpty()) {
			throw new NullPointerException("文件为空");
		}
		String suffix = file.getOriginalFilename();
		String prefix = suffix.substring(suffix.lastIndexOf(".") + 1);
		fileName += ('.' + prefix);
		try {
			File path = new File(ResourceUtils.getURL("classpath:").getPath());
			if (!path.exists()) {
				path = new File("");
			}
			System.out.println("path:" + path.getAbsolutePath());
			File upload = new File(path.getAbsolutePath(), "/home/img/");
			if (!upload.exists()) {
				upload.mkdirs();
			}
			System.out.println("upload url:" + upload.getAbsolutePath());
			File dest = new File(upload.getAbsolutePath() + '/' + fileName);
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();
			}
			file.transferTo(dest);
			String filePathNew = dest.getPath();
			System.out.println("save path:" + filePathNew);
			System.out.println(saveUploadFile(fileName));
			return saveUploadFile(fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error : " + e.getMessage());
			return null;
		}
	}

	private String saveUploadFile(String filePathNew) {
		String localhost = null;
		try {
			localhost = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.out.println("get server host Exception e:" + e.getMessage());
		}
		System.out.println(localhost + ": insert record=" + 1 + ",file=" + filePathNew);
		return http + host + ':' + port + imagePath + filePathNew;
	}
}
