Sure, here is a `README.md` file for your project:

```markdown
# Mapper Service

Mapper Service is a Spring Boot application that provides various endpoints for URL shortening, redirection and utility functions for URL validation and encoding.

## Features

- **URL Shortening**: Generate a shortened version of a given URL.
  **Steps to shorten URL**:
     **1: Generate an MD5 hash of the long URL**
     **2: Get First 6 bytes of the hash**
     **3: Convert these bytes to decimal**
     **4: Encode the result into a Base62 encoded string** 
- **URL Redirection**: Redirect users to the original URL using the shortened URL.

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/mapper-utility.git
    cd mapper-utility
    ```

2. Build the project using Maven:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

## Usage

### Endpoints

The `URLMapperController` class provides the following endpoints:

- **Check Status**: Check if the service is running.
    - **Endpoint**: `/checkStatus`
    - **Method**: `GET`
    - **Response**: `UP` if the service is running.

- **Shorten URL**: Generate a shortened version of a given URL.
    - **Endpoint**: `/shorten`
    - **Method**: `POST`
    - **Request Body**: The URL to be shortened.
    - **Response**: A `ResponseDTO` containing the shortened URL.

- **Redirect to Long URL**: Redirect to the original long URL using the shortened URL.
    - **Endpoint**: `/{shortUrl}`
    - **Method**: `GET`
    - **Path Variable**: `shortUrl` - The shortened URL.
    - **Response**: Redirects to the original long URL if found, otherwise returns `404 Not Found`.

## Configuration/Features

The application uses Spring Retry for retrying failed URL validation attempts. The retry configuration can be found in the `RetryConfig` class.
The application uses Redis Cache for storing the short URL and long URL mapping. 

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -am 'Add new feature'`).
6. Create a new Pull Request.

```

This `README.md` file provides an overview of the project, installation instructions, usage examples, and contribution guidelines. Adjust the repository URL and other details as necessary.