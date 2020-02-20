package com.iamwxc.bbs.service;

import com.iamwxc.bbs.dao.MomentDAO;
import com.iamwxc.bbs.entity.MyUser;
import com.iamwxc.bbs.entity.moment.Moment;
import com.iamwxc.bbs.entity.moment.MomentComment;
import com.iamwxc.bbs.util.CustomErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class description goes here.
 * <p>
 * If you see this sentence, nothing ambiguous.
 * </p>
 *
 * @author CC
 * @version 1.0
 */
@Service
public class MomentDetailsService {

    @Autowired
    private MomentDAO momentDAO;

    public Moment getCurrentMoment(Long momentID) {
        return momentDAO.findByMomentID(momentID);
    }

    public CustomErrorCode doComment(MyUser currentUser, Long momentID, String content) {
        if (content == null || content.equals(""))
            return CustomErrorCode.PARAM_NULL;
        else {
            Moment currentMoment = momentDAO.findByMomentID(momentID);
            MomentComment momentComment = new MomentComment();
            momentComment.setContent(content);
            currentMoment.addMomentComment(momentComment);
            currentUser.addMomentComment(momentComment);
            currentMoment.setComments((long) currentMoment.getMomentComments().size());
            momentDAO.save(currentMoment);
            return CustomErrorCode.REQUEST_SUCCESS;
        }
    }

}
