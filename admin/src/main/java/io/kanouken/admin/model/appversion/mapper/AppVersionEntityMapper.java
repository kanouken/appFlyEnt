package io.kanouken.admin.model.appversion.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.kanouken.admin.model.app.CurrentVersion;
import io.kanouken.admin.model.app.HistoryVersion;
import io.kanouken.admin.model.appversion.vo.CurrentVersionVo;
import io.kanouken.admin.model.appversion.vo.HistoryVersionVo;
import io.kanouken.admin.model.appversion.vo.VersionAddVo;
import io.kanouken.admin.model.appversion.vo.VersionDetailVo;
import io.kanouken.admin.model.appversion.vo.VersionUpdateVo;

@Mapper
public interface AppVersionEntityMapper {
	AppVersionEntityMapper INSTANCE = Mappers.getMapper(AppVersionEntityMapper.class);

	CurrentVersion versionAddVoToCurrentVersion(VersionAddVo versionAddVo);

	@Mapping(target = "id", ignore = true)
	HistoryVersion currentVersionToHistoryVersion(CurrentVersion oldCversion);

	CurrentVersion versionUpdateVoToCurrentVersion(VersionUpdateVo versionUpdateVo);

	CurrentVersionVo currentVersionToCurrentVersionVo(CurrentVersion cv);
	List<CurrentVersionVo> currentVersionToCurrentVersionVo(List<CurrentVersion> cvs);


	HistoryVersionVo historyVersionToHistoryVersionVo(HistoryVersion his);

	List<HistoryVersionVo> historyVersionToHistoryVersionVo(List<HistoryVersion> his);

	VersionDetailVo currentVersionToVersionDetailVo(CurrentVersion cv);

}
