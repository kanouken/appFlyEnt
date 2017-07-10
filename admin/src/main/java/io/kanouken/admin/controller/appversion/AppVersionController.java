package io.kanouken.admin.controller.appversion;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.kanouken.admin.controller.BaseController;
import io.kanouken.admin.model.appversion.vo.CurrentVersionVo;
import io.kanouken.admin.model.appversion.vo.VersionAddVo;
import io.kanouken.admin.model.appversion.vo.VersionDetailVo;
import io.kanouken.admin.model.appversion.vo.VersionUpdateVo;
import io.kanouken.admin.service.appversion.AppVersionService;

@RestController
@SuppressWarnings("all")
public class AppVersionController extends BaseController {
	@Autowired
	AppVersionService versionService;

	
	
	@PostMapping("app/{id}/version")
	public void addVersion(MultipartFile appFile, @PathVariable("id") Integer appId, VersionAddVo versionAddVo) throws IOException {
		versionAddVo.setAppId(appId);
		this.versionService.addVersion(appFile,versionAddVo, currentAccount);
	}

	@PutMapping("app/{id}/version")
	public void updateVersion(@PathVariable("id") Integer appId, MultipartFile appFile,
			VersionUpdateVo versionUpdateVo) {
		versionUpdateVo.setAppId(appId);
		versionService.updateVersion(appFile,versionUpdateVo, currentAccount);
	}
	/**
	 * 客户端检更新版本
	 * @param plat
	 * @param appId
	 * @return
	 */
	@GetMapping("/app/version/current")
	public CurrentVersionVo currentVersion(@RequestParam(value = "plat") String plat,
			@RequestParam(value = "appId") String appId) {
		return versionService.getCurrentVersion(plat, appId);
	}

	@GetMapping("/app/{appId}/{plat}/version")
	public VersionDetailVo detail(@PathVariable("appId") Integer appId, @PathVariable("plat") String plat) {
		return versionService.getDetail(appId, plat);
	}

}
