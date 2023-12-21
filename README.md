# Getting Started

### RabbitMQ Docker Image

```powershell
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=userName -e RABBITMQ_DEFAULT_PASS=password rabbitmq:3.12-management
```
then run docker image.

### Update application.properties File
update the following prop in application.properties

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=port
spring.rabbitmq.username=username
spring.rabbitmq.password=password
```
### Direct Exchange Examples
#### Example 1
```bash
curl --location 'http://localhost:8080/direct-exchange?message=hello%20world1&routingKey=log1'
```

```text
Message sent: hello world1
q1 ------------> Received message: hello world1
```

#### Example 2
```bash
curl --location 'http://localhost:8080/direct-exchange?message=hello%20world2&routingKey=log2'
```

```text
Message sent: hello world2
q2 ------------> Received message: hello world2
```
#### Example 3
```bash
curl --location 'http://localhost:8080/direct-exchange?message=hello%20world3&routingKey=log3'
```

```text
Message sent: hello world3
q3 ------------> Received message: hello world3
```
#### Example 4
```bash
curl --location 'http://localhost:8080/direct-exchange?message=hello%20world4&routingKey=log4'
```

```text
Message sent: hello world4
q4 ------------> Received message: hello world4
```

#### Example 5
```bash
curl --location 'http://localhost:8080/direct-exchange?message=hello%20world5&routingKey=log5'
```

```text
Message sent: hello world5
```
### Topic Exchange Examples

#### Example 1
```bash
curl --location 'http://localhost:8080/topic-exchange?message=hello%20world&routingKey=ayah.log1.log'
```

```text
Message sent: hello world
q3 ------------> Received message: hello world
q1 ------------> Received message: hello world
```

### Example 2
```bash
curl --location 'http://localhost:8080/topic-exchange?message=hello%20world&routingKey=ayah.log2.log'
```

```text
Message sent: hello world
q3 ------------> Received message: hello world
q4 ------------> Received message: hello world
q2 ------------> Received message: hello world
```

### Headers Exchange Example

#### Example 1
```bash
curl --location 'http://localhost:8080/header-exchange?message=hello%20world&type=email'
```

```text
Message sent: hello world
q2 ------------> Received message: hello world
```
#### Example 2

```bash
curl --location 'http://localhost:8080/header-exchange?message=hello%20world&type=message'
```

```text
Message sent: hello world
q1 ------------> Received message: hello world
```

#### Example 3
```bash
curl --location 'http://localhost:8080/header-exchange?message=hello%20world&type=notification'
```

```text
Message sent: hello world
q3 ------------> Received message: hello world
```

#### Example 4
```bash
curl --location 'http://localhost:8080/header-exchange?message=hello%20world&type=sms'
```

```text
Message sent: hello world
q4 ------------> Received message: hello world
```

#### Example 5
```bash
curl --location 'http://localhost:8080/header-exchange?message=hello%20world&type=mms'
```

```text
Message sent: hello world
```

### Fanout Exchange Example

#### Example 1
```bash
curl --location 'http://localhost:8080/fanout-exchange?message=hello%20world'
```

```text
Message sent: hello world
q4 ------------> Received message: hello world
q3 ------------> Received message: hello world
q1 ------------> Received message: hello world
q2 ------------> Received message: hello world
```