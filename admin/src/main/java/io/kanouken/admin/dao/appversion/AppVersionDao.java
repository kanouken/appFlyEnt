package io.kanouken.admin.dao.appversion;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.kanouken.admin.model.app.CurrentVersion;

@Repository
public interface AppVersionDao extends CrudRepository<CurrentVersion, Integer> {

	CurrentVersion findByAppId(Integer appId);

	@Query(value = "select current_version.* from app left join current_version on app.id = current_version.app_id where app.app_id = ?1 and current_version.plat = ?2 ",nativeQuery=true)
	CurrentVersion findByAppIdAndPlat(String appId, String plat);
}
