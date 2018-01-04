# Missing Things
- [ ] create user and database when initializing docker
- [x] move current db to another schema [done]

### Basic Currency Support

**Problem:** Database has no support for currency

**Solution:**
- [ ] add currency table
- [ ] link currency table to the item table


### File repository Support

**Problem:** Application has no support for file storage

**Solution:**
- [x] https://lizardfs.com/download/#toggle-id-1 [selected for development]
- [ ] https://hub.docker.com/r/itherz/lizardfs-master/~/dockerfile/

build docker file according to specs

test files

connect catalog to the repository


### Multi-tenant Support
**Problem:** Application has no support for multi-tenant

**Solution**
- [ ] one db per customer
- [ ] same db, one schema per customer
- [x] same db, same schema, customer has a discriminator [done]




### Account Service
#### Read account by Tenant / Name / Password [done] ####

GET http://localhost:8080/account/api/d79ec11a-2011-4423-ba01-3af8de0a3e10?name=mircea.stanciu&password=secret

**Todo**
- [x] create a table with tenants [done]
  - [x] uuid primary key [done]

- [x] create a table with roles [done]
- [x] create a table with users [done]
  - [x] linked to tenants [done]
  - [x] linked to roles -> customisable role names [done]
- [x] create a user service [done]
- [ ] not sure we need account role name field [selected for development]

#### Create new account by Tenant / Name / Password [selected for development] ####

POST http://localhost:8080/account/api/d79ec11a-2011-4423-ba01-3af8de0a3e10

Payload [name, password]

**Todo**
- [ ] update the controller [selected for development]
- [ ] create the service [selected for development]
- [ ] update the sql [selected for development]
  - [ ] make sure to search for invalid tenant
  - [ ] make sure to search for duplicate names
  - [ ] catch db errors in the controller
