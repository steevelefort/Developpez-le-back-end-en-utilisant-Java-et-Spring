# ChâTop API

REST API for ChâTop rental platform.

## Database Setup

**Create MySQL database**
```sql
CREATE DATABASE chatop;
```

Tables are created automatically when the application starts.

## Installation and Setup

### Requirements

- Java 17+
- Maven 3.6+
- MySQL 8.0+

### Steps

1. **Clone repository**
   ```bash
   git clone <repository-url>
   cd chatop-api
   ```

2. **Generate JWT keys**
   ```bash
   mkdir keys
   openssl genrsa -out keys/private.pem 2048
   openssl rsa -in keys/private.pem -pubout -out keys/public.pem
   ```

3. **Create upload directory**
   ```bash
   mkdir /tmp/uploads
   ```

4. **Run application**
   ```bash
   DB_USERNAME=your_mysql_user \
   DB_PASSWORD=your_mysql_password \
   DB_HOST=jdbc:mysql://localhost:3306/chatop \
   UPLOAD_DIR="/tmp/uploads" \
   JWT_PRIVATE_KEY="`cat ./keys/private.pem`" \
   JWT_PUBLIC_KEY="`cat ./keys/public.pem`" \
   mvn spring-boot:run
   ```

Application will be available at: http://localhost:3001


### Environment Variables

| Variable Name     | Description                                               | Example                              |
|-------------------|-----------------------------------------------------------|--------------------------------------|
| `DB_USERNAME`     | MySQL username                                            | `chatop_user`                        |
| `DB_PASSWORD`     | MySQL password                                            | `my_secure_password`                 |
| `DB_HOST`         | JDBC URL to connect to the database                       | `jdbc:mysql://localhost:3306/chatop` |
| `JWT_PRIVATE_KEY` | Contents of your RSA private key (PEM format, PKCS#8)     | ``cat ./keys/private.pem``           |
| `JWT_PUBLIC_KEY`  | Contents of your RSA public key (PEM format)              | ``cat ./keys/public.pem``            |
| `UPLOAD_DIR`      | Server directory where uploaded images will be stored     | `/tmp/uploads`                       |
| `BASE_URL`        | Base URL used to build image URLs (should match API host) | `http://localhost:3001`              |



## API Documentation

Interactive API documentation is available at:

**http://localhost:3001/swagger-ui/index.html**

You can:
- View all available endpoints
- Test routes directly from the browser
- Authenticate with JWT to access protected routes
