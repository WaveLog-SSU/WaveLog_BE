package wavelog.wavelog.domain.comment.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wavelog.wavelog.domain.comment.domain.entity.Comment;
import wavelog.wavelog.domain.comment.domain.repository.CommentRepository;
import wavelog.wavelog.domain.comment.dto.CommentRequest;
import wavelog.wavelog.domain.comment.dto.CommentResponse;
import wavelog.wavelog.domain.diary.domain.entity.Diary;
import wavelog.wavelog.domain.diary.domain.repository.DiaryRepository;
import wavelog.wavelog.domain.member.domain.entity.Member;
import wavelog.wavelog.domain.member.domain.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;

    @Override
    @Transactional
    public CommentResponse createComment(CommentRequest request, Long memberId, Long diaryId) {

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("다이어리를 찾을 수 없습니다. id=" + diaryId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. id=" + memberId));

        Comment parent = null;
        if (request.getParentCommentId() != null) {
            parent = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글을 찾을 수 없습니다. id=" + request.getParentCommentId()));
            if (parent.getParentComment() != null) {
                throw new IllegalArgumentException("대댓글의 대댓글은 허용되지 않습니다.");
            }
        }

        Comment comment = Comment.builder()
                .content(request.getContent())
                .member(member)
                .diary(diary)
                .parentComment(parent)
                .build();

        commentRepository.save(comment);

        CommentResponse response =CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .parentCommentId(
                        comment.getParentComment() != null
                                ? comment.getParentComment().getId()
                                : null
                )
                .name(comment.getMember().getName())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();

        return response;
    }


}
