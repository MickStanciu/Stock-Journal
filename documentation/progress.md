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
- [ ] one db per customer [will not be done]
- [ ] same db, one schema per customer [will not be done]
- [x] same db, same schema, customer has a discriminator [done]

**Todo**
- [x] tenant service [done]




### Customer Service

**Todo**
- [x] create db [done]
- [x] create client table with basic info [done]
- [ ] create notes table liked many to one with client [selected for development]
- [ ] create guardian ?

#### Read all clients by Tenant / ####

GET http://localhost:8080/client/api/d79ec11a-2011-4423-ba01-3af8de0a3e10?active=true [selected for development]

**Todo**
- [ ] create rest service