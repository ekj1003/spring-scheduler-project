# 나만의 일정관리 앱 서버

## 🎯Goal
구현하고자 하는 서비스의 전체적인 흐름을 파악하고 필요한 기능 설계하기
API 명세서, ERD, SQL 작성하기
Spring Boot 기반 CRUD (Create, Read, Update, Delete) 기능이 포함된 REST API 만들기

## 🗒️ API 명세서

## 유저(User) 관련 API
<img width="816" alt="스크린샷 2024-08-28 16 58 14" src="https://github.com/user-attachments/assets/5f94cdc2-a5e0-468e-8e33-630848e378a2">

## 일정(Schedule)관련 API
<img width="835" alt="스크린샷 2024-08-28 16 58 30" src="https://github.com/user-attachments/assets/9e2876f8-bdf7-4a7a-9d87-9ab886e45865">

## 댓글(Comment)관련 API
<img width="844" alt="스크린샷 2024-08-28 17 00 20" src="https://github.com/user-attachments/assets/f2b7316b-44f1-4539-b46e-4ebfc24e65e0">


## 🧩 ERD 
<img width="698" alt="스크린샷 2024-08-28 13 44 42" src="https://github.com/user-attachments/assets/d12e5c4c-e795-4c7c-a371-486ed1c3c653">

## 💻 SQL

```sql
/* schedule 생성 */
create table schedule
(
    id       bigint       not null auto_increment,
    contents varchar(500) not null,
    manager varchar(255) not null,
    password varchar(200) not null,
    createTime timestamp not null,
    modifiedTime timestamp not null,
    primary key (id)
);


/* comment 생성 */
CREATE TABLE comment (
    id BIGINT NOT NULL AUTO_INCREMENT,
    create_date DATETIME(6),
    modified_date DATETIME(6),
    comments VARCHAR(300) NOT NULL,
    writer VARCHAR(20) NOT NULL,
    schedule_id BIGINT,
    PRIMARY KEY (id)
);


/* User 생성 */
CREATE TABLE user (
    id BIGINT NOT NULL AUTO_INCREMENT,
    create_date DATETIME(6),
    modified_date DATETIME(6),
    email VARCHAR(50) NOT NULL,
    name VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);


/* ScheduleUser 생성 */
CREATE TABLE schedule_user (
    id BIGINT NOT NULL AUTO_INCREMENT,
    manager_user_id BIGINT,
    schedule_id BIGINT,
    PRIMARY KEY (id)
);


-- 외래 키 제약 조건 추가
ALTER TABLE comment
    ADD CONSTRAINT FKsy51iks4dgapu66gfj3mnykch
        FOREIGN KEY (schedule_id)
            REFERENCES schedule (id);

ALTER TABLE schedule
    ADD CONSTRAINT FKa50n59y1j4a6qwa42p8jiguds
        FOREIGN KEY (user_id)
            REFERENCES user (id);

ALTER TABLE schedule_user
    ADD CONSTRAINT FKhq86yha6sf91ay14806t8wry
        FOREIGN KEY (manager_user_id)
            REFERENCES user (id);

ALTER TABLE schedule_user
    ADD CONSTRAINT FK631kxv3p08ynhxtafj0lul432
        FOREIGN KEY (schedule_id)
            REFERENCES schedule (id);
```
## 🎯 요구 기능

### 1단계

기능: 일정 CRU
학습목표: JPA 입문

### 조건

1. 일정을 저장, 단건 조회, 수정할 수 있습니다.
2. 일정은 `작성 유저명`, `할일 제목`, `할일 내용`, `작성일`, `수정일` 필드를 갖고 있습니다.

### 2단계

기능: 댓글 CRUD
학습목표: 연관관계 기본

### 조건

1. 일정에 댓글을 달 수 있습니다.
    1. 댓글과 일정은 연관관계를 가집니다.
2. 댓글을 저장, 단건 조회, 전체 조회, 수정, 삭제할 수 있습니다.
3. 댓글은 `댓글 내용`, `작성일`, `수정일`, `작성 유저명` 필드를 갖고 있습니다.

### 3단계

기능: 일정 페이징 조회
학습목표: 페이징/정렬

### 조건

1. 일정을 Spring Data JPA의 `Pageable`과 `Page` 인터페이스를 활용하여 페이지네이션을 구현해주세요.
    1. `페이지 번호`와 `페이지 크기`를 쿼리 파라미터로 전달하여 요청하는 항목을 나타냅니다.
    2. `할일 제목`, `할일 내용`, `댓글 개수`, `일정 작성일`, `일정 수정일`, `일정 작성 유저명` 필드를 조회합니다.
    3. 디폴트 `페이지 크기`는 10으로 적용합니다.
2. 일정의 `수정일`을 기준으로 내림차순 정렬합니다.

### 4단계

기능: 일정 삭제
학습목표: 영속성 전이

### 조건

1. 일정을 삭제할 때 일정의 댓글도 함께 삭제됩니다. 
    1. JPA의 영속성 전이 기능을 활용합니다.
    

### 5단계

기능: 유저 CRUD
학습목표: 연관관계 심화

### 조건

1. 유저를 저장, 단건 조회, 전체 조회, 삭제할 수 있습니다. 
    1. 유저는 `유저명`, `이메일`, `작성일`, `수정일` 필드를 갖고 있습니다.
2. 일정은 이제 `작성 유저명` 필드 대신 `유저 고유 식별자` 필드를 가집니다.
3. 일정을 작성한 유저는 추가로 일정 담당 유저들을 배치할 수 있습니다. 
    1. 유저와 일정은 N:M 관계입니다. (`@ManyToMany` 사용 금지!)
    

### 6단계

기능: 일정 조회 개선
학습목표: 지연 로딩

### 조건

1. 일정 단건 조회 시 담당 유저들의 `고유 식별자`, `유저명`, `이메일`이 추가로 포함됩니다.
2. 일정 전체 조회 시 담당 유저 정보가 포함되지 않습니다.
    1. JPA의 지연 로딩 기능을 활용합니다.
