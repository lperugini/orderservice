# OrderService

OrderService è un semplice microservizio per la gestione degli ordini, sviluppato in **Spring Boot** con supporto a **RabbitMQ** e **H2 Database**.

Questa versione rappresenta una demo accademica. 

OrderService è sviluppato come microservizio autonomo ma è da intendersi come parte di un sistema composto da:
- **userservice** per la gestione degli utenti.
- **itemservice** per la gestione dei prodotti.
- **paymentservice** per la gestione dei pagamenti.
- un API **gateway**.

## Avvio del Servizio

### **1. Prerequisiti**
- **JDK 17** o superiore
- **Maven 3+**
- **Docker e Docker Compose** (per l'esecuzione in container)

### **2. Configurazione**
Le configurazioni sono definite nel file `application.properties` situato in `src/main/resources`:

```properties
spring.application.name=orderservice
server.port=8083
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:orderservice

spring.rabbitmq.host=${RABBITMQ_HOST:rabbitmq}
spring.rabbitmq.port=${RABBITMQ_PORT:5672}
spring.rabbitmq.username=${RABBITMQ_USER:user}
spring.rabbitmq.password=${RABBITMQ_PASS:password}
```

### **3. Costruzione del progetto**
Compila il progetto ed esegui i test con:
```sh
mvn clean package
```

### **4. Esecuzione**

#### **4.1 - Avvio manuale:**
```sh
java -jar target/orderservice.jar
```
#### **4.2 - Avvio con Docker**
Puoi containerizzare OrderService con il seguente `Dockerfile`:

```dockerfile
# Fase 1: Costruzione del JAR
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copia del file pom.xml e delle dipendenze Maven
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia del codice sorgente e build del progetto
COPY src ./src
RUN mvn clean package -DskipTests

# Fase 2: Creazione dell'immagine per l'applicazione
FROM openjdk:17
WORKDIR /app

# Copia del JAR generato nella fase precedente
COPY --from=build /app/target/*.jar app.jar

# Comando di avvio
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
```

Il servizio RabbitMQ deve essere avviato insieme all'applicazione. Usa il seguente `docker-compose.yml`:

```yaml
version: '3.8'
services:
  orderservice:
    build: .
    ports:
      - "8083:8083"
    depends_on:
      - rabbitmq
    environment:
      SPRING_RABBITMQ_HOST: rabbitmq

  rabbitmq:
    image: "rabbitmq:3-management"
    ports:
      - "5672:5672"
      - "15672:15672"
```

Avvia tutto con:
```sh
docker-compose up --build
```


## **API Endpoints**
Ecco gli endpoint disponibili:

- **GET** `/orders` → Restituisce la lista di tutti gli ordini.
- **GET** `/orders/{id}` → Ottiene i dettagli di un ordine specifico.
- **PUT** `/orders/{id}` → Modifica le informazioni di un'ordine esistente.
```json
{
    "id": 1,
    "item": 1,
    "user": 1,
    "description": "Some useful description.",
    "price": 9.99
}
```
- **DELETE** `/orders/{id}` → Rimuove un ordine esistente.

---
**Autore:** _Leonardo Perugini - leonardo.perugini2@studio.unibo.it_