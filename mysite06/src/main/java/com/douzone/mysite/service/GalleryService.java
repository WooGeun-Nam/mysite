package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GalleryRepository;
import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	@Autowired
	private GalleryRepository galleryRepository;
	
	public List<GalleryVo> getImages() {
		return galleryRepository.findImages();
	}
	
	public void removeImage(Long no) {
		galleryRepository.deleteImage(no);
	}
	
	public void addImage(GalleryVo vo) {
		galleryRepository.insertImage(vo);
	}
}
