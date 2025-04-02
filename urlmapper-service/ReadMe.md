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
    - **HTTP Status Code** : 200 OK (positive scenario).
 ResponseDTO will contain details of Error HttpStatus code and detailed error message.

| Exception             | Error Code | Error Message                                                   |
|-----------------------|------------|-----------------------------------------------------------------|
| SyntaxURLException    | 400        | The input URL to be shortened is malformed                      |
| InvalidURLException   | 400        | The input URL to be shortened is either not valid or not reachable |
| DataBaseException     | 500        | Some DB Exception has occurred                                  |
| URLExpiredException   | 404        | The shortened URL has expired in the system. Please try to generate again. |

#Shorten URL Design:
 - **1.**:Validates the provided URL using the URLValidator and also check if it is not malformed.
 - **2.**:If the URL is valid from above step, it pings the URL to check its availability.
 - **3.**:Fetches existing URL details from the database.
 - **4.**:Depending on the action mode (NO_ACTION_MODE, SAVE_MODE, UPDATE_MODE), it:
   - **a.**:Returns the existing short URL(when URL exists within current date time range - NO_ACTION_MODE).
   - **b.**:Generates a new short URL, checks for its existence in the database, and persists it. (when URL doesn't exist in DB - SAVE_MODE)
   - **c.**:Updates the existing short URL record with dates in the database(when URL exists, but not in current time range - UPDATE_MODE)
 - **5.**:If the URL is invalid, it throws an InvalidURLException.
 - **6.**:Duplicate URL handling scenarios:
   - Duplicate Long URL Scenario 1- if the same long URL exists in system and it is effective within current date, then respective short url(already stored in DB) will be returned.
   - Duplicate Long URL Scenario 2 - if the same long URL exists in system and it is effective within current date, then effective end date will be updated as high date in DB for the existing record.
   - Duplicate Short URL - After shortening a long URL, there is an additional check(recursive function) to check if the short URL already exists in DB.


- **Redirect to Long URL**: Redirect to the original long URL using the shortened URL.
    - **Endpoint**: `/{shortUrl}`
    - **Method**: `GET`
    - **Path Variable**: `shortUrl` - The shortened URL.
    - **Response**: Redirects to the original long URL if found.
    - **HTTP Status Code** : 302 Found (if redirection is successful) or 404 Not Found (if the short URL is not found)

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