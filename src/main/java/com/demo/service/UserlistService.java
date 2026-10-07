package com.demo.service;

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
import com.demo.dto.Userlist;
import com.demo.repository.UserlistRepository;
import com.demo.repository.UserlistSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserlistService {
 
    @Autowired
    private UserlistRepository userlistRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    public List<Userlist> getUsers() {
        return userlistRepository.findAll();
    }
 
    public Optional<Userlist> getUser(String wcode) {
        return userlistRepository.findById(wcode);
    }
    
    public ApiMessage<List<Userlist>> searchUsers(Userlist condition, Pageable pageable) {
        // Pageable 객체를 이용해 데이터 조회
    	Specification<Userlist> spec = UserlistSpecification.searchUsers(condition);
    	
    	Page<Userlist> list = userlistRepository.findAll(spec, pageable);
    	
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
    public void userSave(Userlist userlist) {
    	if (userlistRepository.existsById(userlist.getWcode())) {
    	    throw new BusinessException(
    	        ApiCode.DUPLICATE_ERROR,
    	        "이미 등록된 사용자입니다."
    	    );
    	}
    	
    	userlist.setWpwd(passwordEncoder.encode(userlist.getWpwd()));
    	
    	userlistRepository.save(userlist);
    }
    
    @Transactional
    public void userUpdate(Userlist userlist) {
    	
        Userlist user = userlistRepository.findById(userlist.getWcode())
                .orElseThrow(() -> new BusinessException(ApiCode.NOT_FOUND,"사용자를 찾을 수 없습니다."));

        if (userlist.getWpwd() == null) {
        	userlist.setWpwd(user.getWpwd());
        }

    	userlistRepository.save(userlist);
    }
    
    @Transactional
    public void userDelete(String wcode) {
        Userlist user = userlistRepository.findById(wcode)
                .orElseThrow(() -> new BusinessException(ApiCode.NOT_FOUND,"사용자를 찾을 수 없습니다."));
        
    	userlistRepository.deleteById(wcode);
    }
    
}