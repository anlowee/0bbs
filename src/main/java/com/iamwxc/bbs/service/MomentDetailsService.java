package com.iamwxc.bbs.service;

import com.iamwxc.bbs.dao.MomentCommentDAO;
import com.iamwxc.bbs.dao.MomentDAO;
import com.iamwxc.bbs.dto.PageDTO;
import com.iamwxc.bbs.entity.MyUser;
import com.iamwxc.bbs.entity.moment.Moment;
import com.iamwxc.bbs.entity.moment.MomentComment;
import com.iamwxc.bbs.util.CustomErrorCode;
import com.iamwxc.bbs.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private MomentCommentDAO momentCommentDAO;

    @Autowired
    private PageUtil<MomentComment> momentCommentPageUtil;

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

    public PageDTO<MomentComment> getMomentCommentPage(Integer pageIndex, Integer pageSize, Sort sort, Moment moment) {
        PageDTO<MomentComment> momentCommentPageDTO = new PageDTO<>();
        momentCommentPageDTO.setPageIndex(pageIndex);
        momentCommentPageDTO.setPageSize(pageSize);
        momentCommentPageDTO.setSort(sort);
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, sort);
        Page<MomentComment> momentCommentPage = momentCommentDAO.findAllByMoment(moment, pageable);

        momentCommentPageUtil.fillContent(momentCommentPageDTO, momentCommentPage, pageIndex);
        return momentCommentPageDTO;
    }

}
