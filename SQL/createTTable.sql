CREATE TABLE public.cart
(
    id serial,
    user_id integer NOT NULL,
    created_at date DEFAULT now(),
    CONSTRAINT cart_pk PRIMARY KEY (id),
    CONSTRAINT cart_user_fk FOREIGN KEY (user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
);

ALTER TABLE public.users
    ADD CONSTRAINT "APP_USER_CART_ID_FK" FOREIGN KEY (cart_id)
        REFERENCES public.cart (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL
    NOT VALID;

CREATE TABLE public.rates
(
    id serial,
    "userId" integer,
    "bookId" integer,
    stars integer DEFAULT 0,
    review character varying(1024),
    "createdAt" date DEFAULT now(),
    CONSTRAINT rates_pk PRIMARY KEY (id),
    CONSTRAINT rates_user_fk FOREIGN KEY ("userId")
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL
        NOT VALID,
    CONSTRAINT rates_book_fk FOREIGN KEY ("bookId")
        REFERENCES public.book (book_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL
        NOT VALID
);

CREATE TABLE public.cart
(
    cart_id serial,
    user_id integer NOT NULL,
    created_at date DEFAULT now(),
    PRIMARY KEY (cart_id),
    ADD CONSTRAINT user_cart_fk FOREIGN KEY (user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL
        NOT VALID
);

ALTER TABLE public.users
    ADD CONSTRAINT user_cart_fk FOREIGN KEY (cart_id)
        REFERENCES public.cart (cart_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL
    NOT VALID;

CREATE TABLE public.picked
(
    picked_id serial,
    cart_id integer NOT NULL,
    book_id integer NOT NULL,
    amount integer DEFAULT 1,
    created_at date DEFAULT now(),
    CONSTRAINT "PK_picked_id" PRIMARY KEY (picked_id),
    CONSTRAINT "FK_PICKED_CART" FOREIGN KEY (cart_id)
        REFERENCES public.cart (cart_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT "FK_PICKED_BOOK" FOREIGN KEY (book_id)
        REFERENCES public.book (book_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
);

CREATE TABLE public.wish_list
(
    wishlist_id serial,
    user_id integer NOT NULL,
    book_id integer NOT NULL,
    CONSTRAINT "PK_WISHLIST" PRIMARY KEY (wishlist_id),
    CONSTRAINT "FK_WISHLIST_USER" FOREIGN KEY (user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT "FK_WISHLIST_BOOK" FOREIGN KEY (book_id)
        REFERENCES public.book (book_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
);

ALTER TABLE public.cart
    ADD COLUMN total_price integer DEFAULT 0;

ALTER TABLE public.cart
    ADD COLUMN total_item integer DEFAULT 0;

ALTER TABLE public.cart
    ADD COLUMN pick integer DEFAULT 0;

ALTER TABLE public.cart
    ADD COLUMN address character varying(1024);

ALTER TABLE public.cart
    ADD COLUMN phone1 character varying(20);

ALTER TABLE public.cart
    ADD COLUMN phone2 character varying(20);

ALTER TABLE public.cart
    ADD COLUMN payment_method character varying(50);

ALTER TABLE public.cart
    ADD COLUMN name character varying(100);