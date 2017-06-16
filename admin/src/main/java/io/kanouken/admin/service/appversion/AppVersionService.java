package io.kanouken.admin.service.appversion;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.kanouken.admin.dao.app.AppDao;
import io.kanouken.admin.dao.appversion.AppHistoryVersionDao;
import io.kanouken.admin.dao.appversion.AppVersionDao;
import io.kanouken.admin.model.app.App;
import io.kanouken.admin.model.app.CurrentVersion;
import io.kanouken.admin.model.app.HistoryVersion;
import io.kanouken.admin.model.appversion.mapper.AppVersionEntityMapper;
import io.kanouken.admin.model.appversion.vo.CurrentVersionVo;
import io.kanouken.admin.model.appversion.vo.VersionAddVo;
import io.kanouken.admin.model.appversion.vo.VersionDetailVo;
import io.kanouken.admin.model.appversion.vo.VersionUpdateVo;
import io.kanouken.admin.model.user.dto.CurrentAccountDto;

@Service
public class AppVersionService {

	@Autowired
	AppVersionDao currentVersionDao;
	@Autowired
	AppHistoryVersionDao historyVersionDao;
	@Autowired
	AppDao appdao;

	public void addVersion(VersionAddVo versionAddVo, CurrentAccountDto currentAccount) {
		// find current version
		CurrentVersion oldCversion = currentVersionDao.findByAppIdAndPlat(versionAddVo.getAppId(),
				versionAddVo.getPlat());
		if (null != oldCversion) {
			HistoryVersion historyVersion = AppVersionEntityMapper.INSTANCE.currentVersionToHistoryVersion(oldCversion);
			// set last version to the history version
			historyVersionDao.save(historyVersion);
		}
		// update or save current version
		CurrentVersion cVersion = AppVersionEntityMapper.INSTANCE.versionAddVoToCurrentVersion(versionAddVo);
		if (null != oldCversion) {
			cVersion.setId(oldCversion.getId());
		}
		cVersion.setCreateId(currentAccount.getId());
		cVersion.setCreateTime(new Date());
		cVersion.setUpdateId(currentAccount.getId());
		cVersion.setCreator(currentAccount.getName());
		cVersion.setIsDelete(Byte.parseByte("0"));
		currentVersionDao.save(cVersion);
	}

	public void updateVersion(VersionUpdateVo versionUpdateVo, CurrentAccountDto currentAccount) {
		CurrentVersion oldCversion = currentVersionDao.findByAppIdAndPlat(versionUpdateVo.getAppId(),
				versionUpdateVo.getPlat());
		CurrentVersion cVersion = AppVersionEntityMapper.INSTANCE.versionUpdateVoToCurrentVersion(versionUpdateVo);
		cVersion.setId(oldCversion.getId());
		cVersion.setCreateId(oldCversion.getCreateId());
		cVersion.setCreator(oldCversion.getCreator());
		cVersion.setCreateTime(oldCversion.getCreateTime());
		cVersion.setUpdateId(currentAccount.getId());
		cVersion.setIsDelete(Byte.parseByte("0"));
		currentVersionDao.save(cVersion);
	}

	public CurrentVersionVo getCurrentVersion(String plat, String appId) {
		CurrentVersion cv = currentVersionDao.findByAppIdAndPlat(appId, plat);
		CurrentVersionVo cvVo = AppVersionEntityMapper.INSTANCE.currentVersionToCurrentVersionVo(cv);
		return cvVo;
	}

	public VersionDetailVo getDetail(Integer appId, String plat) {
		App app = this.appdao.findOne(appId);
		CurrentVersion cv = this.currentVersionDao.findByAppIdAndPlat(appId, plat);
		VersionDetailVo cvDetail = AppVersionEntityMapper.INSTANCE.currentVersionToVersionDetailVo(cv);
		cvDetail.setAppId(String.valueOf(appId));
		cvDetail.setAppName(app.getName());
		return cvDetail;
	}

}
