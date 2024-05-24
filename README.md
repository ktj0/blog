# 블로그 프로젝트

이 프로젝트는 사용자가 게시글과 댓글을 작성할 수 있는 블로그의 백엔드 서버입니다.

</br>

## ERD
![blog erd](https://github.com/ktj0/blog/assets/130539892/87938a82-28f3-4dcb-becc-f96da6cdffeb)

</br>

## API 명세서

### User

#### 회원가입
- **URL:** `localhost:8080/api/user/signup`
- **메소드:** `POST`
- **요청 바디:**
  ```json
   {
    "username": "John",
    "password": "12345678",
    "email": "aaa@gmail.com",
    "admin": "false",
    "adminToken": ""
   }

#### 로그인
- **URL:** `localhost:8080/api/user/login`
- **메소드:** `POST`
- **요청 바디:**
  ```json
   {
    "username": "John",
    "password": 12345678
   }

### Post

#### 게시글 작성
- **URL:** `localhost:8080/api/post`
- **메소드:** `POST`
- **헤더:** `Authorization: Bearer <JWT_TOKEN>`
- **요청 바디:**
  ```json
   {
    "title": "title4",
    "content": "content4"
   }

#### 게시글 전체 조회
- **URL:** `localhost:8080/api/posts`
- **메소드:** `GET`
- **요청 바디:**

#### 게시글 개별 조회
- **URL:** `localhost:8080/api/post/${postId}`
- **메소드:** `GET`
- **요청 바디:**

#### 게시글 수정
- **URL:** `localhost:8080/api/post/${postId}`
- **메소드:** `PUT`
- **헤더:** `Authorization: Bearer <JWT_TOKEN>`
- **요청 바디:**
  ```json
   {
    "title": "titleUpdate2",
    "content": "contentUpdate2"
   }

#### 게시글 삭제
- **URL:** `localhost:8080/api/post/${postId}`
- **메소드:** `DELETE`
- **헤더:** `Authorization: Bearer <JWT_TOKEN>`

### Comment

#### 댓글 작성
- **URL:** `localhost:8080/api/${postId}/comment`
- **메소드:** `POST`
- **헤더:** `Authorization: Bearer <JWT_TOKEN>`
- **요청 바디:**
  ```json
   {
    "comment": "comment6"
   }

#### 댓글 수정
- **URL:** `localhost:8080/api/comment/${commentId}`
- **메소드:** `PUT`
- **헤더:** `Authorization: Bearer <JWT_TOKEN>`
- **요청 바디:**
  ```json
   {
    "comment": "commentUpdate2"
   }

#### 댓글 삭제
- **URL:** `localhost:8080/api/comment/${commentId}`
- **메소드:** `DELETE`
- **헤더:** `Authorization: Bearer <JWT_TOKEN>`

### Like

#### 게시글 좋아요
- **URL:** `localhost:8080/api/post/${postId}/like`
- **메소드:** `POST`
- **헤더:** `Authorization: Bearer <JWT_TOKEN>`

#### 댓글 좋아요
- **URL:** `localhost:8080/api/comment/${commentId}/like`
- **메소드:** `POST`
- **헤더:** `Authorization: Bearer <JWT_TOKEN>`

</br>

## application.properties

```
spring.application.name=blog

# DB
spring.datasource.url=jdbc:mysql:/${ip_address}:${port}/${db_name}
spring.datasource.username=${db_username}
spring.datasource.password=${db_password}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA
spring.jpa.hibernate.ddl-auto=update

# JWT
jwt.secret.key=${jwt_secret_key}

# ADMIN
admin.token=${admin_token}
```
