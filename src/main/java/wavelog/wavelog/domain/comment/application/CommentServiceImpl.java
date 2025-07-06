package wavelog.wavelog.domain.comment.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wavelog.wavelog.domain.comment.domain.entity.Comment;
import wavelog.wavelog.domain.comment.domain.repository.CommentRepository;
import wavelog.wavelog.domain.comment.dto.CreateCommentRequest;
import wavelog.wavelog.domain.comment.dto.CreateCommentResponse;
import wavelog.wavelog.domain.comment.dto.GetCommentResponse;
import wavelog.wavelog.domain.diary.domain.entity.Diary;
import wavelog.wavelog.domain.diary.domain.repository.DiaryRepository;
import wavelog.wavelog.domain.member.domain.entity.Member;
import wavelog.wavelog.domain.member.domain.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;

    @Override
    @Transactional
    public CreateCommentResponse createComment(CreateCommentRequest request, Long memberId, Long diaryId) {

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

        CreateCommentResponse response = CreateCommentResponse.builder()
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


    @Override
    @Transactional(readOnly = true)
    public List<GetCommentResponse> getComment(Long diaryId) {
        // 1) 다이어리 ID 로 모든 댓글(최상위+대댓글) 조회
        List<Comment> all = commentRepository.findAllByDiary_IdOrderByCreatedAtAsc(diaryId);

        // 2) 엔티티 → DTO 변환
        List<GetCommentResponse> response = all.stream()
                .map(comment -> GetCommentResponse.builder()
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
                        // replies 는 빈 리스트로 초기화
                        .replies(new ArrayList<>())
                        .build())
                .collect(Collectors.toList());

        // 3) parentId 별로 그룹핑
        Map<Long, List<GetCommentResponse>> byParent = response.stream()
                .filter(dto -> dto.getParentCommentId() != null)
                .collect(Collectors.groupingBy(GetCommentResponse::getParentCommentId));

        // 4) 최상위 댓글만 남기고, replies 세팅
        return response.stream()
                .filter(dto -> dto.getParentCommentId() == null)
                .peek(dto -> {
                    List<GetCommentResponse> children = byParent.get(dto.getId());
                    if (children != null) {
                        dto.getReplies().addAll(children);
                    }
                })
                .collect(Collectors.toList());
    }


}
