
# urlmapper-batches

This project is a Spring Batch application that processes URL mappings and updates their effective end dates.

## Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher
- A running instance of a database (e.g., MySQL, PostgreSQL)

## Installation

### Step 1: Clone the repository

```sh
git clone https://github.com/yourusername/urlmapper-batches.git
cd urlmapper-batches
```

### Step 2: Build the project using Maven

```sh
mvn clean install
```

## Configuration

### Database Configuration

Update the `application.properties` file with your database configuration:

```ini
spring.datasource.url=jdbc:mysql://localhost:3306/yourdatabase
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Application Properties

Add any additional properties required for your application in the `application.properties` file:

```ini
# Application properties
application.daysToExpire=30
```

## Running the Application

### Step 1: Run the Spring Boot application

```sh
mvn spring-boot:run
```

### Step 2: Verify the Batch Job Execution

The batch job is scheduled to run based on the cron expression defined in the `BatchJobScheduler` class. You can check the logs to verify the job execution.

## Usage

### Batch Job Scheduler

The `BatchJobScheduler` class schedules and launches the batch job:

- **Cron Expression**: `* * * * * *` (runs every second)
- **Job Parameters**:
    - `JobID`: Unique job ID
    - `effectiveCalculatedDate`: Calculated date based on `daysToExpire` property
    - `runId`: Unique run ID

### Batch Job Configuration

The `UpdateDateJobConfig` class configures the batch job steps:

- **Reader**: Reads `UrlMapperEntity` records from the database
- **Processor**: Updates the effective end date of each record
- **Writer**: Saves the updated records back to the database

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -am 'Add new feature'`).
5. Push to the branch (`git push origin feature-branch`).
6. Create a new Pull Request.

## License

```