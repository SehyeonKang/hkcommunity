package com.hkcommunity.modules.post;

import com.hkcommunity.modules.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post createNewPost(Post post, Account account) {
        Post newPost = postRepository.save(post);
        newPost.addAuthor(account);
        return newPost;
    }
}
