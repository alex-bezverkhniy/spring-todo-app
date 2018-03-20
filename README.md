# Spring TODO Application

Sample application for demonstrating common techniques and methodologies of developing applications using Spring Framework.
    
For more the details see related posts:

- [Sample Spring Data JPA application](https://alex-bezverkhniy.github.io/Spring-Data-JPA-Sample-app)
- [Spring Boot Testing](https://alex-bezverkhniy.github.io/Spring-Boot-Testing)

#### [Run Application](build-with-gradle)

To run application use next command `java -jar target/spring-todo-app-0.0.1-SNAPSHOT.jar` or `java -jar build/libs/spring-todo-app-0.0.1-SNAPSHOT.jar` depending on which tool you use for building.

### [Call application](call-application) 

Here are some examples how can we call through [curl](https://curl.haxx.se)

#### Create Task
*Task data:*
```json
{
  "title": "Sample Task",
  "description": "Just simple task",
  "complete": false
}
```

```ssh
curl -X POST \
-H 'Content-Type: application/json' \
-d '{
      "title": "Sample Task",
      "description": "Just simple task",
      "complete": false
    }' \
'http://localhost:8080/api/tasks/' \
| python -m json.tool
```

#### Read Task
```ssh
curl -X GET 'http://localhost:8080/api/tasks/1' | python -m json.tool
```

#### Update Task
*Task data:*
```json
{
  "title": "Sample Task",
  "description": "Just simple task",
  "complete": true
}
```

```ssh
curl -X PUT \
-H 'Content-Type: application/json' \
-d '{
      "title": "Sample Task",
      "description": "Just simple task",
      "complete": true
    }' \
'http://localhost:8080/api/tasks/1' \
| python -m json.tool
```