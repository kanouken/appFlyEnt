package io.kanouken.admin.model.app.vo;

import java.util.List;

import io.kanouken.admin.model.appversion.vo.CurrentVersionVo;
import io.kanouken.admin.model.appversion.vo.HistoryVersionVo;
import lombok.Data;

@Data
public class AppDetailVo {

	private String id;
	private String name;
	private String description;
	private String icon;
	private String keywords;
	private String appId;

	private String createTime;
	private String creator;
	private List<CurrentVersionVo> currentVersions;

	private List<HistoryVersionVo> historyVersions;

}
