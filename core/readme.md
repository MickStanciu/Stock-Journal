# Core API
######?

#### Can do:
- Things To Do


#### Example get jobs
GET http://localhost:8080/core/api/jobs/tenantId/accountId
- headers will contain token. Tenant Id is extracted from the token


**Done**
-[x] build a TTD DB (title, description, created, expires, resolution) [done]

**Todo**
-[ ] build hierarchical system for accounts based on roles [?]
-[ ] build a TTD service - get by account and resolution [selected for development]
-[ ] migrate TTD to it's own service [?]