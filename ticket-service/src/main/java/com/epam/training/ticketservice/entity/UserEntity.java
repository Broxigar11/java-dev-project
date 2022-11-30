package com.epam.training.ticketservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name="users")
public class UserEntity {

        public UserEntity(String username, String password, Role role) {
                this.username = username;
                this.password = password;
                this.role = role;
        }

        @Id
        @GeneratedValue
        private Integer id;

        @Column(unique = true)
        private String username;

        private String password;

        @Enumerated(EnumType.STRING)
        private Role role;

        public enum Role {
                ADMIN,
                USER
        }

}
