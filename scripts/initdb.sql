CREATE SCHEMA IF NOT EXISTS library;

CREATE TABLE IF NOT EXISTS library.book (
    book_id BIGSERIAL PRIMARY KEY,
    ISBN VARCHAR(255) UNIQUE,
    title VARCHAR(255),
    description TEXT,
    author VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS library.library_record (
    id BIGSERIAL PRIMARY KEY,
    borrowed_date DATE DEFAULT CURRENT_DATE,
    returned_date DATE,
    book_id BIGINT NOT NULL,
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES library.book (book_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS library.user (
    user_id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'ROLE_USER'
);

CREATE TABLE IF NOT EXISTS library.genre (
    genre_id BIGINT NOT NULL PRIMARY KEY,
    genre_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS library.book_genres (
    book_id BIGINT NOT NULL,
    genre_id BIGINT NOT NULL,
    CONSTRAINT fk_book_genre FOREIGN KEY (book_id) REFERENCES library.book (book_id) ON DELETE CASCADE,
    CONSTRAINT fk_genre FOREIGN KEY (genre_id) REFERENCES library.genre (genre_id) ON DELETE CASCADE
);

