package com.iamwxc.bbs.service;

import com.iamwxc.bbs.dao.MomentDAO;
import com.iamwxc.bbs.dto.PageDTO;
import com.iamwxc.bbs.entity.MyUser;
import com.iamwxc.bbs.entity.moment.Moment;
import com.iamwxc.bbs.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class MomentManageService {

    @Autowired
    private MomentDAO momentDAO;

    @Autowired
    private PageUtil<Moment> momentPageUtil;

    /**
     * get a given page index moment content in moment manage page
     * @param pageIndex given which page index
     * @param pageSize given page size
     * @param sort given sort order
     * @return a <code>PageDTO</code> object
     */
    public PageDTO<Moment> getIndexMomentPage(Integer pageIndex, Integer pageSize, Sort sort, MyUser myUser) {
        PageDTO<Moment> momentManagePageDTO = new PageDTO<>();
        momentManagePageDTO.setPageIndex(pageIndex);
        momentManagePageDTO.setPageSize(pageSize);
        momentManagePageDTO.setSort(sort);
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, sort);
        Page<Moment> momentPage = momentDAO.findAllByMyUser(myUser, pageable);

        momentPageUtil.fillContent(momentManagePageDTO, momentPage, pageIndex);
        return momentManagePageDTO;
    }

}
