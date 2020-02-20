package com.iamwxc.bbs.service;

import com.iamwxc.bbs.dao.MomentDAO;
import com.iamwxc.bbs.entity.moment.Moment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
