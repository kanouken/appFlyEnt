package io.kanouken.admin.controller.foo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.kanouken.admin.controller.BaseController;
import io.kanouken.admin.model.foo.Foo;
import io.kanouken.admin.model.user.dto.CurrentAccountDto;
import io.kanouken.admin.service.FooService;

@RestController
public class FooController extends BaseController {
	@Autowired
	FooService fooService;

	@GetMapping("/foos")
	public Map<String, Object> foos() {
		CurrentAccountDto  dto =  this.currentAccount;
		return renderDt(fooService.getFoos());
	}

	@PostMapping("/foo")
	public Foo addFoo(@RequestBody Foo foo) {

		return fooService.addFoo(foo);
	}

	@GetMapping("/foo/{id}")
	public Foo detail(@PathVariable("id") Integer id) {
		return fooService.queryDetial(id);
	}

	@DeleteMapping("/foo/{id}")
	public void delete(@PathVariable("id") Integer id) {
		fooService.deleteFoo(id);
	}

	@PutMapping("/foo")
	public Foo updateFoo(@RequestBody Foo foo) {

		return fooService.updateFoo(foo);
	}

	
	
	@PostMapping("foo/upload")
	public void upload(MultipartFile file){
		
		System.out.println(file.getSize());
		
	}
	

}
