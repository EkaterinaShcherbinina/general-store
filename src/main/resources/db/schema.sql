CREATE TABLE user (
    id bigint unsigned NOT NULL auto_increment,
    email varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    primary key(id)
);

CREATE TABLE roles (
    id int(11) NOT NULL auto_increment,
    name varchar(45) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE users_roles (
    userId int(11) NOT NULL,
    roleId int(11) NOT NULL,
    primary key (userId, roleId),
    CONSTRAINT role_fk FOREIGN KEY (roleId) REFERENCES roles (id),
    CONSTRAINT user_fk FOREIGN KEY (userId) REFERENCES user (id)
);

CREATE TABLE orders (
    id bigint unsigned NOT NULL auto_increment,
    orderDate DATE NOT NULL,
    status enum('in_progress', 'shipped', 'delivered') NOT NULL,
    userId bigint NOT NULL,
    primary key(id),
    constraint userForeignKey foreign key (userId) references user (id)
);

CREATE TABLE product (
    id bigint unsigned NOT NULL auto_increment,
    title varchar(255) NOT NULL,
    quantity int NOT NULL,
    price double NOT NULL,
    orderId bigint default NULL,
    primary key(id)
);

CREATE TABLE order_product (
    productId bigint unsigned NOT NULL,
    orderId bigint NOT NULL,
    quantity int NOT NULL,
    price double NOT NULL,
    primary key (productId, orderId),
    constraint orderForeignKey foreign key (orderId) references orders (id),
    constraint productForeignKey foreign key (productId) references product (id)
);
