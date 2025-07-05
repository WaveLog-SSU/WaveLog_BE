package wavelog.wavelog.domain.diary.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wavelog.wavelog.domain.diary.domain.entity.Diary;
import wavelog.wavelog.domain.diary.domain.repository.DiaryRepository;
import wavelog.wavelog.domain.diary.dto.*;
import wavelog.wavelog.domain.member.domain.entity.Member;
import wavelog.wavelog.domain.member.domain.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DiaryServiceImpl implements DiaryService{
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;

    @Override
    public CreateResponse create(CreateRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Diary diary = Diary.builder()
                .title(request.getTitle())
                .code(request.getCode())
                .content(request.getContent())
                .category(request.getCategory())
                .likeCount(0)
                .viewCount(0)
                .member(member)
                .build();
        diaryRepository.save(diary);
        // DTO 반환
        return CreateResponse.builder()
                .id(diary.getId())
                .title(diary.getTitle())
                .code(diary.getCode())
                .content(diary.getContent())
                .createdAt(diary.getCreatedAt())
                .hashtags(new ArrayList<>())
                .wavelogId(diary.getMember().getWavelogId())
                .nickname(diary.getMember().getNickname())
                .build();
    }

    @Override
    public DeleteResponse delete(DeleteRequest request) {
        Diary diary = diaryRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 다이어리입니다."));
        diaryRepository.delete(diary);
        // DTO 반환
        return DeleteResponse.builder()
                .id(diary.getId())
                .message("다이어리가 삭제되었습니다.")
                .build();
    }

    @Override
    public UpdateResponse update(UpdateRequest request) {
        Diary diary = diaryRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 다이어리입니다."));
        // Diary Entity에서 만들어놓은 update 메서드
        diary.update(
                request.getTitle(),
                request.getCode(),
                request.getContent(),
                request.getCategory()
        );
        diaryRepository.save(diary);
        // DTO 반환
        return UpdateResponse.builder()
                .id(diary.getId())
                .message("다이어리가 수정되었습니다.")
                .build();
    }

    @Override
    public ViewResponse view(ViewRequest request) {
        Diary diary = diaryRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 다이어리입니다."));

        // DTO 반환
        return ViewResponse.builder()
                .id(diary.getId())
                .title(diary.getTitle())
                .code(diary.getCode())
                .content(diary.getContent())
                .hashtags(new ArrayList<>())
                .wavelogId(diary.getMember().getWavelogId())
                .nickname(diary.getMember().getNickname())
                .profileImageUrl(diary.getMember().getProfileImageUrl())
                .createdAt(diary.getCreatedAt())
                .viewCount(diary.getViewCount())
                .likeCount(diary.getLikeCount())
                .build();
    }
}
