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
- [ ] not sure we need account role name field [selected for development] 
  - [ ] catch db errors in the controller [selected for development]
- [ ] move Exception from controller to a default ExceptionMapper
- [ ] add warm up for DB on startup