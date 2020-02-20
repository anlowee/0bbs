package com.iamwxc.bbs.controller;

import com.iamwxc.bbs.entity.moment.Moment;
import com.iamwxc.bbs.service.MomentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
@RestController
public class MomentDetailsController {

    @Autowired
    private MomentDetailsService momentDetailsService;

    @GetMapping("/details")
    public ModelAndView seeMomentDetails(@RequestParam(value = "id", required = true) Long momentID) {
        ModelAndView modelAndView = new ModelAndView("moment-details");
        modelAndView.addObject("moment", momentDetailsService.getCurrentMoment(momentID));
        return modelAndView;
    }

}
