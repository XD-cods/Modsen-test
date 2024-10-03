## ТЗ

### Описание

Разработка CRUD Web API для имитации библиотеки (создание, изменение, удаление,
получение), выполняется на Spring.
Должна прилагаться инструкция по запуску проекта.

### Основной Функционал Web API

1. Получение списка всех книг;
2. Получение определённой книги по её Id;
3. Получение книги по её ISBN;
4. Добавление новой книги;
5. Изменение информации о существующей книге;
6. Удаление книги.

### Информация о книге

1. ISBN;
2. Название;
3. Жанр;
4. Описание;
5. Автор;

### Дополнительный Функционал Web API

1. Разработать дополнительный сервис (LibraryService), ведущей учет
   свободных книг.
2. При добавлении новой книги в первый сервис, отправляется запрос
   (синхронный или асинхронный), содержащий ID книги.
3. Новый сервис хранит информацию:
   a. О книге (ID);
   b. Время, когда книгу взяли;
   c. Время, когда книгу нужно вернуть.

### Функционал API

1. Получение списка свободных книг.
2. Изменение информации о книге.

3. Spring, Boot, MVC.
4. ORM: Hibernate, Spring Data, Jpa.
5. RDBMS: MySql, MSSql or PostgreSQL or any other.
6. ModelMapper, MapStruct or any other;
7. Authentication via bearer token;
8. Swagger

### Перед запуском

Установите postgreSQL и добавьте данные в config-server/src/main/resources/config
поменять в application.yml на свои значения

```yaml
    username: postgres
    password: 1111
    url: jdbc:postgresql://localhost:5432/postgres
```

Выполните скрипт создания таблиц расположенный в папке sripts create_db.sql


### Запуск приложения

выполнить команду для упарковки каждого микросервиса:

```
make mBuild
```
или

```
mvn clean package -DskipTests
```

выполнить запуск jar файлов

```
java -jar eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar
```

```
java -jar conig-server/target/config-server-0.0.1-SNAPSHOT.jar
```

```
java -jar library-service/target/library-service-0.0.1-SNAPSHOT.jar
```

```
java -jar book-service/target/book-service-0.0.1-SNAPSHOT.jar
```

```
java -jar api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar
```

### Swagger

переходим по ссылке на swagger сайт

<http://localhost:8080/swagger-ui.html>

