package com.iamwxc.bbs.repository;

import com.iamwxc.bbs.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface description goes here.
 * <p>
 * Connect <code>MyUser</code> to database.
 * </p>
 *
 * @author CC
 * @version 1.0
 */
@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    MyUser findByUsername(String s);

}
