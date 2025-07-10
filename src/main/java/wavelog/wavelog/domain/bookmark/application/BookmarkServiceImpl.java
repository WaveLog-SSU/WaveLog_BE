package wavelog.wavelog.domain.bookmark.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wavelog.wavelog.domain.bookmark.domain.entity.Bookmark;
import wavelog.wavelog.domain.bookmark.domain.repository.BookmarkRepository;
import wavelog.wavelog.domain.bookmark.dto.AddRequest;
import wavelog.wavelog.domain.bookmark.dto.AddResponse;
import wavelog.wavelog.domain.bookmark.dto.DeleteResponse;
import wavelog.wavelog.domain.diary.domain.entity.Diary;
import wavelog.wavelog.domain.diary.domain.repository.DiaryRepository;
import wavelog.wavelog.domain.member.domain.entity.Member;
import wavelog.wavelog.domain.member.domain.repository.MemberRepository;

import java.awt.print.Book;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;

    @Override
    public AddResponse add(AddRequest request, Long memberId) {
        System.out.println("BookmarkService.add 메서드 진입 - diaryId: " + request.getDiaryId() + ", memberId: " + memberId); // 로그 추가
        log.info("BookmarkService 진입");
        Diary diary = diaryRepository.findById(request.getDiaryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 다이어리입니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."));

        if(bookmarkRepository.existsByDiaryAndMember(diary, member)) {
            throw new IllegalArgumentException("이미 북마크를 했습니다.");
        }

        Bookmark bookmark = new Bookmark();
        bookmark.add(diary, member);
        bookmarkRepository.save(bookmark);

        // 북마크 수 증가
        diary.addBookmarkCount();
        diaryRepository.save(diary);

        return AddResponse.builder()
                .id(bookmark.getId())
                .diaryId(diary.getId())
                .memberId(member.getId())
                .bookmarkCount(diary.getBookmarkCount())
                .bookmarkCheck(true)
                .build();
    }

    @Override
    public DeleteResponse delete(Long id, Long memberId) {
        Bookmark bookmark = bookmarkRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 북마크입니다."));


        if(!bookmark.getMember().getId().equals(memberId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인의 북마크만 삭제할 수 있습니다.");
        }
        Diary diary = bookmark.getDiary();

        // 북마크 수 감소 메서드 호출 후 저장
        diary.deleteBookmarkCount();
        diaryRepository.save(diary);

        bookmarkRepository.delete(bookmark);

        return DeleteResponse.builder()
                .id(id)
                .message("북마크를 삭제하였습니다.")
                .bookmarkCheck(false)
                .bookmarkCount(diary.getBookmarkCount())
                .build();
    }
}
