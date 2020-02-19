package com.iamwxc.bbs.entity.moment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iamwxc.bbs.entity.MyUser;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

/**
 * Class description goes here.
 * <p>
 * A moment entity.
 * </p>
 *
 * @author CC
 * @version 1.0
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Moment {

    public Moment() {
        readings = 0L;
        likes = 0L;
        dislikes = 0L;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long momentID;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JsonBackReference
    private MyUser myUser;  // author

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "moment")
    @JsonManagedReference
    private List<MomentComment> momentComments;

    // other info
    private Long readings;

    private Long likes;

    private Long dislikes;

    @CreatedDate
    private Long gmtCreate;

    @LastModifiedDate
    private Long gmtModified;

    public void addMomentComment(MomentComment momentComment) {
        momentComments.add(momentComment);
        momentComment.setMoment(this);
    }

    public void removeMomentComment(MomentComment momentComment) {
        momentComments.remove(momentComment);
        momentComment.setMoment(null);
    }

}
