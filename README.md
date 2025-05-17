# closm-backend

## Setup

1. pull image :
   - docker pull mysql
   - docker pull redis
1. Run database
   - docker run --name closm-mysql -e MYSQL_ROOT_PASSWORD=12345678 -p 3306:3306 -d -v "D:/UET/MonHoc/Mobile/Project/closm-backend/src/main/resources/static/database/database.sql:/docker-entrypoint-initdb.d/database.sql" mysql:latest
     (pwd : your absolute directory to mysql file)
   - docker run --name closm-redis -p 6379:6379 redis:latest