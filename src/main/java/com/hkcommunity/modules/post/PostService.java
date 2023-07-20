package com.hkcommunity.modules.post;

import com.hkcommunity.modules.account.Account;
import com.hkcommunity.modules.account.UserAccount;
import com.hkcommunity.modules.post.form.PostForm;
import com.hkcommunity.modules.post.form.PostResponseForm;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public Post createNewPost(Post post, Account account) {
        Post newPost = postRepository.save(post);
        newPost.addAuthor(account);
        return newPost;
    }

    public PostResponseForm getPost(Long id, Account account) {
        Optional<Post> postWrapper = postRepository.findById(id);
        Post post = postWrapper.get();
        String convertedPublishedDateTime = post.getPublishedDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        boolean authorCheck = post.isAuthor(new UserAccount(account));

        PostResponseForm postResponseForm = PostResponseForm.builder()
                .postNum(post.getId())
                .author(post.getAuthor().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .publishedDateTime(convertedPublishedDateTime)
                .modifiedDateTime(post.getModifiedDateTime())
                .authorChecked(authorCheck)
                .build();

        return postResponseForm;
    }

    public Post getPostToUpdate(Account account, Long postId) {
        Post post = this.getPost(postId);
        if (!account.isAuthor(post)) {
            throw new AccessDeniedException("작성자만 게시글 수정이 가능합니다.");
        }

        return post;
    }

    private Post getPost(Long postId) {
        Optional<Post> postWrapped = this.postRepository.findById(postId);
        if (postWrapped == null) {
            throw new IllegalArgumentException(postId + "에 해당하는 게시글이 없습니다.");
        }

        Post post = postWrapped.get();
        return post;
    }

    public void updatePost(Post post, PostForm postForm) {
        modelMapper.map(postForm, post);
    }
}
