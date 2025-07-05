package wavelog.wavelog.domain.like.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wavelog.wavelog.domain.diary.application.DiaryService;
import wavelog.wavelog.domain.diary.domain.entity.Diary;
import wavelog.wavelog.domain.diary.domain.repository.DiaryRepository;
import wavelog.wavelog.domain.like.domain.entity.Like;
import wavelog.wavelog.domain.like.domain.repository.LikeRepository;
import wavelog.wavelog.domain.like.dto.AddRequest;
import wavelog.wavelog.domain.like.dto.AddResponse;
import wavelog.wavelog.domain.like.dto.DeleteRequest;
import wavelog.wavelog.domain.like.dto.DeleteResponse;
import wavelog.wavelog.domain.member.domain.entity.Member;
import wavelog.wavelog.domain.member.domain.repository.MemberRepository;


@Service
@RequiredArgsConstructor
@Transactional
public class LikeServiceImpl implements LikeService{
    private final LikeRepository likeRepository;
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;

    @Override
    public AddResponse add(AddRequest request, Long memberId) {
        Diary diary = diaryRepository.findById(request.getDiaryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 다이어리입니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if(likeRepository.existsByDiaryAndMember(diary, member)) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }

        Like like = new Like();
        like.add(diary, member);
        likeRepository.save(like);

        // 좋아요 수 증가 메서드 호출 후 저장
        diary.addLikeCount();
        diaryRepository.save(diary);

        return AddResponse.builder()
                .diaryId(diary.getId())
                .likeCount(diary.getLikeCount())
                .likeCheck(true)
                .build();
    }


    @Override
    public DeleteResponse delete(DeleteRequest request) {
        Like like = likeRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 좋아요입니다."));

        Diary diary = like.getDiary();

        // 좋아요 수 감소 메서드 호출 후 저장
        diary.deleteLikeCount();
        diaryRepository.save(diary);

        return DeleteResponse.builder()
                .message("좋아요를 삭제하였습니다.")
                .likeCheck(false)
                .build();
    }

}
