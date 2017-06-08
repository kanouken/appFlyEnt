package io.kanouken.admin.dao.foo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.kanouken.admin.model.foo.Foo;
@Repository
public interface FooDao extends CrudRepository<Foo, Integer> {

}
