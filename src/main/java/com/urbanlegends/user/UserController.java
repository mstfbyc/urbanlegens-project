package com.urbanlegends.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.urbanlegends.errors.ApiError;
import com.urbanlegends.shared.CurrentUser;
import com.urbanlegends.shared.Views;
import com.urbanlegends.user.vm.UserUpdateVM;
import com.urbanlegends.user.vm.UserVM;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.urbanlegends.shared.GenericResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1.0")
public class UserController {
	
	@Autowired
	UserService userservice;

	
	@PostMapping("/users")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create user" ,notes="Urban legens create user")
	public GenericResponse createUser(@Valid @RequestBody User user) {
		userservice.saveUser(user);
		GenericResponse  response = new GenericResponse();
		response.setMessage("User Created");
		return response;
		
	}

	@GetMapping("/users")
	@ApiOperation(value= "Get all user", notes="Urban legends get all user")
	//@JsonView(Views.Base.class)
	public ResponseEntity<Page<UserVM>> getAllUser(Pageable pageable, @CurrentUser User user){
		Page<UserVM> userList = userservice.getAllUser(user,pageable).map(UserVM::new);
		return ResponseEntity.ok(userList);
	}

	@GetMapping("/users/{username}")
	@ApiOperation(value = "find user by username ", notes = "find user by username in urban legends")
	public ResponseEntity<UserVM> getUser(@PathVariable String username){
		User user = userservice.getUser(username);
		return ResponseEntity.ok(new UserVM(user));
	}

	@PutMapping("/users/{username}")
	@ApiOperation(value = "Update User", notes = "Urban legends update user")
	@PreAuthorize("#username == principal.username")
	public ResponseEntity<?> updateUser(@RequestBody UserUpdateVM userUpdateVM, @PathVariable String username){
		User user = userservice.updateUser(username,userUpdateVM);
		return ResponseEntity.ok(new UserVM(user));
	}

}
