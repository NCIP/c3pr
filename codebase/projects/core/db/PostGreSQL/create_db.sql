Notes to create a new c3pr_dev DATABASE in PostgreSQL (for an already existing
user 'pg')
---------------------------------------------------------------------
-- Database: c3pr_dev

-- DROP DATABASE c3pr_dev;

CREATE DATABASE c3pr_dev
  WITH OWNER = pg
       ENCODING = 'UTF8'
       TABLESPACE = pg_default;
GRANT ALL ON DATABASE c3pr_dev TO public;
GRANT ALL ON DATABASE c3pr_dev TO pg;
