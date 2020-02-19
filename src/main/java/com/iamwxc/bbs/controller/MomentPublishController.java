package com.iamwxc.bbs.controller;

import com.iamwxc.bbs.dao.MomentDAO;
import com.iamwxc.bbs.entity.MyUser;
import com.iamwxc.bbs.entity.moment.Moment;
import com.iamwxc.bbs.util.MyUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Class description goes here.
 * <p>
 * If you see this sentence, nothing ambiguous.
 * </p>
 *
 * @author CC
 * @version 1.0
 */
@RestController
public class MomentPublishController {

    @Autowired
    private MyUserUtil myUserUtil;

    @GetMapping("/publish")
    public ModelAndView publish() {
        return new ModelAndView("publish");
    }

    /**
     * publish new moment and edit exist moment by this method.
     * @return a mav
     */
    @PostMapping("/publish")
    public ModelAndView doPublishNew(@RequestParam(value = "title", required = false) String title,
                                  @RequestParam(value = "content", required = false) String content) {
        ModelAndView modelAndView = new ModelAndView("publish");
        modelAndView.addObject("title", title);
        modelAndView.addObject("content", content);

        // check if title or content is null
        if (title == null || title.equals("")) {
            modelAndView.addObject("error", "标题不能为空喵~");
            return modelAndView;
        }
        if (content == null || content.equals("")) {
            modelAndView.addObject("error", "帖子内容不能为空喵~");
            return modelAndView;
        }

        // save into database
        MyUser currentUser = myUserUtil.getLoginUser();
        Moment moment = new Moment();
        moment.setTitle(title);
        moment.setContent(content);
        moment.setMyUser(currentUser);
        currentUser.addMoment(moment);
        modelAndView = new ModelAndView("redirect:/");  // publish success, redirect to homepage

        return modelAndView;
    }

}
