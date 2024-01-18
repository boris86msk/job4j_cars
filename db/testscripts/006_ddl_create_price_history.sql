CREATE TABLE PRICE_HISTORY(
     id SERIAL PRIMARY KEY,
     before BIGINT not null,
     after BIGINT not null,
     created TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
     post_id int REFERENCES auto_post(id)
);