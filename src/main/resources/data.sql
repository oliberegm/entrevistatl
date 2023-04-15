CREATE TABLE IF NOT EXISTS EXCHANGE_RATE (
    id bigint auto_increment primary key,
    origin_currency varchar(255),
    final_currency varchar(255),
    date_next varchar(255),
    value_rate varchar(255),
    PRIMARY KEY(id)
);