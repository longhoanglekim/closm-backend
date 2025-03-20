# closm-backend

## Setup

1. pull image :
   - docker pull mysql
1. Run database
   - docker run --name closm-mysql -e MYSQL_ROOT_PASSWORD=12345678 -p 3306:3306 -d -v $(pwd)/database.sql:/docker-entrypoint-initdb.d/database.sql mysql:latest
     (pwd : your absolute directory to mysql file)
