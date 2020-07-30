CREATE TABLE IF NOT EXISTS event(
    id uuid not null primary key,
    type varchar(50) not null,
    operation_date timestamp not null,
    status varchar(50) not null,
    reg_status varchar(50) not null,
    created_date timestamp not null,
    client varchar(50) not null
);

CREATE TABLE IF NOT EXISTS event_status(
    id uuid not null primary key,
    change_operation_date timestamp not null,
    event_id uuid not null,
    foreign key (event_id) references event(id)
);

CREATE TABLE IF NOT EXISTS message(
    id uuid not null primary key,
    status varchar(50) not null,
    document_type_id varchar(50) not null,
    created_date timestamp not null,
    operation_date timestamp not null,
    event_id uuid not null,
    for_mdlp bool not null,
    foreign key (event_id) references event(id)
);

CREATE TABLE IF NOT EXISTS message_history(
    id uuid not null primary key,
    authored_by varchar(50) not null,
    created_date timestamp not null,
    status varchar(50) not null,
    message_id uuid not null,
    foreign key (message_id) references message(id)
);

CREATE TABLE IF NOT EXISTS event_stat(
    id uuid not null primary key,
    event_id uuid not null,
    event_posting_secs numeric,
    message_send_secs_avg numeric,
    is_error_event boolean not null,
    is_error_message boolean not null,
    is_event_posted boolean not null,
    is_message_created boolean not null,
    event_month varchar(50) not null,
    total_sending_secs numeric,
    foreign key (event_id) references event(id)
);