# Développez le back-end en utilisant Java et Spring

Running:

1. Generate a couple of private/public RSA keys for JWT in the `/keys` folder (create it):
```sh
openssl genrsa -out private.pem 2048
openssl rsa -in private.pem -pubout -out public.pem
```

2. Run the Spring Boot application with the following environment variables:
```sh
DB_USERNAME=xxx DB_PASSWORD=yyy JWT_PRIVATE_KEY=`cat ./keys/private.pem` JWT_PUBLIC_KEY=`cat ./keys/public.pem` mvn spring-boot:run
```

Where xxx is the database username and yyy is the password

