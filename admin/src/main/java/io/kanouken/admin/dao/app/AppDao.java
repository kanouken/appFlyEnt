package io.kanouken.admin.dao.app;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.kanouken.admin.model.app.App;

@Repository
public interface AppDao extends CrudRepository<App,Integer> {

	List<App> findAllByOrderByCreateTimeDesc();

}
