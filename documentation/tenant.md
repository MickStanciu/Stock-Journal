# Tenant Service
######Reads tenant by UUID

####Can do:
- read tenant by UUID

#### Example reading a tenant
GET http://localhost:8080/tenant/api/d79ec11a-2011-4423-ba01-3af8de0a3e10

**Todo**
- [x] migrate tenant db from account [done]
- [x] update account service not to use tenant [done]
- [x] simplify account response [done]