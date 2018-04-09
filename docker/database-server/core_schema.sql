-----------------------
--   Core DataBase   --
-----------------------

-- clean up
DROP TABLE IF EXISTS jobs;
DROP SEQUENCE IF EXISTS jobs_seq;
DROP TYPE IF EXISTS job_resolution;

-- jobs table
CREATE SEQUENCE jobs_seq;
GRANT ALL PRIVILEGES ON SEQUENCE jobs_seq TO admin;

CREATE TYPE job_resolution AS ENUM ('NOT_ALLOCATED', 'IN_PROGRESS', 'DONE');

CREATE TABLE jobs (
  id BIGINT PRIMARY KEY DEFAULT nextval('jobs_seq'),
  tenant_fk UUID NOT NULL,
  active boolean DEFAULT false NOT NULL,
  title VARCHAR(32) NOT NULL,
  description TEXT,
  resolution job_resolution,
  created_by  BIGINT NOT NULL,
  allocated_to  BIGINT NOT NULL,
  created_at DATE NOT NULL,
  expires_at DATE NOT NULL
);

-- INSERT INTO jobs(id, tenant_fk, active, title, description, resolution, created_by, allocated_to, created_at, expires_at) VALUES
--   (1);

GRANT ALL PRIVILEGES ON TABLE jobs TO admin;
