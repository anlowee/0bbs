package com.iamwxc.bbs.controller;

import com.iamwxc.bbs.dto.PageDTO;
import com.iamwxc.bbs.entity.MyUser;
import com.iamwxc.bbs.entity.moment.Moment;
import com.iamwxc.bbs.service.IndexMomentService;
import com.iamwxc.bbs.util.MyUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
public class IndexController {

    @Autowired
    private MyUserUtil myUserUtil;

    @Autowired
    private IndexMomentService indexMomentService;

    @GetMapping("/")
    public ModelAndView index(@RequestParam(name = "page", defaultValue = "1") Integer pageIndex) {
        ModelAndView modelAndView = new ModelAndView("index");
        MyUser currentUser = myUserUtil.getLoginUser();
        modelAndView.addObject("user", currentUser);

        PageDTO<Moment> indexMomentPage = indexMomentService.getIndexMomentPage(pageIndex, 10, Sort.by(Sort.Direction.DESC, "gmtModified"));
        modelAndView.addObject("momentPage",indexMomentPage);
        return modelAndView;
    }

}
