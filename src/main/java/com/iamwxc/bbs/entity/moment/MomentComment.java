package com.iamwxc.bbs.entity.moment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.iamwxc.bbs.entity.MyUser;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Class description goes here.
 * <p>
 * If you see this sentence, nothing ambiguous.
 * </p>
 *
 * @author CC
 * @version 1.0
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MomentComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long momentCommentID;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JsonBackReference
    private MyUser myUser;  // who remark

    @ManyToOne
    @JsonBackReference
    private Moment moment;  // of which moment

    @CreatedDate
    private Long gmtCreate;

    @LastModifiedDate
    private Long gmtModified;

}
