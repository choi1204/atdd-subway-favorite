package nextstep.favorites.application;

import nextstep.common.exception.BusinessException;
import nextstep.common.exception.ErrorResponse;
import nextstep.common.exception.LoginException;
import nextstep.favorites.application.dto.FavoriteRequest;
import nextstep.favorites.application.dto.FavoriteResponse;
import nextstep.favorites.domain.Favorite;
import nextstep.favorites.domain.FavoriteRepository;
import nextstep.member.application.TokenService;
import nextstep.member.application.dto.MemberResponse;
import nextstep.member.domain.Member;
import nextstep.member.domain.MemberRepository;
import nextstep.subway.domain.Station;
import nextstep.subway.domain.StationRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    private final StationRepository stationRepository;

    private final MemberRepository memberRepository;


    public FavoriteService(FavoriteRepository favoriteRepository, StationRepository stationRepository, MemberRepository memberRepository) {
        this.favoriteRepository = favoriteRepository;
        this.stationRepository = stationRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long addFavorite(Long memberId, FavoriteRequest favoriteRequest) {
        Member member = getMemberById(memberId);
        Station targetStation = getStationById(favoriteRequest.getTarget());
        Station sourceStation = getStationById(favoriteRequest.getSource());
        Favorite favorite = new Favorite(member, targetStation, sourceStation);
        favoriteRepository.save(favorite);

        member.addFavorite(favorite);
        return favorite.getId();
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new LoginException(ErrorResponse.INVALID_TOKEN_VALUE));
    }
    private Station getStationById(Long stationId) {
        return stationRepository.findById(stationId).orElseThrow(() -> new BusinessException(ErrorResponse.NOT_FOUND_EMAIL));
    }
}