package io.kanouken.admin.controller.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.kanouken.admin.controller.BaseController;

/**
 * 
 * @author xnq
 *
 */
@Controller
public class DownloadController extends BaseController {

	
	@GetMapping("customer/{prefix}/app")
	public String downloadPage(){
		return "download";
	}
}
