package com.iamwxc.bbs.controller;

import com.iamwxc.bbs.dto.PageDTO;
import com.iamwxc.bbs.entity.MyUser;
import com.iamwxc.bbs.entity.moment.Moment;
import com.iamwxc.bbs.service.IndexMomentService;
import com.iamwxc.bbs.service.MomentManageService;
import com.iamwxc.bbs.util.MyUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
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
public class MomentManageController {

    @Autowired
    private MomentManageService momentManageService;

    @Autowired
    private MyUserUtil myUserUtil;

    @GetMapping("/moments")
    public ModelAndView personalMoments(@RequestParam(value = "page", defaultValue = "1") Integer pageIndex) {
        ModelAndView modelAndView = new ModelAndView("moment-manage");
        MyUser currentUser = myUserUtil.getLoginUser();
        PageDTO<Moment> momentManagePage = momentManageService.getIndexMomentPage(pageIndex, 10, Sort.by(Sort.Direction.DESC, "gmtModified"), currentUser);
        modelAndView.addObject("momentPage", momentManagePage);
        return modelAndView;
    }

}
