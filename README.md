# BNP Online Boot Store (Test)
An online bookstore application containing both frontend and backend seperated by folders. Frontend is built using React and Tailwindcss. Backend is built with Java and Springboot.

## Getting started
This section provides a brief on how to start the backend and frontend applications.

## Backend
Prerequisite: Install Java 17, Maven 3.x
```
$> cd backend

# Install dependencies, Build Jar and Run Tests
$> mvn clean install

# Start the Backend Application
$> java -jar target/bookstore-0.0.1-SNAPSHOT.jar 
```
Alternatively you can run the BookstoreApplication class from your favorite IDE to start the application.

## Frontend
Prerequisite: Install Node, Your favorite package manager - yarn, pnpm, npm (comes with Node)
```
$> cd frontend

# Install dependencies
$> npm i

# Run Test
$> npm run test

# Start the Frontend Application
$> npm run start
```

## Browser
Navigtate to [link](http://localhost:5173) after starting the applications for vieiwng the GUI on browser. Backend is running on http://localhost:8000.
I have also added a swagger documentation for APIs available at http://localhost:8000/api/swagger-ui/index.html

## Some points to note
* The backend is running using in memory H2 DB, If you need to use a DB running on local, please update the below section in application.properties. 
```
# JDBC for DB connection
```

* The books and author are preloaded at the start of the backend application with the help of @PostContruct method of InitialLoad class