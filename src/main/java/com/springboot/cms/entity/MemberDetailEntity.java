package com.springboot.cms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "member_detail")
public class MemberDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Integer detailId;

    @Column(name = "phone")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phone;

    @Column(name = "email")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "description")
    private String description;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id",nullable = false,referencedColumnName = "id")
    private MemberEntity member;

    @PrePersist
    protected void onCreate() {
        if (createdDate == null && updateTime == null) {
            createdDate = new Date();
            updateTime = new Date();
        }
    }

}

