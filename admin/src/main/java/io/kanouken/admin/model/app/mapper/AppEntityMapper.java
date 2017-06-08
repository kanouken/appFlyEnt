package io.kanouken.admin.model.app.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.kanouken.admin.model.app.App;
import io.kanouken.admin.model.app.dto.AppListDto;
import io.kanouken.admin.model.app.vo.AppDetailVo;
import io.kanouken.admin.model.app.vo.AppUpdateVo;

@Mapper
public interface AppEntityMapper {
	AppEntityMapper INSTANCE = Mappers.getMapper(AppEntityMapper.class);

	AppListDto appToAppListDto(App a);
	List<AppListDto> appToAppListDto(List<App> a);
	
	App appUpdateVoToApp(AppUpdateVo appUpdateVo);
	AppDetailVo appToAppDetailVo(App app);
}
