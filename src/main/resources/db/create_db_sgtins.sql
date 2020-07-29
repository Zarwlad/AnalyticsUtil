CREATE TABLE IF NOT EXISTS sgtin (
    id uuid PRIMARY KEY,
    sgtin varchar(27) not null,
    sscc_id uuid,
    status varchar(50) not null,
    batch_or_lot varchar (50) not null,
    last_status_op_date timestamp not null,
    gtin varchar(14) not null,
    status_date timestamp not null,
    emission_operation_date timestamp not null,
    foreign key (sscc_id) references sscc(id),
    unique (sgtin)
);
CREATE table IF NOT EXISTS sscc (
    id uuid PRIMARY KEY,
    sscc varchar (18) not null,
    parent_sscc_id uuid,
    foreign key (parent_sscc_id) references sscc(id),
    unique (sscc)
);
