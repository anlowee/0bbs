package com.iamwxc.bbs.entity.like;

import lombok.Data;

/**
 * Class description goes here.
 * <p>
 * If you see this sentence, nothing ambiguous.
 * </p>
 *
 * @author CC
 * @version 1.0
 */
@Data
public class Like {

    public Like() {
        status = 0;
    }

    private Integer status;  // 0-nothing, 1-like, -1-dislike

    private Long userID;  // who like

}
