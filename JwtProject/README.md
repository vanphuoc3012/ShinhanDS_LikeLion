# Spring Security + JWT

Project content 2 separated application:
    1. Authentication Server
    2. Business Authentication Logic App (JWT implement here)

## Code structure
### Business Authentication Logic App
```
├── <b>src</b>
│   ├── <b>main</b>
│   │   ├── <b>java</b>
│   │   │   ├── <b>com.example.businesslogicserver</b>
│   │   │   │   ├── authentication
│   │   │   │   │   ├── filter
│   │   │   │   │   ├── model
│   │   │   │   │   ├── provider
│   │   │   │   │   ├── proxy
│   │   │   │   ├── configuration
│   │   │   │   ├── controller
│   │   │   │   ├── service
```

### Authentication Server
```
├── <b>src</b>
│   ├── <b>main</b>
│   │   ├── <b>java</b>
│   │   │   ├── <b>com.example.implauthenticationserver</b>
│   │   │   │   ├── config
│   │   │   │   ├── controller
│   │   │   │   ├── entity
│   │   │   │   ├── repository
│   │   │   │   ├── service
│   │   │   │   ├── util
```

## Work flow:

1. Client send username + password to receive an OTP (Once time password).
2. Client send username + OTP to receive token.
3. Client use token to access the website content.




