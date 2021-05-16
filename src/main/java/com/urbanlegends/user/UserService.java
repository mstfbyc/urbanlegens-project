package com.urbanlegends.user;

import com.urbanlegends.errors.NotFoundException;
import com.urbanlegends.file.FileService;
import com.urbanlegends.user.vm.UserUpdateVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {

	UserRepository userRepository;
	PasswordEncoder passwordEncoder;
	FileService fileService;

	public UserService(UserRepository userRepository,FileService fileService) {
		this.userRepository = userRepository;
		this.passwordEncoder = new BCryptPasswordEncoder();
		this.fileService = fileService;
	}

	public void saveUser(User user) {
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

    public Page<User> getAllUser(User user,Pageable pageable) {
		Page<User> userList;
		if(user != null){
			userList = userRepository.findByUsernameNot(user.getUsername(),pageable);
		}else{
			userList = userRepository.findAll(pageable);
		}

		return userList;
    }

	public User getUser(String username) {
		User user = userRepository.findByUsername(username);
		if(user == null){
			throw new NotFoundException();
		}
		return user;
	}

	public User updateUser(String username, UserUpdateVM userUpdateVM) {
		User user = getUser(username);
		user.setDisplayName(userUpdateVM.getDisplayName());
		if(userUpdateVM.getImage() !=null){
			try {
				String storedFileName = fileService.writeBase64EncodedStringToFile(userUpdateVM.getImage());
				user.setImage(storedFileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return userRepository.save(user);
	}
}
