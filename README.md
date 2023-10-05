# edTechREST (JDBC, Servlets, MySQL, Docker, Testcontainers, HikariCP)
++

## Endpoints

### GET
- `http://localhost:8080/students/1` - Получить студента с id = 1
- `http://localhost:8080/courses/4` - Получить курс с id = 4
- `http://localhost:8080/teachers/2` - Получить учителя с id = 2
- `http://localhost:8080/students/` - Получить список всех студентов (внутри список курсов, на которые подписан студент)
- `http://localhost:8080/courses/` - Получить список всех курсов (внутри список студентов, подписанных на курс)
- `http://localhost:8080/teachers/` - Получить список всех учителей (внутри список всех курсов, которые ведет учитель)

### POST
- `http://localhost:8080/teachers/` - Добавить учителя. Body raw JSON:
```json
{
    "name": "Михаил Акимов",
    "age":33
}
```
- `http://localhost:8080/students/` - Добавить студента. Body raw JSON:
```json
{
    "name": "Михаил Акимов",
    "age":31
}
```
- `http://localhost:8080/courses/` - Добавить курс. Body raw JSON:
```json
{
    "name": "Португальский язык",
    "price": 123000,
    "duration": 12
}
```
- `http://localhost:8080/management/?studentId=1&courseId=1` - Добавить студента с id = 1 на курс c id = 1

### PUT
- `http://localhost:8080/management/?&teacherId=1&courseId=2&action=add` - Добавить учителя с id = 1 на курс c id = 2
- `http://localhost:8080/management/?&teacherId=2&courseId=3&action=replace` - Заменить учителя на курсе с id = 2 на курс c id = 3
- `http://localhost:8080/students/` - Обновить данные студента с id = 2. Body raw JSON:
```json
{
    "id":"2",
    "name":"Патрикей Патрикеев",
    "age": 31
}
```

- `http://localhost:8080/teachers/` - Обновить данные учителя с id = 2. Body raw JSON:
```json
{
    "id":"2",
    "name":"Никита Минин",
    "age": 29
}
```

- `http://localhost:8080/courses/` - Обновить данные курса с id = 2. Body raw JSON:
```json
{
    "id":"2",
    "name": "Испанский язык",
    "price": 95000,
    "duration": 11
}
```
### DELETE
- `http://localhost:8080/courses/2` - Удалить курс с id = 2
- `http://localhost:8080/students/2` - Удалить студента с id = 2
- `http://localhost:8080/management?studentId=1&courseId=1` - Удалить студента с id = 1 с курса c id = 1
