package com.iamwxc.bbs.dao;

import com.iamwxc.bbs.entity.MyUser;
import com.iamwxc.bbs.entity.moment.Moment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface description goes here.
 * <p>
 * If you see this sentence, nothing ambiguous.
 * </p>
 *
 * @author CC
 * @version 1.0
 */
@Repository
public interface MomentDAO extends JpaRepository<Moment, Long> {

    Moment findByMomentID(Long id);

    Page<Moment> findAllByMyUser(MyUser myUser, Pageable pageable);

}
