# Tenant Service
######Reads tenant by UUID

####Can do:
- read tenant by UUID

#### Example reading a tenant
GET http://localhost:8080/tenant/api/d79ec11a-2011-4423-ba01-3af8de0a3e10

**Done**
- [x] migrate tenant db from account [done]
- [x] update account service not to use tenant [done]
- [x] simplify account response [done]
- [x] add health check for first record [done]

**Todo**
- [ ] add warm up for DB on startup
- [ ] if uuid is malformed .. it crashes [selected for development]
- [ ] update wildfly