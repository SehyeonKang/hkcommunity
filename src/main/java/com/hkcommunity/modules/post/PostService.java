package com.hkcommunity.modules.post;

import com.hkcommunity.modules.account.Account;
import com.hkcommunity.modules.post.form.PostResponseForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    public PostResponseForm getPost(Long id) {
        Optional<Post> postWrapper = postRepository.findById(id);
        Post post = postWrapper.get();

        PostResponseForm postResponseForm = PostResponseForm.builder()
                .author(post.getAuthor().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .publishedDateTime(post.getPublishedDateTime())
                .modifiedDateTime(post.getModifiedDateTime())
                .build();

        return postResponseForm;
    }
}
