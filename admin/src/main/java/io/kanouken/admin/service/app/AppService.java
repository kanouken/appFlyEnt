package io.kanouken.admin.service.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.kanouken.admin.dao.app.AppDao;
import io.kanouken.admin.dao.appversion.AppHistoryVersionDao;
import io.kanouken.admin.dao.appversion.AppVersionDao;
import io.kanouken.admin.model.app.App;
import io.kanouken.admin.model.app.CurrentVersion;
import io.kanouken.admin.model.app.HistoryVersion;
import io.kanouken.admin.model.app.dto.AppListDto;
import io.kanouken.admin.model.app.mapper.AppEntityMapper;
import io.kanouken.admin.model.app.vo.AppAddVo;
import io.kanouken.admin.model.app.vo.AppDetailVo;
import io.kanouken.admin.model.app.vo.AppUpdateVo;
import io.kanouken.admin.model.appversion.mapper.AppVersionEntityMapper;
import io.kanouken.admin.model.user.dto.CurrentAccountDto;

@Service
public class AppService {

	@Autowired
	private AppDao appdao;

	@Autowired
	AppVersionDao currentVersionDao;

	@Autowired
	AppHistoryVersionDao historyVersionDao;

	public AppAddVo addApp(AppAddVo appAddVo, MultipartFile icon, CurrentAccountDto currentAccount) {
		App app = new App();
		app.setName(appAddVo.getName());
		app.setDescription(appAddVo.getDescription());
		app.setIcon(icon.getName());
		app.setKeywords(appAddVo.getKeywords());
		app.setAppId(appAddVo.getAppId());
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

}
