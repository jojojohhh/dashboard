# Dashboard
이 프로젝트는 Spring Boot로 개발하는 Gitlab API기반 대시보드 입니다.

## 할 일
  1. Spring Security5를 이용한 로그인 처리
      - _csrf.token 적용
  2. 로그인 form 만들기
  3. 회원가입 기능 추가
      - 회원가입 form에서 post요청시 Controller에서 받는 데이터의 ```content-type: 'application/x-www-form-urlencoded;set-UTF-8'```
      - Controller에서 ```@RequestBody```로 데이터를 dto에 매핑 시키기 위해서는 ```content-type: 'application/json'``` 이어야 함
      - ```content-type: 'application/json'```으로 전송 시키기 위해서는 jQuery를 사용해 ajax 통신이 필요 
      - Controller에서 ```@RequestBody UserDto userdto``` 형식으로 데이터를 매핑 시킬 수 있음
      - ```content-type: 'application/x-www-form-urlencoded;set-UTF-8'``` 으로 전송 시키는 경우 ```@ModelAttribute UserDto userdto```와 같이 사용가능
  4. Repository
      - jpa 테스트 코드를 작성하는데 테스트 코드에 있지도 않은 passwordencoder의 빈을 찾을 수 없다는 에러가 계속 발생함
        - TestConfig 클래스를 생성하고 passwordencoder를 bean으로 등록한 뒤 Import 어노테이션을 이용해 TestConfig 클래스를 import하여 에러 해결
          - 왜 이러한 문제가 발생하는지 문제점 
      - ~~jpa 대신 jdbctemplate 사용해 직접 쿼리를 작성~~
        - jpa와 jdbc로 분리하여 작성
          - jpa와 jdbc로 분리하여 테스트 해보는 것은 공부가 부족함 jpa와 jdbc에 대한 적당한 공부 후 간단한 게시판 만들기로 테스트 하기
  5. gitlab 소셜 로그인 기능 추가하기
      - gitlab oauth2를 이용한 로그인 인증처리 (https://docs.gitlab.com/ee/api/oauth2.html)
        - gitlab 인증 요청을 보내는 API ```/auth/gitlab/authorize``` 의 내부 로직
          - ```https://gitlab.example.com/oauth/authorize?client_id=APP_ID&redirect_uri=REDIRECT_URI&response_type=code&state=STATE&scope=REQUESTED_SCOPES&code_challenge=CODE_CHALLENGE&code_challenge_method=S256``` 와 같이 gitlab에 인증 요청을 보내고 redirect Uri으로 결과를 받음
        - gitlab 인증 결과를 받는 API ```/auth/gitlab/callback``` 
  6. API 작성하기
      - GitLab4J API GitLab API Java Client (https://github.com/gitlab4j/gitlab4j-api) 라이브러리 의존성 추가
      - gitlab personalAccessToken, url을 application-gitlab.yml에 저장
      - GitLabProperties.class로 프로퍼티를 받아옴
      - OpenApi와 swagger-ui를 적용
      - 각 차트에 맞는 데이터를 불러오기 위한 GitLab API 호출시에 유저 id 또는 프로젝트 id 와 같은 정보가 필요함
  7. 예외처리
      - 각 종 예외에 대한 응답 구현
  8. 데이터베이스
      - MYSQL
  9. 사용할 무료 대시보드 수정 및 적용하기
      - 출처: [Admin-LTE](https://github.com/ColorlibHQ/AdminLTE)
