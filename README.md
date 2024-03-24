# Real-Time Chat Application using Spring Boot, Spring Security, MongoDB, and WebSocket

This project is a real-time chat application built using Spring Boot, Spring Security, MongoDB, and WebSocket technologies. It allows users to register, log in securely, and exchange messages in real-time with other users.

**Features:**

* User Registration and Authentication: Users can register an account with a unique username and password. Upon successful registration, the application provides a JWT authentication token for subsequent logins.

* User Search: Users can search for other users by their username. Searching is facilitated using JWT authentication tokens.

* WebSocket Handshake: Users can establish a WebSocket connection by providing their JWT authentication token in the request header.

* Check-In Mechanism: Users can perform a check-in operation, providing a random topic ID (UUID) to receive messages. This ensures that messages are securely transmitted to the intended recipient only.

* Message Storage and Retrieval: The server stores messages sent to users while they were offline. Upon check-in, stored messages are sent to the user.

* Message Sending: Users can send messages to other users by specifying the recipient's public ID, message type, and message body in the request body.

**Getting Started:**

To run the application locally, follow these steps:

Clone this repository.
Configure MongoDB settings in application.properties.
Build and run the Spring Boot application.
Use API endpoints to register, log in, search for users, establish WebSocket connections, and exchange messages.
Technologies Used:
Spring Boot: For building the backend application.
Spring Security: For user authentication and authorization.
MongoDB: For storing user data and messages.
WebSocket: For real-time messaging functionality.

**Contributors:**

[Jerin Johnykutty](https://github.com/J3rryCodes)

Thanks .. :)
