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
