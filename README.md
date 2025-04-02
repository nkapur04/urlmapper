# URL shortening and redirection service
This is a simple service that maps a URL to a shortened URL and vice versa. 

# Functional Requirements:
- **URL shortening**: Take long URL as input and send short URL as response.
- **URL redirection**: Redirect the short URL to original corresponding long URL.
- **Validation and error handling**: Incorporate validations for input URL and show meaningful error messages for scenarios like malformed URL, expired links.
- **Link Expiration**: Links will expire after a configured time(like 5/10/20 years).

# Technical Design:
- **Database**: NoSQL database like MongoDB is most suitable to store the mapping between short URL and long URL. Though in this case, I am using an MySql database.
- **URL Mapping Service**: Exposes RESTful APIs for URL shortening and redirection.
- **Clean Up Service**: A service that runs periodically to clean up expired links.