# BOMNAR Framework

> 적용 구성 요소
1. spring boot 3.3.4
- security
- jpa
- thymeleaf(layout 포함)
- charisma template
- swagger(적용중)
- jwt(적용중)

---

##spring boot 3.3.4

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

##security
```

public class SecurityConfig { 

 -> 페이지별 권한 처리, formLogin 적용, 401 403 관련 예외처리 적용
 -> 401 처리시 api , page 구분 적용
 -> CustomLoginException를 통해 로그인 시 조건별 메세지 노출 하도록 처리
 -> 로그인 메세지 사용자 화면 표시 위해 session 사용하여 노출 처리 - 추후 다른 방식으로 변경 예정 , session 사용은 별로로 보여서... 이슈는 없어보이기는 하지만..

```


