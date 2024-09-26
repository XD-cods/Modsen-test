CREATE SCHEMA IF NOT EXISTS library;

CREATE TABLE IF NOT EXISTS library.book (
                                            book_id BIGSERIAL PRIMARY KEY,
                                            ISBN VARCHAR(255),
                                            title VARCHAR(255),
                                            genre SMALLINT ARRAY,  -- Используем JSONB для хранения списка жанров
                                            description TEXT,
                                            author VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS library.library_record (
                                                      id BIGINT PRIMARY KEY,
                                                      borrowed_date DATE DEFAULT CURRENT_DATE,
                                                      returned_date DATE,
                                                      book_id BIGINT NOT NULL,
                                                      CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES library.book (book_id) ON DELETE CASCADE
);
