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
        likes = 0L;
        dislikes = 0L;
        comments = 0L;
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
    private Long likes;

    private Long comments;

    private Long dislikes;

    @CreatedDate
    private Long gmtCreate;

    @LastModifiedDate
    private Long gmtModified;

    public String getTimeInfo() {
        long seconds = (System.currentTimeMillis() - gmtCreate) / 1000;
        long years = seconds / 60 / 60 / 24 / 365;
        long months = seconds / 60 / 60 / 24 / 30;
        long weeks = seconds / 60 / 60 / 24 / 7;
        long days = seconds / 60 / 60 / 24;
        long hours = seconds / 60 / 60;
        long minutes = seconds / 60;
        String timeInfo = "";
        if (years > 0)
            timeInfo = years + "年前";
        else if (months > 0)
            timeInfo = months + "月前";
        else if (weeks > 0)
            timeInfo = weeks + "周前";
        else if (days > 0)
            timeInfo = days + "天前";
        else if (hours > 0)
            timeInfo = hours + "小时前";
        else if (minutes > 0)
            timeInfo = minutes + "分钟前";
        else
            timeInfo = seconds + "秒前";
        return timeInfo;
    }

    public void addMomentComment(MomentComment momentComment) {
        momentComments.add(momentComment);
        momentComment.setMoment(this);
    }

    public void removeMomentComment(MomentComment momentComment) {
        momentComments.remove(momentComment);
        momentComment.setMoment(null);
    }

}
