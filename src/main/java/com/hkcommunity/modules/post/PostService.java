package com.hkcommunity.modules.post;

import com.hkcommunity.modules.account.Account;
import com.hkcommunity.modules.account.UserAccount;
import com.hkcommunity.modules.post.form.BoardResponseForm;
import com.hkcommunity.modules.post.form.PostForm;
import com.hkcommunity.modules.post.form.PostResponseForm;
import com.hkcommunity.modules.post.form.ProfilePostResponseForm;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public Post createNewPost(Post post, Account account) {
        if (!account.isEmailVerified()) {
            throw new AccessDeniedException("이메일 인증을 완료한 사용자만 게시글 작성이 가능합니다.");
        }
        Post newPost = postRepository.save(post);
        postRepository.addAuthor(account, newPost);
        return newPost;
    }

    public PostResponseForm getPostResponseForm(Long id, Account account) {
        Optional<Post> postWrapper = postRepository.findById(id);
        Post post = postWrapper.get();
        String convertedPublishedDateTime = post.getPublishedDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        boolean authorCheck = post.isAuthor(new UserAccount(account));

        postRepository.plusViewCount(post);

        PostResponseForm postResponseForm = PostResponseForm.builder()
                .id(post.getId())
                .author(post.getAuthor().getNickname())
                .authorProfileImage(post.getAuthor().getProfileImage())
                .title(post.getTitle())
                .content(post.getContent())
                .postCategory(post.getPostCategory())
                .viewCount(post.getViewCount())
                .commentCount(post.getCommentCount())
                .publishedDateTime(convertedPublishedDateTime)
                .authorChecked(authorCheck)
                .build();

        return postResponseForm;
    }

    @Transactional(readOnly = true)
    public Page<BoardResponseForm> selectPostList(String boardCategory, String category, String searchType, String searchKeyword, Pageable pageable) {
        if (StringUtils.hasText(searchKeyword)) {
            return postRepository.selectPostListWithKeyword(boardCategory, category, searchType, searchKeyword, pageable);
        } else {
            return postRepository.selectPostList(boardCategory, category, pageable);
        }
    }

    @Transactional(readOnly = true)
    public Page<ProfilePostResponseForm> selectProfilePostList(String author, Pageable pageable) {
        return postRepository.selectProfilePostList(author, pageable);
    }

    @Transactional(readOnly = true)
    public Post getPostToUpdate(Account account, Long postId) {
        Post post = this.getPost(postId);
        if (!account.isAuthor(post)) {
            throw new AccessDeniedException("작성자만 게시글 수정이 가능합니다.");
        }

        return post;
    }

    @Transactional(readOnly = true)
    public Post getPostToDelete(Account account, Long postId) {
        Post post = this.getPost(postId);
        if (!account.isAuthor(post)) {
            throw new AccessDeniedException("작성자만 게시글 삭제가 가능합니다.");
        }

        return post;
    }

    @Transactional(readOnly = true)
    public Post getPost(Long postId) {
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

    public void deletePost(Post post) {
        postRepository.delete(post);
    }

}
