package com.iamwxc.bbs.util;

import com.iamwxc.bbs.dto.PageDTO;
import com.iamwxc.bbs.entity.moment.Moment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Class description goes here.
 * <p>
 * Util for page setting.
 * </p>
 *
 * @author CC
 * @version 1.0
 */
@Component
public class PageUtil<T> {

    /**
     * fill content and first/last page properties
     * @param tPageDTO target <code>PageDTO</code>
     * @param pageIndex current page index, used to determine first/last page
     */
    public void fillContent(PageDTO<T> tPageDTO, Page<T> tPage, Integer pageIndex) {
        tPageDTO.setPageNumber(tPage.getTotalPages());
        if (pageIndex.equals(1))
            tPageDTO.setFirstPage(true);
        else
            tPageDTO.setFirstPage(false);
        if (pageIndex.equals(tPageDTO.getPageNumber()))
            tPageDTO.setLastPage(true);
        else
            tPageDTO.setLastPage(false);
        List<T> ts = new ArrayList<>();
        for (T t : tPage) {
            ts.add(t);
        }
        tPageDTO.setPageContent(ts);
    }

}
