package io.kanouken.admin.service.appversion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;

import io.kanouken.admin.properties.ConfigProperties;
import lombok.Data;

@Data
public class PlistFactory {

	public static final String body = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">"
			+ "<plist version=\"1.0\">" + "<dict>" + "<key>items</key>"
			+ "	<array> <dict> <key>assets</key> <array><dict> <key>kind</key><string>software-package</string><key>url</key>";

	@Autowired
	ConfigProperties config;

	/**
	 * 应用版本号
	 */
	private String version;
	/**
	 * 应用 id
	 */
	private String appId;
	/**
	 * 下载弹出框 显示 名称
	 */
	private String title;
	/**
	 * 文件下载路径
	 */
	private String downloadUrl;

	/**
	 * init 数据
	 * 
	 * @param version
	 * @param appId
	 * @param title
	 * @param downloadUrl
	 */
	public PlistFactory(String version, String appId, String title, String downloadUrl) {
		this.version = version;
		this.appId = appId;
		this.title = title;
		this.downloadUrl = downloadUrl;
	}

	public void build(File destFile) throws IOException {
		String is = PlistFactory.class.getResource("/default.plist").getFile();
		String content = FileUtils.readFileToString(new File(is), "utf8");
		content = content.replace("#{downloadUrl}", this.downloadUrl).replace("#{appId}", this.appId)
				.replace("#{version}", this.version).replace("#{title}", this.title);
		FileUtils.write(destFile, content, "utf8");
	}
}
