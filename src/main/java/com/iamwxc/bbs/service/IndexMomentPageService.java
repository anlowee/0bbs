package com.iamwxc.bbs.service;

import com.iamwxc.bbs.dao.MomentDAO;
import com.iamwxc.bbs.dto.PageDTO;
import com.iamwxc.bbs.entity.moment.Moment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
public class IndexMomentPageService {

    @Autowired
    private MomentDAO momentDAO;

    /**
     * get a given page index moment content in index page
     * @param pageIndex given which page index
     * @param pageSize given page size
     * @param sort given sort order
     * @return a <code>PageDTO</code> object
     */
    public PageDTO<Moment> getIndexMomentPage(Integer pageIndex, Integer pageSize, Sort sort) {
        PageDTO<Moment> indexMomentPageDTO = new PageDTO<>();
        indexMomentPageDTO.setPageIndex(pageIndex);
        indexMomentPageDTO.setPageSize(pageSize);
        indexMomentPageDTO.setSort(sort);
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, sort);
        Page<Moment> momentPage = momentDAO.findAll(pageable);

        indexMomentPageDTO.setPageNumber(momentPage.getTotalPages());
        if (pageIndex.equals(1))
            indexMomentPageDTO.setFirstPage(true);
        else
            indexMomentPageDTO.setFirstPage(false);
        if (pageIndex.equals(indexMomentPageDTO.getPageNumber()))
            indexMomentPageDTO.setLastPage(true);
        else
            indexMomentPageDTO.setLastPage(false);
        List<Moment> currentPageMoments = new ArrayList<>();
        for (Moment moment : momentPage) {
            currentPageMoments.add(moment);
        }
        indexMomentPageDTO.setPageContent(currentPageMoments);
        return indexMomentPageDTO;
    }

}
