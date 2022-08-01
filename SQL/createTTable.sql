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