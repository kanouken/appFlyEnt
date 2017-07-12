package io.kanouken.admin.service.appversion;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.kanouken.admin.dao.app.AppDao;
import io.kanouken.admin.dao.appversion.AppHistoryVersionDao;
import io.kanouken.admin.dao.appversion.AppVersionDao;
import io.kanouken.admin.model.PlatEnum;
import io.kanouken.admin.model.app.App;
import io.kanouken.admin.model.app.CurrentVersion;
import io.kanouken.admin.model.app.HistoryVersion;
import io.kanouken.admin.model.appversion.mapper.AppVersionEntityMapper;
import io.kanouken.admin.model.appversion.vo.CurrentVersionVo;
import io.kanouken.admin.model.appversion.vo.VersionAddVo;
import io.kanouken.admin.model.appversion.vo.VersionDetailVo;
import io.kanouken.admin.model.appversion.vo.VersionUpdateVo;
import io.kanouken.admin.model.user.dto.CurrentAccountDto;
import io.kanouken.admin.service.BaseService;

@Service
public class AppVersionService extends BaseService {

	@Autowired
	AppVersionDao currentVersionDao;
	@Autowired
	AppHistoryVersionDao historyVersionDao;
	@Autowired
	AppDao appdao;

	public void addVersion(MultipartFile appFile, VersionAddVo versionAddVo, CurrentAccountDto currentAccount)
			throws IOException {
		String downLoadUrl = "";
		String _oname = appFile.getOriginalFilename();
		App app = appdao.findOne(versionAddVo.getAppId());
		String   fileSize =  (appFile.getSize()/1024/1024) +"mb";
		String appFileName = app.getKeywords() + "_" + versionAddVo.getVersion()
				+ _oname.substring(_oname.lastIndexOf("."), _oname.length());
		downLoadUrl = config.getPublicUrl() + "/resources/app/" + app.getKeywords()
				+ "-" + versionAddVo.getPlat() +"-" + versionAddVo.getVersion() + "-"
				+ appFileName;
		File pPath = new File(config.getAppFilePath() + File.separator + app.getKeywords() + File.separator
				+ versionAddVo.getPlat() + File.separator + versionAddVo.getVersion());
		// make the plist file if ios
		if (PlatEnum.ios.toString().equals(versionAddVo.getPlat())) {
			PlistFactory factory = new PlistFactory(versionAddVo.getVersion(), app.getAppId(), app.getName(),
					downLoadUrl);

			File plistPPath = new File(config.getPlistFilePath() + File.separator + app.getKeywords());
			if (!plistPPath.exists()) {
				plistPPath.mkdirs();
			}
			factory.build(new File(plistPPath, app.getKeywords()+"_"+versionAddVo.getVersion() + ".plist"));
			// save app

			
			if (!pPath.exists()) {
				pPath.mkdirs();
			}
			downLoadUrl = "itms-services://?action=download-manifest&url=" + config.getPublicUrl()
					+ "/resources/plist/" + app.getKeywords() + "-" + app.getKeywords()+"_"+versionAddVo.getVersion()
					+ ".plist";
		}
		FileUtils.copyInputStreamToFile(appFile.getInputStream(), new File(pPath, appFileName));

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
		cVersion.setDownloadUrl(downLoadUrl);

		cVersion.setCreateId(currentAccount.getId());
		cVersion.setCreateTime(new Date());
		cVersion.setUpdateId(currentAccount.getId());
		cVersion.setCreator(currentAccount.getName());
		cVersion.setIsDelete(Byte.parseByte("0"));
		cVersion.setFileSize(fileSize);
		currentVersionDao.save(cVersion);
	}

	public void updateVersion(MultipartFile appFile, VersionUpdateVo versionUpdateVo, CurrentAccountDto currentAccount) {
		//FIXME not allow to change version number
		CurrentVersion oldCversion = currentVersionDao.findByAppIdAndPlat(versionUpdateVo.getAppId(),
				versionUpdateVo.getPlat());
		CurrentVersion cVersion = AppVersionEntityMapper.INSTANCE.versionUpdateVoToCurrentVersion(versionUpdateVo);
		cVersion.setDownloadUrl(oldCversion.getDownloadUrl());
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
