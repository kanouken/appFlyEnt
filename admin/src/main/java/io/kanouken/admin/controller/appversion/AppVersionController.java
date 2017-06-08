package io.kanouken.admin.controller.appversion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.kanouken.admin.controller.BaseController;
import io.kanouken.admin.model.app.vo.AppDetailVo;
import io.kanouken.admin.model.appversion.vo.CurrentVersionVo;
import io.kanouken.admin.model.appversion.vo.VersionAddVo;
import io.kanouken.admin.model.appversion.vo.VersionUpdateVo;
import io.kanouken.admin.service.appversion.AppVersionService;

@RestController
@SuppressWarnings("all")
public class AppVersionController extends BaseController {
	@Autowired
	AppVersionService versionService;

	@PostMapping("app/{appId}version")
	public void addVersion(@PathVariable("appId") Integer appId, @RequestBody VersionAddVo versionAddVo) {
		versionAddVo.setAppId(appId);
		this.versionService.addVersion(versionAddVo, currentAccount);
	}

	@PutMapping("app/{appId}/version")
	public void updateVersion(@PathVariable("appId") Integer appId, @RequestBody VersionUpdateVo versionUpdateVo) {
		versionUpdateVo.setAppId(appId);
		versionService.updateVersion(versionUpdateVo, currentAccount);
	}

	@GetMapping("/app/version/current")
	public CurrentVersionVo detail(@RequestParam(value = "plat") String plat, @RequestParam(value = "appId") String appId) {
		return versionService.getCurrentVersion(plat,appId);
	}

}
