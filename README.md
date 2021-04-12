# Dashboard
이 프로젝트는 Spring Boot로 개발하는 Gitlab API기반 대시보드 입니다.

## 할 일
  1. Spring Security5를 이용한 로그인 처리
  2. ~로그인 form 만들기~
  3. ~회원가입 기능 추가~
      - 회원가입 form에서 post요청시 Controller에서 받는 데이터의 ```content-type```은 ```'application/x-www-form-urlencoded;set-UTF-8'```
      - Controller에서 ```@RequestBody```로 데이터를 dto에 받기 때문에 ```content-type```은 ```JSON``` 이어야 함
      - 이로 인해서 서버는 415에러 발생
