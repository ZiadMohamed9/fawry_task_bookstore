CREATE TABLE book
(
    isbn             VARCHAR(255) NOT NULL,
    title            VARCHAR(255) NULL,
    book_type        SMALLINT     NULL,
    publication_date date         NULL,
    price            DOUBLE       NOT NULL,
    stock            INT          NOT NULL,
    CONSTRAINT pk_book PRIMARY KEY (isbn)
);

CREATE TABLE book_author
(
    author_id BIGINT       NOT NULL,
    book_isbn VARCHAR(255) NOT NULL,
    CONSTRAINT pk_book_author PRIMARY KEY (author_id, book_isbn)
);

CREATE INDEX idx_book_price ON book (price);

ALTER TABLE book_author
    ADD CONSTRAINT fk_booaut_on_author FOREIGN KEY (author_id) REFERENCES author (id);

ALTER TABLE book_author
    ADD CONSTRAINT fk_booaut_on_book FOREIGN KEY (book_isbn) REFERENCES book (isbn);