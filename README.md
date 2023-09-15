## SocialMediaWeb

*RESTful API для социальной медиа платформы, позволяющая пользователям регистрироваться, входить в систему, создавать
посты, переписываться, подписываться друг на друга и получать свою ленту активности*

## Бэкенд-часть проекта предполагает реализацию следующего функционала: ##

### 1. Аутентификация и авторизация:
   
![](https://github.com/AlekseyPetkun/SocialMediaWeb/blob/master/screens/Аутентификация%20и%20авторизация.png)

### - Пользователи могут зарегистрироваться, указав имя и фамилию пользователя, электронную почту и пароль.

***POST*** localhost:8081/api/auth/register
**curl:**
curl -X 'POST' \
  'http://localhost:8081/api/auth/register' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "first_name": "user",
    "last_name":  "userovic",
    "email":	  "user@mail.ru",
    "password":	  "password"
}'

`RegisterRequest`
```json
{
    "first_name": "user",
    "last_name":  "userovic",
    "email":	  "user@mail.ru",
    "password":	  "password"
}
```

### - Пользователи могут войти в систему, предоставив правильные учетные данные (логин (почта) и пароль).

***POST*** localhost:8081/api/auth/login
**curl:**
curl -X 'POST' \
  'http://localhost:8081/api/auth/login' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "username": "user@mail.ru",
    "password":	"password"
}'

`LoginRequest`
```json
{
    "username": "user@mail.ru",
    "password":	"password"
}
```

### - API обеспечивает защиту конфиденциальности пользовательских данных, включая хэширование паролей и использование JWT.

### 2. Управление постами:
   
![](https://github.com/AlekseyPetkun/SocialMediaWeb/blob/master/screens/Управление%20постами.png)

### - Пользователи могут создавать новые посты, указывая текст, заголовок и прикрепляя изображения.

***POST*** localhost:8081/api/posts
**curl:**
curl -X 'POST' \
  'http://localhost:8081/api/posts' \
  -H 'accept: application/json' \
  -H 'Content-Type: multipart/form-data' \
  -F 'dto={
  "title": "Заголовок поста",
  "content": "Содержание поста"
}' \
  -F 'image=@2023-09-15_17-50-04.png;type=image/png'

`CreatePost`
```json
{
    "title":    "Заголовок поста",
    "content":	"Содержание поста"
}
```

### - Пользователи могут просматривать посты других пользователей.

***GET*** localhost:8081/api/posts
**curl:**
curl -X 'GET' \
  'http://localhost:8081/api/posts?page_number=0&page_size=10' \
  -H 'accept: application/json'

`ResponseWrapperPosts`

```json
{
  "count": 1,
  "results": [
    {
      "id":	            1,
      "title":	            "Заголовок поста",
      "content":	    "Содержание поста",
      "image":	            "путь к картинке",
      "date_time_post":	    "2023-09-15T12:01:36",
      "author_post":	    1
    }
  ]
}
```

### - Пользователи могут обновлять и удалять свои собственные посты.

***PATCH*** localhost:8081/api/posts
**curl:**
curl -X 'PATCH' \
  'http://localhost:8081/api/posts/1' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "title": "Заголовок поста",
  "content": "Содержание поста"
}'

`UpdatePost`

```json
{
  "title":      "Заголовок поста",
  "content":	"Содержание поста"
}
```

### 3. Взаимодействие пользователей:
   
![](https://github.com/AlekseyPetkun/SocialMediaWeb/blob/master/screens/Взаимодействие%20пользователей.png)

- Пользователи могут отправлять заявки в друзья другим пользователям. С этого момента, пользователь, отправивший заявку,
  остается подписчиком до тех пор, пока сам не откажется от подписки. Если пользователь, получивший заявку, принимает
  ее, оба пользователя становятся друзьями. Если отклонит, то пользователь, отправивший заявку, как и указано ранее, все
  равно остается подписчиком.
- Пользователи, являющиеся друзьями, также являются подписчиками друг на друга.
- Если один из друзей удаляет другого из друзей, то он также отписывается. Второй пользователь при этом должен остаться
  подписчиком.
- Пользователи могут писать друг другу сообщения

![](screens/Сообщения.png)

### 4. Подписки и лента активности:

- Лента активности пользователя отображает последние посты от пользователей, на которых он подписан.
- Лента активности поддерживает пагинацию и сортировку по времени создания постов.

### 5. Обработка ошибок:

- API обрабатывает и возвращать понятные сообщения об ошибках при неправильном запросе или внутренних проблемах
  сервера.
- API осуществляет валидацию введенных данных и возвращает информативные сообщения при неправильном формате

### 6. Документация API:

- API задокументировано с использованием инструментов OpenAPI.

---

## Спецификация OpenAPI для описания API ##

[OpenAPI specification](openapi.yaml "OpenAPI")

## Использован следующий стек технологий: ##

Java 17\
SpringBoot 3\
Security\
JWT tokens\
Hibernate\
PostgreSQL\
Lombok\
Gradle\
Flyway\
Docker\
Mapstruct\
OpenAPI

## Структура базы данных: 

![2023-06-27_22-09-29](https://github.com/AlekseyPetkun/SocialMediaWeb/blob/master/screens/Схема%20БД.png)

##

[![Typing SVG](https://readme-typing-svg.herokuapp.com?color=%2336BCF7&lines=thank+you+for+your+attention)](https://git.io/typing-svg)
