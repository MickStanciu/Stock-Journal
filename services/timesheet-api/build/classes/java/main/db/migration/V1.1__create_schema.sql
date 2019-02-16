-- clean up
DROP TABLE IF EXISTS timesheet;
DROP SEQUENCE IF EXISTS timesheet_seq;
DROP TABLE IF EXISTS tasks;
DROP SEQUENCE IF EXISTS tasks_seq;
DROP TABLE IF EXISTS projects;
DROP SEQUENCE IF EXISTS projects_seq;


-- projects table
CREATE SEQUENCE projects_seq;
GRANT ALL PRIVILEGES ON SEQUENCE projects_seq TO admin;

CREATE TABLE projects (
  id BIGINT PRIMARY KEY DEFAULT nextval('projects_seq'),
  tenant_fk UUID NOT NULL,
  active boolean DEFAULT false NOT NULL,
  title VARCHAR(32) NOT NULL,
  description TEXT
);

GRANT ALL PRIVILEGES ON TABLE projects TO admin;


-- tasks table
CREATE SEQUENCE tasks_seq;
GRANT ALL PRIVILEGES ON SEQUENCE tasks_seq TO admin;

CREATE TABLE tasks (
  id BIGINT PRIMARY KEY DEFAULT nextval('tasks_seq'),
  tenant_fk UUID NOT NULL,
  project_fk BIGINT NOT NULL REFERENCES projects(id),
  active boolean DEFAULT false NOT NULL,
  title VARCHAR(32) NOT NULL,
  description TEXT
);

GRANT ALL PRIVILEGES ON TABLE tasks TO admin;


-- timesheet table yyyy-mm-dd
CREATE SEQUENCE timesheet_seq;
GRANT ALL PRIVILEGES ON SEQUENCE timesheet_seq TO admin;

CREATE TABLE timesheet (
  id BIGINT PRIMARY KEY DEFAULT nextval('timesheet_seq'),
  tenant_fk UUID NOT NULL,
  from_time TIMESTAMP NOT NULL,
  to_time TIMESTAMP NOT NULL,
  account_fk BIGINT NOT NULL,
  project_fk BIGINT NOT NULL REFERENCES projects(id),
  task_fk BIGINT NOT NULL REFERENCES tasks(id),
  title VARCHAR(32) NOT NULL,
  status VARCHAR(32) NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE timesheet TO admin;
