package com.hkcommunity.modules.like;

import com.hkcommunity.modules.like.form.LikeForm;
import com.hkcommunity.modules.like.form.LikeResponseForm;
import com.hkcommunity.modules.post.Post;
import com.hkcommunity.modules.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public boolean pushLikeButton(LikeForm likeForm) {
        likeRepository.existsLikeByAccountAndPost(likeForm.getAccount().getId(), likeForm.getPost().getId())
                .ifPresentOrElse(
                        like -> {
                            Post post = getPostByLikeForm(likeForm);
                            post.minusLikeCount(post.getLikeCount());
                            post.minusViewCount(post.getViewCount());
                            likeRepository.deleteById(like.getId());
                            //postRepository.save(post);
                        }, () -> {
                            Post post = getPostByLikeForm(likeForm);
                            post.plusLikeCount(post.getLikeCount());
                            post.minusViewCount(post.getViewCount());
                            likeRepository.save(new Like(likeForm.getAccount(), post));
                            //postRepository.save(post);
                        });

        return true;
    }

    @Transactional(readOnly = true)
    public Post getPostByLikeForm(LikeForm likeForm) {
        return postRepository.findById(likeForm.getPost().getId()).orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public LikeResponseForm getLikeInfo(LikeForm likeForm) {
        Long postLikeNum = getPostLikeNum(likeForm);
        boolean pushedCheck = checkPushedLike(likeForm);

        return new LikeResponseForm(postLikeNum, pushedCheck);
    }

    @Transactional(readOnly = true)
    public boolean checkPushedLike(LikeForm likeForm) {
        return likeRepository.existsLikeByAccountAndPost(likeForm.getAccount().getId(), likeForm.getPost().getId()).isPresent();
    }

    @Transactional(readOnly = true)
    public Long getPostLikeNum(LikeForm likeForm) {
        return likeRepository.findLikeNum(likeForm.getPost().getId());
    }

    public Post getPost(Long postId) {
        Optional<Post> postWrapped = this.postRepository.findById(postId);
        if (postWrapped == null) {
            throw new IllegalArgumentException(postId + "에 해당하는 게시글이 없습니다.");
        }

        Post post = postWrapped.get();
        return post;
    }
}
