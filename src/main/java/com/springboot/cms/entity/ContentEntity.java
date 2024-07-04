package com.springboot.cms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "content")
public class ContentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "brief")
    @NotNull
    private String brief;

    @Column(name = "content")
    @NotNull
    private String content;

    @Column(name = "category")
    @NotNull
    private String category;

    @Column(name = "create_date")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "update_time")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private MemberEntity member;

    @PrePersist
    private void initCreatedTime() {
        if (createDate == null && updateTime == null) {
            createDate = new Date();
            updateTime = new Date();
        }
    }
}

