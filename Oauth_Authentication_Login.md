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
    - 네이버, 깃허브, 구글에 대한 oauth2 login 인증을 할 수 있도록 CommonOAuth2Provider가 자동으로 매핑해준다.
    - GitLab은 지원을 하지 않기때문에 application-oauth.yml 파일을 아래와 같이 설정한다.
        ```
            spring:
                security:
                    oauth2:
                        client:
                            registration:
                                gitlab:
                                    client-id: ClientID
                                    client-secret: ClientSecret
                                    redirect-uri: '{baseUrl}/login/oauth2/code/gitlab'
                                    authorization-grant-type: authorization_code
                                    scope:
                                        - api
                                        - read_user
                                        - read_repository
                                        - write_repository
                                    client-name: GitLab
                            provider:
                                gitlab:
                                    authorization-uri: https://gitlab.com/oauth/authorize
                                    token-uri: https://gitlab.com/oauth/token
                                    user-info-uri: https://gitlab.com/api/v4/user
                                    jwk-set-uri: https://gitlab.com/oauth/discovery/keys
                                    user-name-attribute: username
        ```

## GitLab OAuth2 Login Error
    - GitLab 인증 페이지인 ```https://gitlab.com/oauth/authorize```에 접근 시 ERR_SSL_PROTOCOL_ERROR 발생
        - 참고 : https://forum.mendix.com/link/questions/93519
        - 앱 https 적용하여도 동일한 에러 발생
            -> 원인 : authorization-uri를 http가 아닌 https로 접근하도록 하게 되어서 문제가 발생함.
            -> 해결방법 : https를 http로 모두 변경

            