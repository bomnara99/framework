# BOMNAR Framework

> 적용 구성 요소
1. spring boot 3.3.4
- security
- jpa
- thymeleaf(layout 포함)
- charisma template
- swagger
- jwt

---

## spring boot 3.3.4

[start.spring.io](https://start.spring.io)


```
https://start.spring.io
 > Project : Gradle - Groovy 
 > Language : Java
 > Spring Boot : 3.4.4
 > ADD DEPENDENCIES
 	SPRING WEB
 	Thymeleaf
 	Spirng Security
 	Spring Data JPA
 	H2 Database
 	...

```

## security
```

public class SecurityConfig { 

 -> 페이지별 권한 처리, formLogin 적용, 401 403 관련 예외처리 적용
 -> 401 처리시 api , page 구분 적용
 -> CustomLoginException를 통해 로그인 시 조건별 메세지 노출 하도록 처리
 -> 로그인 메세지 사용자 화면 표시 위해 session 사용하여 노출 처리 - 추후 다른 방식으로 변경 예정 , session 사용은 별로로 보여서... 이슈는 없어보이기는 하지만..
 -> jwtAuthenticationFilter 생성해서 jwt 토큰 필터를 적용함
    ->if (request.getRequestURI().startsWith("/api/saleData")) { 
    조건식으로 특정 api만 적용될수 있도록 하였는데... 간단하고 적용이 쉬어보임  
```

## jpa

```
단순 select 와 paging 정도만 적용
dto, domain, repository 등에 대한 정리를 다시 해야겠음

```

## thymeleaf(layout 포함)

```
깊에 파지느 않았지만.. ui적 요소나 등등을 고려해봤을때
layout 경우 예전 sitemash와 매우 유사한거로 보이고
view 단 은 jstl 과 비슷한 느낌임 
단순 문법 몇개만 익히면 쉽게 적응할수 있도록 잘 만들어진거가 같음
```

