# Dashboard
이 프로젝트는 Spring Boot로 개발하는 Gitlab API기반 대시보드 입니다.

## 할 일
  1. Spring Security5를 이용한 로그인 처리
  3. ~로그인 form 만들기~
  4. 회원가입 기능 추가
      - 회원가입 form에서 post요청시 Controller에서 받는 데이터의 ```content-type: 'application/x-www-form-urlencoded;set-UTF-8'```
      - Controller에서 ```@RequestBody```로 데이터를 dto에 매핑 시키기 위해서는 ```content-type: 'application/json'``` 이어야 함
      - ```content-type: 'application/json'```으로 전송 시키기 위해서는 jQuery를 사용해 ajax 통신이 필요 
      - Controller에서 ```@RequestBody UserDto userdto``` 형식으로 데이터를 매핑 시킬 수 있음
      - ```content-type: 'application/x-www-form-urlencoded;set-UTF-8'``` 으로 전송 시키는 경우 ```@ModelAttribute UserDto userdto```와 같이 사용가능
