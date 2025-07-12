package wavelog.wavelog.domain.diary.application;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wavelog.wavelog.domain.bookmark.domain.repository.BookmarkRepository;
import wavelog.wavelog.domain.diary.domain.entity.Diary;
import wavelog.wavelog.domain.diary.domain.repository.DiaryRepository;
import wavelog.wavelog.domain.diary.dto.*;
import wavelog.wavelog.domain.diaryHashtag.domain.entity.DiaryHashtag;
import wavelog.wavelog.domain.diaryHashtag.domain.repository.DiaryHashtagRepository;
import wavelog.wavelog.domain.hashtag.domain.entity.Hashtag;
import wavelog.wavelog.domain.hashtag.domain.repository.HashtagRepository;
import wavelog.wavelog.domain.like.domain.repository.LikeRepository;
import wavelog.wavelog.domain.member.domain.entity.Member;
import wavelog.wavelog.domain.member.domain.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class DiaryServiceImpl implements DiaryService{
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final HashtagRepository hashtagRepository;
    private final DiaryHashtagRepository diaryHashtagRepository;


    @Override
    public List<ViewResponse> listByDateAndMember(String date, Long memberId) {
        List<Diary> diaries;

        if (date == null || date.isEmpty()) {
            diaries = diaryRepository.findByMemberId(memberId);
        } else {
            diaries = diaryRepository.findByCreatedAtDateAndMemberId(date, memberId);
        }

        return diaries.stream()
                .map(this::convertToViewResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CountDiaryResponse countDiary(int year, int month, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."));

        long cnt = diaryRepository.countDistinctByMemberAndYearAndMonth(memberId, year, month);
        return CountDiaryResponse.builder()
                .count(cnt)
                .build();
    }

    @Override
    public List<ViewResponse> listByMemberId(Long memberId) {
        List<Diary> diaries = diaryRepository.findByMemberId(memberId);

        return diaries.stream()
                .map(this::convertToViewResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ViewResponse> search(String query) {
        List<Diary> diaries = diaryRepository.findByTitleContainingIgnoreCase(query);

        return diaries.stream()
                .map(this::convertToViewResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CreateResponse create(CreateRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."));

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

        List<String> tags = request.getHashtags() != null ? request.getHashtags() : new ArrayList<>();
        for (String tag : tags) {
            Hashtag hashtag = hashtagRepository.findByTag(tag)
                    .orElseGet(() -> hashtagRepository.save(Hashtag.builder().tag(tag).build()));

            boolean exists = diaryHashtagRepository.findByDiaryIdAndHashtagId(diary.getId(), hashtag.getId()).isPresent();
            if (!exists) {
                DiaryHashtag diaryHashtag = DiaryHashtag.builder()
                        .diary(diary)
                        .hashtag(hashtag)
                        .build();

                diaryHashtagRepository.save(diaryHashtag);
            }
        }
        // DTO 반환
        return CreateResponse.builder()
                .id(diary.getId())
                .title(diary.getTitle())
                .code(diary.getCode())
                .content(diary.getContent())
                .createdAt(diary.getCreatedAt())
                .hashtags(tags)
                .wavelogId(diary.getMember().getWavelogId())
                .nickname(diary.getMember().getNickname())
                .build();
    }

    @Override
    public DeleteResponse delete(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 다이어리입니다."));
        diaryRepository.delete(diary);
        // DTO 반환
        return DeleteResponse.builder()
                .message("다이어리가 삭제되었습니다.")
                .build();
    }

    @Override
    public UpdateResponse update(Long diaryId, UpdateRequest request) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 다이어리입니다."));
        // Diary Entity에서 만들어놓은 update 메서드
        diary.update(
                request.getTitle(),
                request.getCode(),
                request.getContent(),
                request.getCategory()
        );

        List<String> newTags = request.getHashtags() != null ? request.getHashtags() : new ArrayList<>();

        List<DiaryHashtag> existingRelations = diary.getDiaryHashtags();


        for(String tag : newTags) {
            boolean alreadyLinked = existingRelations.stream()
                    .anyMatch(dh -> dh.getHashtag().getTag().equals(tag));
            if(!alreadyLinked) {
                Hashtag hashtag = hashtagRepository.findByTag(tag)
                        .orElseGet(() -> hashtagRepository.save(Hashtag.builder().tag(tag).build()));
                DiaryHashtag diaryHashtag = DiaryHashtag.builder()
                        .diary(diary)
                        .hashtag(hashtag)
                        .build();

                diaryHashtagRepository.save(diaryHashtag);
            }
        }

        for(DiaryHashtag dh : new ArrayList<>(existingRelations)) {
            if(!newTags.contains(dh.getHashtag().getTag())) {
                diaryHashtagRepository.delete(dh);

                boolean isLinkedElsewhere = diaryHashtagRepository.existsByHashtagId(dh.getHashtag().getId());
                if(!isLinkedElsewhere) {
                    hashtagRepository.delete(dh.getHashtag());
                }
            }
        }
        diaryRepository.save(diary);
        // DTO 반환
        return UpdateResponse.builder()
                .id(diary.getId())
                .message("다이어리가 수정되었습니다.")
                .build();
    }

    @Override
    public ViewResponse view(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 다이어리입니다."));

        List<String> hashtagTags = diary.getDiaryHashtags().stream()
                .map(dh -> dh.getHashtag().getTag())
                .collect(Collectors.toList());
        // DTO 반환
        return ViewResponse.builder()
                .id(diary.getId())
                .title(diary.getTitle())
                .code(diary.getCode())
                .content(diary.getContent())
                .hashtags(hashtagTags)
                .wavelogId(diary.getMember().getWavelogId())
                .nickname(diary.getMember().getNickname())
                .profileImageUrl(diary.getMember().getProfileImageUrl())
                .createdAt(diary.getCreatedAt())
                .viewCount(diary.getViewCount())
                .likeCount(diary.getLikeCount())
                .build();
    }

    private ViewResponse convertToViewResponse(Diary diary) {
        List<String> hashtagTags = diary.getDiaryHashtags().stream()
                .map(dh -> dh.getHashtag().getTag())
                .collect(Collectors.toList());

        return ViewResponse.builder()
                .id(diary.getId())
                .title(diary.getTitle())
                .code(diary.getCode())
                .content(diary.getContent())
                .hashtags(hashtagTags)
                .category(diary.getCategory())
                .wavelogId(diary.getMember().getWavelogId())
                .nickname(diary.getMember().getNickname())
                .profileImageUrl(diary.getMember().getProfileImageUrl())
                .createdAt(diary.getCreatedAt())
                .viewCount(diary.getViewCount())
                .likeCount(diary.getLikeCount())
                .build();
    }

}
