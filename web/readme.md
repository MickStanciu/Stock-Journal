# Web Interface
######Interface between User and GatewayApi

**Done**
- upgrade wildfly [done]
- attempt login [done]
- create login cookie [done]
- deal with WebApplicationException errors from the client [done]
- fix Caused by: java.lang.IllegalStateException: Connection is still allocated [done]


**Todo**
- install Vue [partially done]
- create a basic login page [wip]
- front end validation for email [wip]
- back end validation for the form [wip]
- stop javax.ws.rs errors to be logged [not done]
- test when AuthToken is null -> redirect back with message [not done]
- jsf remove jsessionid [not done]
- adjust the filter to skip login if cookie is present and valid
- check cookie for expired (?)