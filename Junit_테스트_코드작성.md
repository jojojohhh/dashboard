# Junit5를 사용하여 테스트 코드 작성시에 만난 문제점들과 해결방법

## @SpringBootTest

## @WebMvcTest
    1. ApiController
        1. 테스트 실행 시 AccessDeniedHandler를 구현한 CustomWebAccessDeniedHandler 클래스의 Bean을 찾을 수 없다는 에러가 발생함
            -> 원인 :
            -> 해결방법 : 

## @DataJpaTest
    1. UserRepositoryTest
        1. 테스트 실행 시 Bean으로 등록된 PasswordEncoder를 찾을 수 없다는 에러 발생
            -> 원인 : 
            -> 해결방법 : TestConfig 클래스를 생성하고 PasswordEncoder를 빈으로 등록해준 다음 
                        @Import(TestConfig.class)를 사용하여 해결
    
