package io.kanouken.admin.dao.appversion;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.kanouken.admin.model.app.HistoryVersion;
@Repository
public interface AppHistoryVersionDao extends CrudRepository<HistoryVersion, Integer> {

	
	List<HistoryVersion> findByAppIdOrderByCreateTimeDesc(Integer appId);
}
