# Core API

java -jar ./target/core-hollow-swarm.jar ./target/core.war -s src/main/resources/project-defaults.yml -S local
mvn wildfly-swarm:run

######?

#### Can do:
- Things To Do


#### Example get jobs
GET http://localhost:8080/core/api/job/tenantId/accountId

**Done**
-[x] build a TTD DB (title, description, created, expires, resolution) [done]

**Todo**
-[ ] build hierarchical system for accounts based on roles [?]
-[ ] build a TTD service - get by account and resolution [selected for development]
-[ ] migrate TTD to it's own service [?]