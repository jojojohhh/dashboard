# OAuth 인증과 로그인에 대하여
    참조 : [NaverD2](https://d2.naver.com/helloworld/24942)

## 인증(Authentication) 과 허가(Authorization)
    - 인증이란 접근하는 사용자에 대하여 올바른 사용자인지 확인하는 절차 
    - 허가란 어떠한 사용자가 요청하는 것을 실행하는 것에 대한 권한을 확인하는 절차

## OpenID
    - OpenID의 주요 목적은 인증이다. OpenID를 사용한다는 것은 로그인하는 행동과 같으며, 
      OpenID Provider에서 사용자의 인증 과정을 처리한다.

## OAuth
    - OAuth에서의 Auth는 인증과 허가를 포함하고 있다. 하지만 주된 목적은 허가이다.
    - OAuth의 인증 과정
        1. User는 Consumer에게 Request Token의 발급을 요청
        2. Consumer는 Service Provider의 사용자 인증 페이지를 호출
        3. User는 로그인 완료
        4. Service Provider는 사용자의 권한 요청 및 수락
        5. Consumer는 Access Token을 발급
        6. User는 Access Token을 이용하여 서비스 정보 요청