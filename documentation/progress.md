# Missing Things
todos

### Basic Currency Support

**Problem:** Database has no support for currency

**Solution:**
- [ ] add currency table
- [ ] link currency table to the item table


### File repository Support

**Problem:** Application has no support for file storage

**Solution:**
- [x] https://lizardfs.com/download/#toggle-id-1
- [ ] https://hub.docker.com/r/itherz/lizardfs-master/~/dockerfile/

build docker file according to specs

test files

connect catalog to the repository


### Multi-tenant Support
**Problem:** Application has no support for multi-tenant

**Solution**
- [ ] one db per customer
- [ ] same db, one schema per customer
- [ ] same db, same schema, customer has a discriminator

**Todo**
- [ ] create a table with tenants -uuid primary key-
- [ ] create a table with users -linked to tenants-
- [ ] create a user service