package com.aptech.group.model;

import lombok.*;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "titles", indexes = @Index(columnList = "code"))
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TitleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

//    @OneToMany(mappedBy="title")
//    private Set<UserEntity> employees;

    @ManyToOne
    @JoinColumnOrFormula(column = @JoinColumn(name = "created_by", referencedColumnName = "keycloak_id", insertable = false, updatable = false))
    private UserInfoEntity creator;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @CreatedDate
    private ZonedDateTime createdDate;

    @LastModifiedDate
    private ZonedDateTime modifiedDate;

    private boolean deleteFlag = false;

    @Version
    private Long version;
}
