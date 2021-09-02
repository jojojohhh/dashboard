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
        1. Consumer가 Request Token 발급을 요청하고 Service Provider가 Request Token을 발급
        2. Service Provider는 Request Token으로 사용할 oauth_token과 oauth_token_secret을 전달,
           Access Token을 요청할 때는 oauth_token_secret을 사용한다.
        3. Consumer는 oauth_token을 이용해 Service Provider가 정해놓은 사용자 인증 페이지를 User에게 보여주도록 한다.
        4. User는 사용자 인증 페이지를 통해 인증을 마치면 Consumer가 oauth_callback에 지정한 URL로 리다이렉트 한다. 
           이때 Service Provider는 새로운 oauth_token과 oauth_verifier를 Consumer에게 전달한다. 
           이 값들은 Access Token을 요청할 때 사용한다.
        5. Access Token 발급을 요청할 때는 Consumer Secret Key에 oauth_token_secret을 결합하여 oauth_token_sercret을 생성한다.
           각 매개변수를 상황에 맞게 정의한 뒤 Access Token을 요청하면 oauth_token과 oauth_token_secret을 전달 받는다.
        6. User는 Access Token을 이용하여 서비스 정보 요청

## CommonOAuth2Provider


## OAuth2를 이용한 로그인 및 회원가입
    - 로그인 및 회원가입 과정
        1. GitLab oauth2를 이용해 인증
        2. response 데이터에 존재하는 email으로 중복 조회
            2-1. 중복이 존재하지 않는다면 회원가입 페이지로 리다이렉트
        3. 중복이 존재한다면 해당 User DB에 access token 값을 저장 후 메인 페이지로 리다이렉트
            