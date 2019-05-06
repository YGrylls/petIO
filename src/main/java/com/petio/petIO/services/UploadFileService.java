package com.petio.petIO.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {
 

    public boolean getUploadFilePath(MultipartFile file, String fileName ) {
        if (file.isEmpty()) {
            throw new NullPointerException("文件为空");
        }
        String suffix = file.getOriginalFilename();
        String prefix = suffix.substring(suffix.lastIndexOf(".")+1);
        fileName+=prefix;
        try {
			File path = new File(ResourceUtils.getURL("classpath:").getPath());
			if (!path.exists()) {
				path = new File("");
			}
			System.out.println("path:"+path.getAbsolutePath());
			File upload = new File(path.getAbsolutePath(),"static/images/upload/");
			if (!upload.exists()) {
				upload.mkdirs();
			}
			System.out.println("upload url:"+upload.getAbsolutePath());
	        File dest = new File(upload.getAbsolutePath() +'/'+ fileName);
	        if (!dest.getParentFile().exists()) {
	            dest.getParentFile().mkdirs();
	        }
	        file.transferTo(dest);
			String filePathNew = dest.getPath();
			System.out.println(saveUploadFile(filePathNew));
			return true;
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error : "+e.getMessage());
			return false;
		}
    }
 
    private String saveUploadFile(String filePathNew) {
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        	System.out.println("get server host Exception e:"+e.getMessage());
        }
        return host+": insert record="+1+",file="+filePathNew;
    }
}