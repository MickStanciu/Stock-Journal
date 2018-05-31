-----------------------
--   Core DataBase   --
-----------------------

-- clean up
DROP TABLE IF EXISTS projects;
DROP SEQUENCE IF EXISTS projects_seq;
DROP TABLE IF EXISTS tasks;
DROP SEQUENCE IF EXISTS tasks_seq;
DROP TABLE IF EXISTS timesheet;
DROP SEQUENCE IF EXISTS timesheet_seq;

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

INSERT INTO projects (tenant_fk, active, title, description) VALUES
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', true, 'Project 1', 'Project 1 description'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', true, 'Project 2', 'Project 2 description');

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


INSERT INTO tasks (tenant_fk, project_fk, active, title, description) VALUES
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 1, true, 'task1', 'task 1 desc'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 1, true, 'task2', 'task 2 desc'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 1, true, 'task3', 'task 3 desc'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 2, true, 'task1', 'task 1 desc'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 2, true, 'task2', 'task 2 desc'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 2, true, 'task3', 'task 3 desc');

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

INSERT INTO timesheet (tenant_fk, from_time, to_time, account_fk, project_fk, task_fk, title, status) VALUES
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', '2018-05-01 08:01', '2018-05-01 08:30', 6, 1, 1, 'worked on bla', 'FILLED'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', '2018-05-01 08:31', '2018-05-01 09:30', 6, 1, 2, 'worked on bla 2', 'FILLED'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', '2018-05-02 10:31', '2018-05-01 11:30', 6, 2, 2, 'worked on bla 3', 'FILLED'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', '2018-04-01 08:01', '2018-04-01 08:30', 6, 1, 1, 'worked on bla 4', 'FILLED'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', '2018-04-01 08:31', '2018-04-01 09:30', 6, 1, 2, 'worked on bla 5', 'FILLED'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', '2018-04-02 10:31', '2018-04-01 11:30', 6, 2, 2, 'worked on bla 6', 'FILLED'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', '2018-05-07 08:31', '2018-05-07 09:30', 6, 1, 2, 'worked on bla 7', 'FILLED'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', '2018-05-07 10:31', '2018-05-07 11:30', 6, 2, 2, 'worked on bla 8', 'FILLED');

