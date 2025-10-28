create extension if not exists "uuid-ossp";

create table if not exists drivers (
                                       id uuid primary key default uuid_generate_v4(),
    name text not null,
    latitude double precision not null,
    longitude double precision not null,
    status text not null check (status in ('AVAILABLE','BUSY','OFFLINE')),
    rating numeric(2,1) not null default 5.0
    );

-- Index to speed up nearest-available lookups
create index if not exists idx_drivers_status on drivers (status);
