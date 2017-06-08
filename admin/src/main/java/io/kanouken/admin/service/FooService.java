package io.kanouken.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.kanouken.admin.dao.foo.FooDao;
import io.kanouken.admin.model.foo.Foo;

@Service
public class FooService {
	@Autowired
	FooDao fooDao;

	@Transactional(readOnly = true)
	public Iterable<Foo> getFoos() {
		return this.fooDao.findAll();
	}

	public Foo addFoo(Foo foo) {
		return fooDao.save(foo);

	}

	public Foo queryDetial(Integer id) {

		return fooDao.findOne(id);
	}

	public Foo updateFoo(Foo foo) {

		foo = fooDao.save(foo);
		return foo;
	}

	public void deleteFoo(Integer id) {
		
	     fooDao.delete(id);
	}
}
