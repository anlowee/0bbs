package com.iamwxc.bbs.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iamwxc.bbs.entity.moment.Moment;
import com.iamwxc.bbs.entity.moment.MomentComment;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
@EntityListeners(AuditingEntityListener.class)
public class MyUser {

    public MyUser() {
        profileURL = "http://www.gravatar.com/avatar?s=64";
        bbsPoint = 0L;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    // personal basic info
    @Column(unique = true)
    private String username;

    private String password;

    private String role;

    // some other feature
    private String profileURL;

    private Long bbsPoint;

    private String emailAddress;

    // personal moments info
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "myUser")
    @JsonManagedReference
    private List<Moment> moments;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "myUser")
    @JsonManagedReference
    private List<MomentComment> momentComments;

    @CreatedDate
    private Long gmtCreate;

    @LastModifiedDate
    private Long gmtModified;

    public void addMoment(Moment moment) {
        moments.add(moment);
        moment.setMyUser(this);
    }

    public void removeMoment(Moment moment) {
        moments.remove(moment);
        moment.setMyUser(null);
    }

    public void addMomentComment(MomentComment momentComment) {
        momentComments.add(momentComment);
        momentComment.setMyUser(this);
    }

    public void removeMomentComment(MomentComment momentComment) {
        momentComments.remove(momentComment);
        momentComment.setMyUser(null);
    }

    @Override
    public String toString() {
        Date createDate = new Date();
        if (gmtCreate != null)
            createDate.setTime(gmtCreate);
        else
            createDate.setTime(0L);
        return "username:" + username + "; role:" + role + "; create:" + createDate.toString();
    }

}
