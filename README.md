Welcome to Tomcat 7 RabbitMQ ClickStart

This is a "ClickStart" that gets you going with a simple RabbitMQ "seed" project starting point, which will show you the way you should use RabbitMQ on CloudBees platform.

<a href="https://grandcentral.cloudbees.com/?CB_clickstart=https://raw.github.com/fbelzunc/tomcat7-rabbitmq-clickstart/master/clickstart.json"><img src="https://d3ko533tu1ozfq.cloudfront.net/clickstart/deployInstantly.png"/></a>

This will setup a continuous deployment pipeline - a CloudBees Git repository, a Jenkins build compiling and running the test suite (on each commit).

Should the build succeed, this seed app is deployed on a Tomcat 7 container.

# RabbitMQ on CloudBees platform

CloudBees offers RabbitMQ as a Service through CloudAMQP, once of their technological partners.

<img alt="Bees Shop - CloudBees MySQL" src="https://raw.github.com/fbelzunc/tomcat7-rabbitmq-clickstart/master/src/site/img/rabbitmq-cloudamqp.png" style="width: 70%;"/>

# Application Overview

<img alt="Bees Shop - CloudBees MySQL" src="https://raw.github.com/fbelzunc/tomcat7-rabbitmq-clickstart/master/src/site/img/rabbitmq-snapshot.png" style="width: 70%;"/>

This ClickStart is based on the Hello World! implementation on RabbitMQ doc (http://www.rabbitmq.com/tutorials/tutorial-one-java.html).

<img alt="Bees Shop - CloudBees MySQL" src="https://raw.github.com/fbelzunc/tomcat7-rabbitmq-clickstart/master/src/site/img/rabbitmq-functionality.png" style="width: 70%;"/>

The only different is the **durable** queue used in which the consumer will confirm by **ack** every message that it reads.

RabbitMQServer.java
```java
 	boolean durable = true;
    channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
```
RabbitMQClient.java
```java
 	boolean autoACK = false;
    channel.basicConsume(QUEUE_NAME, autoACK, consumer);
```
```java
 	channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
```

## Configure RabbitMQ in your application

#### Declare RabbitMQ client jar in your Maven pom.xml

```xml
 	<!-- RabitMQ -->
	<dependency>
	    <groupId>com.rabbitmq</groupId>
	    <artifactId>amqp-client</artifactId>
	    <version>3.0.4</version>
	</dependency>
```

# Create application manually

### Create Tomcat container

```sh
  bees app:create -a rabbitmq -t tomcat7
```

### Deploy your application

```sh
  bees app:deploy -a rabbitmq -t tomcat7 app.war
```
