package com.demo.dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DynamicUpdate
@Table(name = "userlist")
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "사용자")
public class Userlist {

	@Schema(description = "사용자명", example = "")
    @Column(name = "uname", length = 100, nullable = false)
    private String uname;

	@Schema(description = "부서명", example = "")
    @Column(name = "udept", length = 100)
    private String udept;

    @Id
    @Schema(description = "사용자 코드", example = "")
    @Column(name = "wcode", length = 50, nullable = false)
    private String wcode;

    @Schema(description = "패스워드", example = "")
    @Column(name = "wpwd", length = 200)
    private String wpwd;

    @Schema(description = "휴대폰", example = "")
    @Column(name = "mobile", length = 50)
    private String mobile;

    @Schema(description = "회사명", example = "")
    @Column(name = "\"CompName\"", length = 200)
    private String compName;

    @Schema(description = "이메일", example = "")
    @Column(name = "email", length = 200)
    private String email;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}    