package io.kanouken.admin.controller.app;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.kanouken.admin.controller.BaseController;
import io.kanouken.admin.model.app.dto.AppDownloadListDto;
import io.kanouken.admin.model.app.vo.AppAddVo;
import io.kanouken.admin.model.app.vo.AppDetailVo;
import io.kanouken.admin.model.app.vo.AppUpdateVo;
import io.kanouken.admin.service.app.AppService;

@RestController
@SuppressWarnings("all")
public class ApplicationController extends BaseController {

	@Autowired
	private AppService appService;

	@PostMapping("app")
	public AppAddVo addApp(AppAddVo appAddVo, MultipartFile icon) throws UnsupportedEncodingException {
		return appService.addApp(appAddVo, icon, currentAccount);
	}

	@PutMapping("app/{appId}")
	public void updateApp(@PathVariable("appId") Integer appId, @RequestBody AppUpdateVo appUpdateVo)
			throws UnsupportedEncodingException {
		appUpdateVo.setId(appId);
		appService.updateApp(appUpdateVo, null, currentAccount);
	}

	@GetMapping("/apps")
	public Map<String, Object> apps() {
		return renderDt(appService.queryApps());
	}

	@GetMapping("{customoerPrefix}/{plat}/apps")
	public List<AppDownloadListDto> downloadApps(@PathVariable("customoerPrefix") String prefix, @PathVariable("plat") String plat) {
		return appService.queryCustomerApps(prefix,plat);
	}

	@DeleteMapping("/app/{appId}")
	public void deleteApp(@PathVariable("appId") Integer appId) {
		appService.deleteApp(appId, currentAccount);
	}

	@GetMapping("/app/{appId}")
	public AppDetailVo detail(@PathVariable("appId") Integer appId) {
		return appService.getDetail(appId);
	}

}
