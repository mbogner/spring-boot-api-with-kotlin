CREATE TABLE breweries
(
    id           uuid                        not null default uuid_generate_v4()
        constraint pk_breweries primary key,
    created_at   timestamp without time zone not null default now_utc(),
    updated_at   timestamp without time zone
        constraint cc_breweries__create_before_update check (created_at <= updated_at),
    lock_version bigint                      not null default 0
        constraint cc_breweries__lock_version_positive check (lock_version >= 0),

    name         varchar(255)                not null
        constraint uc_breweries__name unique
) WITHOUT OIDS;


CREATE TRIGGER trg_breweries__check_created_at_unchanged
    BEFORE UPDATE
    ON breweries
    FOR EACH ROW
EXECUTE PROCEDURE trg_func__check_created_at_unchanged();

CREATE TRIGGER trg_breweries__update_updated_at
    BEFORE UPDATE
    ON breweries
    FOR EACH ROW
EXECUTE PROCEDURE trg_func__update_updated_at();

CREATE TRIGGER trg_breweries__increment_lock_version
    BEFORE UPDATE
    ON breweries
    FOR EACH ROW
EXECUTE PROCEDURE trg_func__increment_lock_version();
