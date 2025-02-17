-- Script 1: Drop and recreate the schema
-- (effectively delete all tables)
DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public
    GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;
