# Dashboard
이 프로젝트는 Spring Boot로 개발하는 Gitlab API기반 대시보드 입니다.

## 할 일
  1. ~Spring Security5를 이용한 로그인 처리~
      - _csrf.token 적용
  2. ~로그인 form 만들기~
  3. ~회원가입 기능 추가~
      - 회원가입 form에서 post요청시 Controller에서 받는 데이터의 ```content-type: 'application/x-www-form-urlencoded;set-UTF-8'```
      - Controller에서 ```@RequestBody```로 데이터를 dto에 매핑 시키기 위해서는 ```content-type: 'application/json'``` 이어야 함
      - ```content-type: 'application/json'```으로 전송 시키기 위해서는 jQuery를 사용해 ajax 통신이 필요 
      - Controller에서 ```@RequestBody UserDto userdto``` 형식으로 데이터를 매핑 시킬 수 있음
      - ```content-type: 'application/x-www-form-urlencoded;set-UTF-8'``` 으로 전송 시키는 경우 ```@ModelAttribute UserDto userdto```와 같이 사용가능
  4. 사용할 무료 대시보드 수정 및 적용하기
      - 출처: [Admin-LTE](https://github.com/ColorlibHQ/AdminLTE)
  5. gitlab 소셜 로그인 기능 추가하기
      - gitlab oauth2를 이용한 로그인 인증처리 (https://docs.gitlab.com/ee/api/oauth2.html)
        - gitlab 인증 요청을 보내는 API ```/auth/gitlab/authorize``` 의 내부 로직
          - ```https://gitlab.example.com/oauth/authorize?client_id=APP_ID&redirect_uri=REDIRECT_URI&response_type=code&state=STATE&scope=REQUESTED_SCOPES&code_challenge=CODE_CHALLENGE&code_challenge_method=S256``` 와 같이 gitlab에 인증 요청을 보내고 redirect Uri으로 결과를 받음
        - gitlab 인증 결과를 받는 API ```/auth/gitlab/callback``` 
  6. api 작성하기
