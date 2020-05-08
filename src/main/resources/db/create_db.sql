CREATE TABLE IF NOT EXISTS business_partner (
    system_subj_id uuid PRIMARY KEY,
    inn varchar,
    kpp varchar,
    org_name varchar,
    ogrn varchar,
    dir_first_name varchar,
    dir_middle_name varchar,
    dir_last_name varchar,
    request_reg_date timestamptz,
    fact_req_date timestamptz,
    country_code varchar,
    federal_subj_code varchar,
    state_gov_supplier bool not null,
    entity_type varchar
);

CREATE TABLE IF NOT EXISTS fias (
    house_guid uuid PRIMARY KEY,
    ao_guid uuid
);

CREATE TABLE IF NOT EXISTS location (
    branch_id varchar(25) PRIMARY KEY,
    fias_id uuid not null,
    address varchar,
    location_status varchar,
    is_address_found_in_fias bool not null,
    business_partner_id uuid not null,
    is_safe_warehouse bool not null,

    foreign key (fias_id) references fias(house_guid),
    foreign key (business_partner_id) references business_partner(system_subj_id)
);

