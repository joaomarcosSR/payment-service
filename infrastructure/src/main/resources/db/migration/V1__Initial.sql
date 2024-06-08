create table consumers (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    token VARCHAR(255) NOT NULL,
    update_payment_status_endpoint VARCHAR(255) NULL,
    update_payment_status_token VARCHAR(255) NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
);

create table payments (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    consumer_id VARCHAR(36) NOT NULL,
    payment_key VARCHAR(255) NOT NULL,
    status VARCHAR(30) NOT NULL,
    error_message VARCHAR(4000) NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
);

create table payments_consumers (
    payment_id VARCHAR(36) NOT NULL,
    consumer_id VARCHAR(36) NOT NULL,
    constraint idx_payment_consumer unique (payment_id, consumer_id),
    constraint fk_payment_id foreign key (payment_id) references payments (id) on delete cascade,
    constraint fk_consumer_id foreign key (consumer_id) references consumers (id) on delete cascade
);