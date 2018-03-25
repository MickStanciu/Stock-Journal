# Web Interface
######Interface between User and GatewayApi

**Done**
- upgrade wildfly [done]
- attempt login [done]
- create login cookie [done]
- deal with WebApplicationException errors from the client [done]
- fix Caused by: java.lang.IllegalStateException: Connection is still allocated [done]
- create a basic login page [done]
- adjust the filter to skip login if cookie is present and valid [done]

**Todo**
- install Vue [partially done]
- upgrade login page to hide button if nothing is typed in one of the fields [wip]
- front end validation for email [wip]
- back end validation for the form [wip]
- stop javax.ws.rs errors to be logged [not done]
- test when AuthToken is null -> redirect back with message [not done]
- jsf remove jsessionid [not done]
- check cookie for expired (? not sure if I need to, the browser should discard them)
- create cookie that contains tenant information / logged in user information [wip]
- align token expiry date with GW and LOGIN cookie expiry dates 