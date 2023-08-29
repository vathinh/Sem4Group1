package com.aptech.group.model;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "users", indexes = @Index(columnList = "email"), uniqueConstraints={@UniqueConstraint(columnNames={"keycloak_id"})})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private TitleEntity title;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    @Column(name="keycloak_id", unique = true)
    private String keycloakId;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private ZonedDateTime createdDate;

    @LastModifiedDate
    private ZonedDateTime modifiedDate;

    private boolean deleteFlag = false;

    @Version
    private Long version;
}
