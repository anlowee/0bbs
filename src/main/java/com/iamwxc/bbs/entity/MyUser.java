package com.iamwxc.bbs.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Class description goes here.
 * <p>
 * User basic information.
 * </p>
 *
 * @author CC
 * @version 1.0
 */
@Data
@Entity
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String role;

    private Long gmtCreate;

    private Long gmtModified;

}
