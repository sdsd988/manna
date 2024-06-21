# 만나 Package Crud API 작성

### Requirements

- Package 생성, 조회, 수정, 삭제 API 개발
- 데이터 통신은 Json 형식
- 테스트 코드를 작성

### ERD
![img_6.png](img_6.png)

### Tech Stack

#### Server

- Java 17, Spring Boot 3.2, Spring MVC, JPA

#### DataBase

- local : Mysql, test : H2

#### Etc

- Gradle, Docker

#### Explain

- docker-compose 파일을 통해 local, test DB 환경을 설정할 수 있도록 작성하였습니다.
- 서비스 계층의 테스트 코드 작성을 통해 코드를 검증하였습니다.



#### Result

##### 테스트 코드 결과

![img_9.png](img_9.png)

##### Postman을 통한 테스트

- 패키지 생성
![img_1.png](img_1.png)트
![img_3.png](img_3.png)

- 패키지 수정
![img_2.png](img_2.png)

수정 후 조회
![img_4.png](img_4.png)

- 패키지 삭제
![img_5.png](img_5.png)

- 예외 처리
    - 존재하지 않는 패키지 조회
  ![img_8.png](img_8.png)
    - 존재하지 않는 패키지 수정
  ![img_7.png](img_7.png)









