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

INSERT INTO jobs(tenant_fk, active, title, description, resolution, created_by, allocated_to, created_at, expires_at) VALUES
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', true, 'Test Job 1', 'Test job ...', 'NOT_ALLOCATED', 1, 1, '2018-01-01', '2019-01-01'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', true, 'Test Job 2', 'Test expired job ...', 'NOT_ALLOCATED', 1, 1, '2018-01-01', '2018-02-01'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', true, 'Test Job 3', 'Test allocated job ...', 'IN_PROGRESS', 1, 36, '2018-01-01', '2018-10-01');


GRANT ALL PRIVILEGES ON TABLE jobs TO admin;
