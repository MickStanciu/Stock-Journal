# Account Service
######Reads and account, creates an account and updates an account, for a given tenant

#### Can do:
- read account by name and password
- create an account
- update an account

#### Example reading an account
GET http://localhost:8080/account/api/d79ec11a-2011-4423-ba01-3af8de0a3e10?name=mircea.stanciu&password=secret
GET http://localhost:8080/account/api/d79ec11a-2011-4423-ba01-3af8de0a3e10/1

#### Example creating an account
POST http://localhost:8080/account/api/d79ec11a-2011-4423-ba01-3af8de0a3e10

Payload [name, password]

#### Example updating an account
PUT http://localhost:8080/account/api/d79ec11a-2011-4423-ba01-3af8de0a3e10/36

Payload [name, password, email]


**Todo**
- [x] create a table with tenants [done]
  - [x] uuid primary key [done]
- [x] create a table with roles [done]
- [x] create a table with users [done]
  - [x] linked to tenants [done]
  - [x] linked to roles -> customisable role names [done]
- [x] create a user service [done]
- [ ] not sure we need account role name field [selected for development]
- [x] add LTE (name and password constraints) for validation and DB constraints [done]
- [x] add active flag [done]
- [x] update the controller to create account [done]
- [x] create the service to create account [done]
- [x] update the sql [done]
  - [ ] make sure to search for invalid tenant [Gateway Api] [selected for development]
  - [x] make sure to search for duplicate names [done]
  - [ ] catch db errors in the controller [selected for development]
- [x] update controller to accept only name and password [done]
- [x] update the controller to update account [done]
- [x] create unit tests for validation [done]
- [x] update for [active] [done]
- [x] update for [role] [done]
- [x] check if the account name already exists
- [ ] move Exception from controller to a default ExceptionMapper
- [ ] add warm up for DB on startup
- [x] add health check for first record [done]
- [x] read account by ID [done]