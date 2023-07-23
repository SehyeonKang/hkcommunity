package com.hkcommunity.modules.like.form;

import lombok.Data;

@Data
public class LikeResponseForm {

    private Long postLikeNum;
    private boolean pushedCheck;

    public LikeResponseForm(Long postLikeNum, boolean pushedCheck) {
        this.postLikeNum = postLikeNum;
        this.pushedCheck = pushedCheck;
    }
}
