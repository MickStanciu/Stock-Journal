# Web Interface
######Interface between User and GatewayApi

**Done**
-[x] upgrade wildfly [done]
-[x] attempt login [done]
-[x] create login cookie [done]
-[x] deal with WebApplicationException errors from the client [done]
-[x] fix Caused by: java.lang.IllegalStateException: Connection is still allocated [done]
-[x] create a basic login page [done]
-[x] adjust the filter to skip login if cookie is present and valid [done]
-[x] align token expiry date with GW and LOGIN cookie expiry dates [done]
-[x] create cookie that contains tenant information [done]
-[x] obtain account information and cache it for session [done]
-[x] get account info should not need tenant id anymore, use token for that! [done]

**Todo**
- install Vue [partially done]
- upgrade login page to hide button if nothing is typed in one of the fields [wip]
- front end validation for email [wip]
- back end validation for the form [wip]
- stop javax.ws.rs errors to be logged [not done]
- test when AuthToken is null -> redirect back with message [not done]
- jsf remove jsessionid [not done]
- check cookie for expired (? not sure if I need to, the browser should discard them) [not done - to investigate]
