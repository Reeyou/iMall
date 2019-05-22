package com.reeyou.imall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Reeyou
 * @data 2019/5/20 10:30
 */
public interface FileService {

	String upload(MultipartFile file, String path);
}
