package com.hkcommunity.modules.post.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostSearchType {

    ALL("all", "전체"),
    TITLE("title", "제목"),
    CONTENT("content", "내용"),
    TITCON("title_content", "제목+내용"),
    AUTHOR("nickname", "글쓴이");

    private final String value;
    private final String description;
}
