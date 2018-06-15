INSERT INTO account_roles (tenant_fk, name) VALUES
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'No Role'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'System Administrator'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'Chief Executive Officer'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'Director Human Resources'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'Director Finance'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'Director Operations'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'Senior Project Manager'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'Senior Warehouse Manager'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'Senior Finance Officer'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'Project Manager'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'Warehouse Team Leader'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 'Technician');


INSERT INTO account_permissions (tenant_fk, role_fk, name) VALUES
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 2, 'CREATE_ACCOUNT'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 2, 'LOG_IN'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 3, 'LOG_IN'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 3, 'PAYROLL_APPROVE'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 4, 'LOG_IN'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 4, 'PAYROLL_APPROVE'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 4, 'PAYROLL_REQUEST_APPROVAL'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 5, 'LOG_IN'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 5, 'PAYROLL_APPROVE'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 5, 'PAYROLL_REQUEST_APPROVAL'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 6, 'LOG_IN'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 6, 'PAYROLL_APPROVE'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 6, 'PAYROLL_REQUEST_APPROVAL'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 7, 'LOG_IN'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 7, 'TIMESHEET_APPROVE'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 7, 'TIMESHEET_REJECT'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 8, 'LOG_IN'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 8, 'TIMESHEET_APPROVE'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 8, 'TIMESHEET_REJECT'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 9, 'LOG_IN'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 9, 'PAYROLL_REQUEST_APPROVAL'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 10, 'LOG_IN'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 10, 'TIMESHEET_APPROVE'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 10, 'TIMESHEET_REJECT'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 10, 'TIMESHEET_INSERT'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 10, 'TIMESHEET_UPDATE'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 11, 'LOG_IN'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 11, 'TIMESHEET_APPROVE'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 11, 'TIMESHEET_REJECT'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 11, 'TIMESHEET_INSERT'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 11, 'TIMESHEET_UPDATE'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 12, 'LOG_IN'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 12, 'TIMESHEET_INSERT'),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 12, 'TIMESHEET_UPDATE');


INSERT INTO accounts (tenant_fk, role_fk, name, email, password, active) VALUES
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 2, 'Lola Admin', 'lola.admin@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 3, 'Lola White', 'lola.white@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 4, 'Lola Yellow', 'lola.yellow@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 5, 'Lola Red', 'lola.red@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 6, 'Lola Purple', 'lola.purple@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 7, 'Lola Blue', 'lola.blue@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 8, 'Lola Pink', 'lola.pink@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 9, 'Lola Black', 'lola.black@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 10, 'Lola Orange', 'lola.orange@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 11, 'Lola Fuchsia', 'lola.fuchsia@jadebaboon.com', 'secret', true),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 12, 'Lola Green', 'lola.green@jadebaboon.com', 'secret', true);


-- TODO: not sure if 1-1 type is necessary
INSERT INTO account_relationships (tenant_fk, parent_fk, child_fk, depth) VALUES
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 1, 1, 0),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 2, 2, 0),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 3, 3, 0),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 4, 4, 0),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 5, 5, 0),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 6, 6, 0),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 7, 7, 0),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 8, 8, 0),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 9, 9, 0),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 10, 10, 0),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 11, 11, 0);


-- RELATIONSHIPS
INSERT INTO account_relationships (tenant_fk, parent_fk, child_fk, depth) VALUES
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 2, 3, 1),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 2, 4, 1),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 2, 5, 1),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 2, 8, 2),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 2, 6, 2),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 2, 7, 2),

  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 4, 8, 1),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 5, 6, 1),
  ('d79ec11a-2011-4423-ba01-3af8de0a3e10', 5, 7, 1);