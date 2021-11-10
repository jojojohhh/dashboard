## Dashboard

### Description
  GitLab API와 Spring Boot를 이용한 Dashboard
  
### Environment
  |도구|Version|
  |:---:|:---:|
  |Language|Java 1.8|
  |IDE|IntelliJ IDE|
  |Framwork|Spring boot 2.4.4|
  |DB|H2|
  |Build|gradle 6.8.3|
  |OS|Window 10|
  
### GitLab Oauth2 로그인 인증
  - application-oauth.yml 에 등록
  ```
    spring:
    security:
      oauth2:
        client:
          registration:
            gitlab:
              client-id: your client-id
              client-secret: your client-secret
              redirect-uri: '{baseUrl}/login/oauth2/code/{registrationId}'
              authorization-grant-type: authorization_code
              scope:
                - api
                - read_user
                - read_repository
                - write_repository
              client-name: GitLab
          provider:
            gitlab:
              authorization-uri: http://your.gitlab.com/oauth/authorize
              token-uri: http://your.gitlab.com/oauth/token
              user-info-uri: http://your.gitlab.com/api/v4/user
              jwk-set-uri: http://your.gitlab.com/oauth/discovery/keys
              user-name-attribute: username
  ```
  - SecurityConfiguration에서 oauth2Login과 [Oauth2UserService](https://github.com/jojojohhh/dashboard/blob/master/src/main/java/com/swlab/dashboard/config/security/oauth/CustomOAuth2UserService.java)를 설정해준다.
  ```
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .oauth2Login()
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService)
                .and()
                    .redirectionEndpoint()
                    .baseUri("/login/oauth2/code/*")
                .and()
                    .loginPage("/login")
                    .defaultSuccessUrl("/home", true);
  ```
  - 클라이언트가 웹 에서 localhost/oauth2/authorization/gitlab 으로 접근시 서버는 application-oauth.yml 에 등록된 GitLab 인증 페이지로 이동한다.
  - GitLab에서 로그인 인증을 하고 나면 ```redirect-uri: '{baseUrl}/login/oauth2/code/{registrationId}'``` 으로 redirect 되며 Access Token을 건네받고 유저를 생성하고 세션에 등록시킨다.

### GitLab API
  - GitLab4J 라이브러리를 사용하여 API를 호출
  ```
    public GitLabApi getGitLabApi() {
        gitLabApi = new GitLabApi(gitlabProperties.getUrl(), gitlabProperties.getPersonalAccessToken());
        gitLabApi.setRequestTimeout(1000, 5000);
        return gitLabApi;
    }
  ```
  - 추후 Oauth2 인증을 통해 받은 AccessToken으로 Api를 호출 하도록 변경
  - 상세 : [GitLab4j](https://github.com/gitlab4j/gitlab4j-api)

### Google Calendar API  
  - [Google API Developer Console](https://console.developers.google.com) 에서 서비스 계정 생성 후 calendar-credentials.json 을 이용하여 API 호출
  - fullcalendar.js 와 API Key를 이용하여 캘린더 조회
  - 추후 하나의 방법으로 통합 후 일정 추가 기능 추가 예정
  
