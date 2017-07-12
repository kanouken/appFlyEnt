package io.kanouken.admin.controller.resources;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.kanouken.admin.controller.BaseController;

@Controller
public class ResourcesController extends BaseController {

	@RequestMapping(value="resources/{resourceType}/{file:.+}",method=RequestMethod.GET)
	public ResponseEntity<byte[]> download(@PathVariable("file") String file,
			@PathVariable("resourceType") String resourceType) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		String targetFilePath = "";
		String[] filePath = file.split("-");
		file = Arrays.asList(filePath).stream().collect(Collectors.joining(File.separator));
		if ("plist".equals(resourceType)) {
			targetFilePath = config.getPlistFilePath();
		}
		if ("app".equals(resourceType)) {
			targetFilePath = config.getAppFilePath();
		}
		if ("img".equals(resourceType)) {
			targetFilePath = config.getIconPath();
		}
		String filename = file.substring(file.lastIndexOf(File.separator) + 1, file.length());
		headers.setContentDispositionFormData("attachment", filename);
//		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//	    headers.add("Pragma", "no-cache");
//	    headers.add("Expires", "0");
//	    headers.setContentLength(new File(targetFilePath + File.separator + file).length());
		return new ResponseEntity<byte[]>(
				FileCopyUtils.copyToByteArray(new File(targetFilePath + File.separator + file)), headers,
				HttpStatus.OK);
	}

}
