package com.iamwxc.bbs.dao;

import com.iamwxc.bbs.entity.moment.MomentComment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface description goes here.
 * <p>
 * If you see this sentence, nothing ambiguous.
 * </p>
 *
 * @author CC
 * @version 1.0
 */
public interface MomentCommentDAO extends JpaRepository<MomentComment, Long> {
}
