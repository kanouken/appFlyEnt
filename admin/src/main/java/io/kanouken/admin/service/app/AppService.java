package io.kanouken.admin.service.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import io.kanouken.admin.dao.app.AppDao;
import io.kanouken.admin.dao.appversion.AppHistoryVersionDao;
import io.kanouken.admin.dao.appversion.AppVersionDao;
import io.kanouken.admin.model.app.App;
import io.kanouken.admin.model.app.CurrentVersion;
import io.kanouken.admin.model.app.HistoryVersion;
import io.kanouken.admin.model.app.dto.AppDownloadListDto;
import io.kanouken.admin.model.app.dto.AppListDto;
import io.kanouken.admin.model.app.mapper.AppEntityMapper;
import io.kanouken.admin.model.app.vo.AppAddVo;
import io.kanouken.admin.model.app.vo.AppDetailVo;
import io.kanouken.admin.model.app.vo.AppUpdateVo;
import io.kanouken.admin.model.appversion.mapper.AppVersionEntityMapper;
import io.kanouken.admin.model.user.dto.CurrentAccountDto;
import io.kanouken.admin.service.BaseService;

@Service
public class AppService extends BaseService {

	@Autowired
	private AppDao appdao;

	@Autowired
	AppVersionDao currentVersionDao;

	@Autowired
	AppHistoryVersionDao historyVersionDao;

	public AppAddVo addApp(AppAddVo appAddVo, MultipartFile icon, CurrentAccountDto currentAccount) {
		String _fileName = icon.getOriginalFilename();
		String type = _fileName.substring(icon.getOriginalFilename().lastIndexOf("."), _fileName.length());
		String destFile = this.config.getIconPath() + File.separator + appAddVo.getKeywords();
		File pPath = new File(destFile);
		if (!pPath.exists()) {
			pPath.mkdirs();
		}
		String fileName = UUID.randomUUID().hashCode() + type;
		destFile = destFile + File.separator + fileName;
		try {
			IOUtils.copy(icon.getInputStream(), new FileOutputStream(destFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		App app = new App();
		app.setName(appAddVo.getName());
		app.setDescription(appAddVo.getDescription());
		app.setKeywords(appAddVo.getKeywords());
		app.setIcon(app.getKeywords() + File.separator + fileName);
		app.setAppId(appAddVo.getAppId());
		app.setCustomerId(appAddVo.getCustomerId());
		app.setCustomerName(appAddVo.getCustomerName());
		app.setCustomerPrefix(appAddVo.getCustomerPrefix());
		appdao.save(app);
		return appAddVo;
	}

	public List<AppListDto> queryApps() {
		List<App> apps = this.appdao.findAllByOrderByCreateTimeDesc();
		List<AppListDto> applistDtos = AppEntityMapper.INSTANCE.appToAppListDto(apps);
		return applistDtos;

	}

	public void deleteApp(Integer appId, CurrentAccountDto currentAccount) {
		App app = new App();
		app.setId(appId);
		app.setIsDelete(Byte.parseByte("1"));
		appdao.save(app);
	}

	public void updateApp(AppUpdateVo appUpdateVo, MultipartFile icon, CurrentAccountDto currentAccount) {
		App app = AppEntityMapper.INSTANCE.appUpdateVoToApp(appUpdateVo);
		app.setUpdateId(currentAccount.getId());
		appdao.save(app);
	}

	public AppDetailVo getDetail(Integer appId) {
		App app = appdao.findOne(appId);
		List<CurrentVersion> cvs = currentVersionDao.findByAppId(app.getId());
		List<HistoryVersion> his = historyVersionDao.findByAppIdOrderByCreateTimeDesc(app.getId());
		AppDetailVo detailVo = null;
		detailVo = AppEntityMapper.INSTANCE.appToAppDetailVo(app);
		detailVo.setCurrentVersions(AppVersionEntityMapper.INSTANCE.currentVersionToCurrentVersionVo(cvs));
		detailVo.setHistoryVersions(AppVersionEntityMapper.INSTANCE.historyVersionToHistoryVersionVo(his));
		return detailVo;
	}

	@Transactional(readOnly = true)
	public List<AppDownloadListDto> queryCustomerApps(String prefix, String plat) {
		List<App> apps = appdao.findByCustomerPrefix(prefix);
		if (CollectionUtils.isEmpty(apps)) {
			throw new IllegalAddException("error customer prefix" + prefix);
		}
		List<CurrentVersion> cvs = currentVersionDao.findByAppCustomerPrefixAndPlat(prefix, plat);
		
		List<AppDownloadListDto> downloadList =  AppEntityMapper.INSTANCE.currentVersionToAppDownloadListDto(cvs);
		App app = null;
		for(AppDownloadListDto  download : downloadList){
			app = appdao.findOne(download.getAppId());
			download.setName(app.getName());
			download.setIcon(app.getIcon());
		}
		
		return downloadList;
	}

}
