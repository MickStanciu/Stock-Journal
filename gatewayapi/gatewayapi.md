# Gateway API
######Interface between all the services

#### Can do:
- authenticate for a given tenant with provided name and password


#### Example authentication
GET http://localhost:8080/gatewayapi/api/auth/d79ec11a-2011-4423-ba01-3af8de0a3e10?name=mircea.stanciu&password=secret 

**Todo**
- create auth controller
  - connect to tenant api [done]
  - connect to account api [done]
- create basic JWT token [done]
- remove gatewayapi from the url [done]
- add URL filter in order to force token on certain urls except a whitelist [wip]
  
