package io.kanouken.admin.controller.resources;

import java.io.File;
import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import io.kanouken.admin.controller.BaseController;

@Controller
public class ResourcesController extends BaseController {

	@GetMapping("resources/{resourceType}")
	public ResponseEntity<byte[]> download(@RequestParam("file") String file,
			@PathVariable("resourceType") String resourceType) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		String targetFilePath = "";
		if ("plist".equals(resourceType)) {
			targetFilePath = config.getPlistFilePath();
		}
		if ("app".equals(resourceType)) {
			targetFilePath = config.getAppFilePath();
		}
		if ("img".equals(resourceType)) {
			targetFilePath = config.getIconPath();
		}
		String filename = file.substring(file.lastIndexOf(File.separator)+1, file.length());
		headers.setContentDispositionFormData("attachment", 
				filename);  
		
		return new ResponseEntity<byte[]>(
				FileCopyUtils.copyToByteArray(new File(targetFilePath + File.separator + file)), headers,
				HttpStatus.CREATED);
	}

}
