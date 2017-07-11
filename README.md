# Тест

Приложение на Spring Boot

## /hello/contacts
Запрос контактов

### _Параметры_
**nameFilter** | Регулярое выражение для отбора контактов
**skip** | Количество пропущеных результатов (необязательный параметр)
**max** | Максимальное количество контактов в ответе (необязательный параметр)
--- | ---

### _Результат_
При удачном выполнении, возвращается список контактов со следующими полями:
* name - имя контакта
* id - идентификатор

### _Ошибки_
* 400 Bad Request (некорректный запрос, неправильные параметры)
* 500 Internal server error (внутренняя ошибка сервера)


## /hello/new
Создание случайных контактов

### _Параметры_
**count** | Количество контактов которое будет создано (необязательный параметр)
--- | ---

### _Результат_
При выполнении, возвращается объект с полем text содеращим статус выполнения

## Развёртывание
 Приложение сконфигурировано для работы с базой на PostgreSQL с именем hello от пользователя bot с паролем qwe321.
 Скрипт таблицы лежит в [sql/create.sql](sql/create.sql)
 После запуста приложения можно заполнить базу случайными значениями, к примеру: curl http://localhost:8080/hello/new?count=10000