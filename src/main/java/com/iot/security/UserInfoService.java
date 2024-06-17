package com.iot.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.iot.entity.UserInfo;

@Service
public class UserInfoService implements UserDetailsService {
	@Autowired
	private UserInfoRepository userInfoRepository;

//    @Autowired
//    private PasswordEncoder encoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserInfo> userDetail = userInfoRepository.findByName(username);

		// Converting userDetail to UserDetails
		return userDetail.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}

	public String addUser(UserInfo userInfo) {
//        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
		userInfoRepository.save(userInfo);
		return "User Added Successfully";
	}

}
