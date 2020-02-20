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

    public MomentComment() {
        likes = 0L;
        comments = 0L;
        dislikes = 0L;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long momentCommentID;

    @Column(columnDefinition = "TEXT")
    private String content;

    // other info
    private Long likes;

    private Long comments;

    private Long dislikes;

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

}
