CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE OR REPLACE FUNCTION now_utc() RETURNS TIMESTAMP AS
$$
SELECT now() AT TIME ZONE 'utc';
$$ LANGUAGE sql;


-- enforce that created at can't be changed
CREATE OR REPLACE FUNCTION trg_func__check_created_at_unchanged()
    RETURNS TRIGGER AS
$$
BEGIN
    IF OLD.created_at != NEW.created_at THEN
        RAISE EXCEPTION 'attempted to update created_at' USING HINT = 'created_at field can not be changed';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- set updated_at to now_utc if not set by an update
CREATE OR REPLACE FUNCTION trg_func__update_updated_at()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.updated_at IS NULL OR NEW.updated_at <= OLD.updated_at THEN
        NEW.updated_at = now_utc();
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- increment lock_version if not done
CREATE OR REPLACE FUNCTION trg_func__increment_lock_version()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.lock_version <= OLD.lock_version THEN
        NEW.lock_version = OLD.lock_version + 1;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
