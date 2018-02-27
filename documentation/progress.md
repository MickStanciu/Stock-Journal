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
- fix lost DB connection issues [not done]

