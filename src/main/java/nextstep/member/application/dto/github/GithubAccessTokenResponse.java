package nextstep.member.application;

public class GithubAccessTokenResponse {
    private final String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public GithubAccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
