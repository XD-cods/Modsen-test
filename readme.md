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

- Установить docker
- Установить make (для облегченного запуска приложения)

А также в [config сервере](./config-server/src/main/resources/application.yml) поменять spring.profiles.active на
default если вы хотите получить настройки с config сервера

### Запуск приложения

Если вы хотите запустить микросервисы с помощью .env файла, то создайте его в корне проекта
с переменными:

```properties
POSTGRES_USER=postgres
POSTGRES_PASSWORD=1111
POSTGRES_DB=postgres
PGADMIN_DEFAULT_EMAIL=postgres@gmail.com
PGADMIN_DEFAULT_PASSWORD=1111
GIT_URI=your git uri for ssh
SSH_GIT_PRIVATE_KEY=your ssh key
```

Выполните команду:

```shell
   make build-env
```

или вы можете выполнить в git bash:

```shell
   make build
```

что создаст docker-compose образ с default значениями

### Pgadmin

Также есть админка для взаимодействия с базой данных

<localhost:5050>

по умолчанию стоят следующие данные для входа:

```properties
PGADMIN_DEFAULT_EMAIL=postgres@gmail.com
PGADMIN_DEFAULT_PASSWORD=1111
```

### Swagger

переходим по ссылке на swagger сайт

<http://localhost:8080/swagger-ui.html>

