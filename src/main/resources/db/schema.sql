DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users_roles CASCADE;
DROP TABLE IF EXISTS reset_password_tokens CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS product CASCADE;
DROP TABLE IF EXISTS order_product CASCADE;
DROP TYPE IF EXISTS order_status CASCADE;

CREATE TABLE IF NOT EXISTS users (
    id bigserial PRIMARY KEY,
    email character varying NOT NULL UNIQUE,
    password character varying NOT NULL
);

CREATE TABLE IF NOT EXISTS roles (
    id serial PRIMARY KEY,
    name character varying NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users_roles (
    userId integer NOT NULL,
    roleId integer NOT NULL,
    primary key (userId, roleId),
    CONSTRAINT role_fk FOREIGN KEY (roleId) REFERENCES roles (id),
    CONSTRAINT user_fk FOREIGN KEY (userId) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS reset_password_tokens (
    id bigserial PRIMARY KEY,
    token character varying NOT NULL,
    userId bigint NOT NULL,
    constraint user_token_fk foreign key (userId) references users (id)
);

create type order_status as enum('in_progress', 'shipped', 'delivered');

CREATE TABLE IF NOT EXISTS orders (
    id bigserial PRIMARY KEY,
    orderDate DATE NOT NULL,
    status order_status NOT NULL,
    userId bigint NOT NULL,
    constraint userForeignKey foreign key (userId) references users (id)
);

CREATE TABLE IF NOT EXISTS product (
    id bigserial PRIMARY KEY,
    title character varying NOT NULL,
    quantity integer NOT NULL,
    price numeric NOT NULL,
    orderId bigint default NULL
);

CREATE TABLE IF NOT EXISTS order_product (
    productId bigint NOT NULL,
    orderId bigint NOT NULL,
    quantity integer NOT NULL,
    price numeric NOT NULL,
    primary key (productId, orderId),
    constraint orderForeignKey foreign key (orderId) references orders (id),
    constraint productForeignKey foreign key (productId) references product (id)
);
