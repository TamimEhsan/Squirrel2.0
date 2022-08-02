CREATE TABLE wish_list(
    user_id integer,
    book_id integer,
    CONSTRAINT "WISH_LIST_PK" PRIMARY KEY (user_id, book_id),
    CONSTRAINT "WISH_LIST_USER_FK" FOREIGN KEY (user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT "WISH_LIST_BOOK_FK" FOREIGN KEY (book_id)
        REFERENCES public.book (book_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
);