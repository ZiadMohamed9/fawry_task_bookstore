CREATE TABLE author
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255)          NOT NULL,
    birth_date date                  NOT NULL,
    CONSTRAINT pk_author PRIMARY KEY (id)
);

CREATE TABLE book
(
    isbn             VARCHAR(255) NOT NULL,
    title            VARCHAR(255) NOT NULL,
    book_type        SMALLINT     NOT NULL,
    publication_date date         NOT NULL,
    price            DOUBLE       NULL,
    stock            INT          NULL,
    CONSTRAINT pk_book PRIMARY KEY (isbn)
);

CREATE TABLE book_author
(
    author_id BIGINT       NOT NULL,
    book_isbn VARCHAR(255) NOT NULL,
    CONSTRAINT pk_book_author PRIMARY KEY (author_id, book_isbn)
);

ALTER TABLE book_author
    ADD CONSTRAINT fk_booaut_on_author FOREIGN KEY (author_id) REFERENCES author (id);

ALTER TABLE book_author
    ADD CONSTRAINT fk_booaut_on_book FOREIGN KEY (book_isbn) REFERENCES book (isbn);

CREATE TABLE user
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    username VARCHAR(255)          NOT NULL,
    email    VARCHAR(255)          NOT NULL,
    password VARCHAR(255)          NULL,
    `role`   VARCHAR(255)          NULL,
    address  VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE INDEX idx_book_price ON book (price);