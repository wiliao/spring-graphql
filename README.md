# Getting Started
This API implementation works as the resource server under OAuth2 protocol and uses Spring Security's mock OAuth2 authorization server to validate the token.

One token has the scope "message:read" which simulates the lower rate limit use case ("unauthenticated user"); another token has the scope "message:write" which simulates the higher rate limit use case ("authenticated user").

The API is protected under Spring Security OAuth2 so the access without a valid token would be denied.

### How to build

Run the following commands in Windows command prompt.

1. Build the fat-jar file

   1). install JDK 17 if not yet, setup JAVA_HOME and path so gradle can use it <br />
   2). cd < Project root folder > <br />
   3). gradlew clean bootJar <br />
   

2. Build the Docker image
   
   1). launch the Windows Docker Desktop <br />
   2). cd < Project root folder > <br />
   3). docker build -t takehome . <br />


   The Docker image called "takehome" will be stored locally inside the Docker engine (Check Docker desktop)


### How to run
   Run the docker image "takehome" in Windows command prompt:

   docker run --rm -p8080:8080 takehome


### How to test
Run the following commands in Git Bash:

1. Access the API using token with read scope to simulate the lower rate limit use case

export TOKEN=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzdWJqZWN0IiwiZXhwIjoyMTY0MjQ1NjQ4LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiY2I1ZGMwNDYtMDkyMi00ZGJmLWE5MzAtOGI2M2FhZTYzZjk2IiwiY2xpZW50X2lkIjoicmVhZGVyIiwic2NvcGUiOlsibWVzc2FnZTpyZWFkIl19.Pre2ksnMiOGYWQtuIgHB0i3uTnNzD0SMFM34iyQJHK5RLlSjge08s9qHdx6uv5cZ4gZm_cB1D6f4-fLx76bCblK6mVcabbR74w_eCdSBXNXuqG-HNrOYYmmx5iJtdwx5fXPmF8TyVzsq_LvRm_LN4lWNYquT4y36Tox6ZD3feYxXvHQ3XyZn9mVKnlzv-GCwkBohCR3yPow5uVmr04qh_al52VIwKMrvJBr44igr4fTZmzwRAZmQw5rZeyep0b4nsCjadNcndHtMtYKNVuG5zbDLsB7GGvilcI9TDDnUXtwthB_3iq32DAd9x8wJmJ5K8gmX6GjZFtYzKk_zEboXoQ

curl -H 'Accept: application/json' -H "Authorization: Bearer ${TOKEN}" "http://localhost:8080/api/v1/continents?countryCode=CA&countryCode=US&countryCode=BE"


2. Access the API using token with write scope to simulate the higher rate limit use case

export TOKEN=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzdWJqZWN0IiwiZXhwIjoyMTY0MjQzOTA0LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiZGI4ZjgwMzQtM2VlNy00NjBjLTk3NTEtMDJiMDA1OWI5NzA4IiwiY2xpZW50X2lkIjoid3JpdGVyIiwic2NvcGUiOlsibWVzc2FnZTp3cml0ZSJdfQ.USvpx_ntKXtchLmc93auJq0qSav6vLm4B7ItPzhrDH2xmogBP35eKeklwXK5GCb7ck1aKJV5SpguBlTCz0bZC1zAWKB6gyFIqedALPAran5QR-8WpGfl0wFqds7d8Jw3xmpUUBduRLab9hkeAhgoVgxevc8d6ITM7kRnHo5wT3VzvBU8DquedVXm5fbBnRPgG4_jOWJKbqYpqaR2z2TnZRWh3CqL82Orh1Ww1dJYF_fae1dTVV4tvN5iSndYcGxMoBaiw3kRRi6EyNxnXnt1pFtZqc1f6D9x4AHiri8_vpBp2vwG5OfQD5-rrleP_XlIB3rNQT7tu3fiqu4vUzQaEg

curl -H 'Accept: application/json' -H "Authorization: Bearer ${TOKEN}" "http://localhost:8080/api/v1/continents?countryCode=CA&countryCode=US&countryCode=BE"


### The result will be like this:

{
"continent" : [ {
"countries" : [ "BE" ],
"name" : "Europe",
"otherCountries" : [ "AD", "AL", "AT", "AX", "BA", "BG", "BY", "CH", "CY", "CZ", "DE", "DK", "EE", "ES", "FI", "FO", "FR", "GB", "GG", "GI", "GR", "HR", "HU", "IE", "IM", "IS", "IT", "JE", "LI", "LT", "LU", "LV", "MC", "MD", "ME", "MK", "MT", "NL", "NO", "PL", "PT", "RO", "RS", "RU", "SE", "SI", "SJ", "SK", "SM", "UA", "VA", "XK" ]
}, {
"countries" : [ "CA", "US" ],
"name" : "North America",
"otherCountries" : [ "AG", "AI", "AW", "BB", "BL", "BM", "BQ", "BS", "BZ", "CR", "CU", "CW", "DM", "DO", "GD", "GL", "GP", "GT", "HN", "HT", "JM", "KN", "KY", "LC", "MF", "MQ", "MS", "MX", "NI", "PA", "PM", "PR", "SV", "SX", "TC", "TT", "VC", "VG", "VI" ]
} ]
}
