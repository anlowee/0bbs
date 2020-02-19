package com.iamwxc.bbs.dto;

import lombok.Data;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class description goes here.
 * <p>
 * This is a DTO page of any things.
 * </p>
 *
 * @author CC
 * @version 1.0
 */
@Data
@Component
public class PageDTO<T> {

    private List<T> pageContent;  // the content in a page(can be moments, comments...

    private Integer pageIndex;  // page index

    private Integer pageSize;  // how many objects in a page

    private Integer pageNumber;  // total pages number

    private Sort sort;

    private boolean firstPage;

    private boolean lastPage;

    public Integer getPreviousPage() {
        return Math.max(pageIndex - 1, 1);
    }

    public Integer getNextPage() {
        return Math.min(pageIndex + 1, pageNumber);
    }

}
