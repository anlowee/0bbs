package com.iamwxc.bbs.controller;

import com.iamwxc.bbs.dto.PageDTO;
import com.iamwxc.bbs.entity.MyUser;
import com.iamwxc.bbs.entity.moment.Moment;
import com.iamwxc.bbs.entity.moment.MomentComment;
import com.iamwxc.bbs.service.MomentDetailsService;
import com.iamwxc.bbs.util.CustomErrorCode;
import com.iamwxc.bbs.util.MyUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Class description goes here.
 * <p>
 * Browser the moment and make/browser comments.
 * </p>
 *
 * @author CC
 * @version 1.0
 */
@Controller
public class MomentDetailsController {

    @Autowired
    private MomentDetailsService momentDetailsService;

    @Autowired
    private MyUserUtil myUserUtil;

    @GetMapping("/details")
    public ModelAndView seeMomentDetails(@RequestParam(value = "id") Long momentID,
                                         @RequestParam(value = "page", defaultValue = "1") Integer pageIndex) {
        ModelAndView modelAndView = new ModelAndView("moment-details");
        MyUser currentUser = myUserUtil.getLoginUser();
        modelAndView.addObject("user", currentUser);
        Moment currentMoment = momentDetailsService.getCurrentMoment(momentID);
        modelAndView.addObject("moment", currentMoment);
        PageDTO<MomentComment> momentCommentPage = momentDetailsService.getMomentCommentPage(pageIndex, 10, Sort.by(Sort.Direction.DESC, "gmtModified"), currentMoment);
        modelAndView.addObject("commentPage", momentCommentPage);
        return modelAndView;
    }

    @PostMapping("/comment")
    public ModelAndView doComment(@RequestParam(value = "id", required = false) Long momentID,
                                  @RequestParam(value = "content", required = false) String content) {
        ModelAndView modelAndView = new ModelAndView("redirect:/details");
        MyUser currentUser = myUserUtil.getLoginUser();
        modelAndView.addObject("id", momentID);
        CustomErrorCode status = momentDetailsService.doComment(currentUser, momentID, content);
        return modelAndView;
    }

}
