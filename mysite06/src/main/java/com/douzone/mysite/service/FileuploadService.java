package com.douzone.mysite.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.exception.FileuploadServiceException;

@Service
@PropertySource("classpath:com/douzone/mysite/web/fileupload.properties")
public class FileuploadService {

	private final static String SAVE_PATH = "/Users/nam-woogeun/mysite-uploads/upload-images";
	private final static String URL_PATH = "/assets/upload-images";
	
	public String restore(MultipartFile file) {
		String url = null;

		try {
			File uploadDirectory = new File(SAVE_PATH);
			// File uploadDirectory = new File(uploadLocations);
			if (!uploadDirectory.exists()) {
				uploadDirectory.mkdirs();
			}

			if (file.isEmpty()) {
				return url;
			}

			String originFilename = file.getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf(".") + 1);
			String saveFilename = generateSaveFileName(extName);
			Long fileSize = file.getSize();

			System.out.println("#########" + originFilename);
			System.out.println("#########" + saveFilename);
			System.out.println("#########" + fileSize);

			byte[] data = file.getBytes();
			
			// OutputStream os = new FileOutputStream(env.getProperty("fileupload.uploadLocations") + "/" + saveFilename);
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFilename);
			os.write(data);
			os.close();

			// url = env.getProperty("fileupload.resourceUrl") + "/" + saveFilename;
			url = URL_PATH + "/" + saveFilename;
		} catch (IOException ex) {
			throw new FileuploadServiceException(ex.toString());
		}

		return url;

	}

	private String generateSaveFileName(String extName) {
		String filename = "";

		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);

		return filename;
	}

}
