package com.demo.oaush2api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.config.ApiCode;
import com.demo.config.ApiMessage;
import com.demo.config.BusinessException;
import com.demo.config.Pagination;
import com.demo.oaush2api.dto.User;
import com.demo.oaush2api.repository.UserRepository;
import com.demo.oaush2api.repository.UserSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
 
    @Autowired
    private UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    public List<User> getUsers() {
        return userRepository.findAll();
    }
 
    public Optional<User> getUser(String userId) {
        return userRepository.findByUserId(userId);
    }
    
    public ApiMessage<List<User>> searchUsers(User condition, Pageable pageable) {
        // Pageable 객체를 이용해 데이터 조회
    	Specification<User> spec = UserSpecification.searchUsers(condition);
    	
    	Page<User> list = userRepository.findAll(spec, pageable);
    	
        // 페이지네이션 정보 생성
        Pagination pagination = Pagination.builder()
            .page(list.getNumber())                   // 현재 페이지 번호
            .size(list.getSize())                     // 페이지당 데이터 개수
            .currentElement(list.getNumberOfElements())// 현재 페이지 데이터 개수
            .totalPage(list.getTotalPages())          // 전체 페이지 수
            .totalElement(list.getTotalElements())    // 전체 데이터 수
            .build();

        return ApiMessage.success(list.getContent(), pagination);
    }
    
    @Transactional
    public void userSave(User user) {
    	if (userRepository.existsByUserId(user.getUserId())) {
    	    throw new BusinessException(
    	        ApiCode.DUPLICATE_ERROR,
    	        "이미 등록된 사용자입니다."
    	    );
    	}
    	
    	user.setPasswd(passwordEncoder.encode(user.getPasswd()));
    	
    	userRepository.save(user);
    }
    
    @Transactional
    public void userUpdate(User user) {
    	
    	User userInfo = userRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new BusinessException(ApiCode.NOT_FOUND,"사용자를 찾을 수 없습니다."));

        if (user.getPasswd() == null) {
        	user.setPasswd(passwordEncoder.encode(userInfo.getPasswd()));
        }

    	userRepository.save(user);
    }
    
    @Transactional
    public void userDelete(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ApiCode.NOT_FOUND,"사용자를 찾을 수 없습니다."));
        
    	userRepository.deleteByUserId(userId);
    }
    
}