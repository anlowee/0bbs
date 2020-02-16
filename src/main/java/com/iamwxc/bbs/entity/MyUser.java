package com.iamwxc.bbs.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iamwxc.bbs.entity.moment.Moment;
import com.iamwxc.bbs.entity.moment.MomentComment;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    // personal basic info
    @Column(unique = true)
    private String username;

    private String password;

    private String role;

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

    @Override
    public String toString() {
        return username;
    }

}
