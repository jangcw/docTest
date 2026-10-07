package com.demo.oaush2api.dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "사용자")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;

    @Schema(description = "사용자ID", example = "")
    @Column(name = "user_id", length = 100, nullable = false)
    private String userId;

    @Schema(description = "사용자명", example = "")
    @Column(name = "user_name", length = 100, nullable = false)
    private String userName;

    @Schema(description = "패스워드", example = "")
    @Column(name = "passwd", length = 200, nullable = false)
    private String passwd;

    @Schema(description = "부서명", example = "")
    @Column(name = "dept", length = 100)
    private String dept;

    @Schema(description = "휴대폰", example = "")
    @Column(name = "mobile", length = 50)
    private String mobile;

    @Schema(description = "이메일", example = "")
    @Column(name = "email", length = 200)
    private String email;

    @CreationTimestamp
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    public User(
            String userId,
            String passwd,
            String dept,
            String mobile,
            String email,
            String userName,            
            String provider,
            String providerId
    ) {
        this.userId = userId;
        this.passwd = passwd;
        this.dept = dept;
        this.mobile = mobile;
        this.email = email;
        this.userName = userName;
        this.provider = provider;
        this.providerId = providerId;
    }

    
    @Builder
    public User(
            String email,
            String userName,
            Role role,
            String provider,
            String providerId
    ) {
        this.email = email;
        this.userName = userName;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
    
    /*
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_userroles",
            joinColumns = @JoinColumn(name = "user_idx"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    
    private String provider;
    private String providerId; // 소셜 식별자

    // 소셜 로그인 시 사용자 이름 업데이트 메서드
    public User updateUserName(String userName) {
        this.userName = userName;
        return this;
    }
}