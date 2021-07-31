# Junit5를 사용하여 테스트 코드 작성시에 만난 문제점들과 해결방법

## @SpringBootTest
    - 통합 테스트시 사용되는 어노테이션 @SpringBootApplication 을 찾아간 뒤 모든 Bean을 스캔함

## @WebMvcTest
    1. ApiController
        1. 테스트 실행 시 AccessDeniedHandler를 구현한 CustomWebAccessDeniedHandler 클래스의 Bean을 찾을 수 없다는 에러가 발생함
            -> 원인 : SecurityConfiguration 에서 CustomWebAccessDeniedHandler 의 Bean을 주입 하는데 @WebMvcTest 를 사용한 테스트 시 CustomWebAccessDeniedHandler를 스캔하지 못함. 
            -> 해결방법 : excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {SecurityConfiguration.class}) 라는 코드를 추가하여 해결 
                -> 위 문제를 해결한 후 UserRepository Bean을 찾을 수 없다고 함.
                    -> 원인 : 앱 실행 시 테스트를 위한 user를 생성 해 두기 위해 DashboardApplication 클래스에 runner 라는 메서드를 빈으로 등록하였고 해당 메서드에서 UserRepository를 사용함
                    -> 해결방법 : 해당 runner 메서드를 없애거나, Testconfig에 UserRepository, UserRoleRepository, PasswordEncoder를 추가 해야함
                        -> 401 Unauthorized 에러 발생
                            -> 원인 : 해당 URL에 접근 하기위해서 권한이 필요
                            -> 해결방법 : 테스트 메서드에 @WithMockUser("User") 어노테이션 추가함으로써 USER 권한을 줌
                

## @DataJpaTest
    1. UserRepositoryTest
        1. 테스트 실행 시 Bean으로 등록된 PasswordEncoder를 찾을 수 없다는 에러 발생
            -> 원인 : 앱 실행 시 테스트를 위한 user를 생성 해 두기 위해 DashboardApplication 클래스에 runner 라는 메서드를 빈으로 등록하였고 해당 메서드에서 user의 비밀번호 암호화를 위해 PasswordEncoder를 사용함 
            -> 해결방법 : TestConfig 클래스를 생성하고 PasswordEncoder를 빈으로 등록해준 다음 
                        @Import(TestConfig.class)를 사용하여 해결
    
## 단위 테스트시 Bean could not be found 에러에 대해서
    각 단위 테스트를 할 때 스캔하는 범위가 다 다르기 때문에 스캔하는 도중 @Autowired로 주입되는 클래스가 스캔하는 범위가 아니라면 해당 에러가 발생한다.
    @DataJpaTest 에서 1번 문제와 @WebMvcTest의 1번 문제는 앱 실행시 user를 생성하는 메서드에서 UserRepository, UserRoleRepository, PasswordEncoder를 
    사용하기 때문에 에러가 발생한 이유가 해당 테스트의 스캔 범위에 벗어난 클래스를 사용하기 때문에 발생한 것 같아 보인다. 
    