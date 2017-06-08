package io.kanouken.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.kanouken.admin.dao.app.AppDao;
import io.kanouken.admin.model.app.App;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {
	
	@Autowired
	private AppDao appDao;
	
	@Test
	public void add(){
		App  app = new App();
		app.setName("sfsf");
		app.setDescription("sfsf");
		app.setIcon("sfsf");
		appDao.save(app);
		
	}

}
