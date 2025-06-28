# EventSync Feedback Analyzer

## Project Overview

This system allows for the creation of events, the submission of feedback for those events, and provides an aggregated summary of the sentiment breakdown per event. The project is built with Java and Spring Boot, utilizes an in-memory H2 database for local development.

**Live Application URL:** [https://eventsync-service-96257397931.europe-west1.run.app](https://eventsync-service-96257397931.europe-west1.run.app) 

---

## Technologies Used

* **Backend:** Java 17, Spring Boot 3
* **API:** Spring Web (RESTful)
* **Database:** Spring Data JPA, H2 In-Memory Database
* **AI Service:** Hugging Face Inference API (`cardiffnlp/twitter-roberta-base-sentiment` model)
* **Testing:** JUnit 5, Mockito, Spring Boot Test
* **API Documentation:** SpringDoc OpenAPI (Swagger UI)
* **Containerization:** Docker
* **Deployment:** Google Cloud Run, Google Artifact Registry, Google Secret Manager
* **Build Tool:** Apache Maven

---

## Prerequisites

Before you begin, ensure you have the following installed:
* Java Development Kit (JDK) 17 or later
* Apache Maven 3.6 or later
* An active Hugging Face account with an Access Token (API Key)

---

## Setup and Local Execution

To run the application on your local machine, follow these steps:

**1. Clone the Repository**
```bash
git clone https://github.com/gvidas333/EventSync.git
cd EventSync
```

**2. Configure the API Key**
The application requires a Hugging Face API key to function.

* Navigate to the configuration file at `src/main/resources/application.properties`.
* Add your Hugging Face Access Token to the file:

```properties
huggingface.api.key=YOUR_HUGGING_FACE_API_KEY
```

**3. Build and Run the Application**
You can run the application directly using the Maven wrapper included in the project.

* In the root directory of the project, run the following command:

```bash
./mvnw spring-boot:run
```

The application will start up and be accessible at `http://localhost:8080`.

## API Documentation and Usage

This project includes Swagger UI for interactive API documentation. Once the application is running, you can access it at:

**[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

