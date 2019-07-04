### Installing
###### First of all you will need to clone the Backend service in order to proceed.
 `git clone https://github.com/poposszum/knowledgebasewebapp-backend/tree/login`
###### Download [MongoDB](https://www.mongodb.com/download-center/community) and [Robo3T](https://robomongo.org/) in order to set up and access a local database.
###### How to start MongoDB:
###### 1. Create database directory:
 `cd C:\`
 `md "data\db"`
###### 2. Start your MongoDB server:
`"C:\Program Files\MongoDB\Server\4.0\bin\mongod.exe" --dbpath="c:\data\db"`
###### Start Robo3T. You will need to connect to the database
![screenshot](https://imgur.com/a/AI1hGB1)
###### Now let's clone the Frontend side of the project.
`git clone https://github.com/poposszum/knowledgebasewebapp-frontend`
###### Now you need to run both the backend and frontend projects.
###### To start the backend server, open IntelliJ with the project loaded, and run the KnowledgebaseBackendApplication.java main class.
######  To start the frontend server, open a command line in the knowledgebasewebapp-frontend folder and type
`ng serve`
###### The server should start on `localhost:4200`
## Disclaimer
This is an early version with a lot of bugs!
## Users
###### The database should contain an admin user:
`email: admin@admin.com`
`password: admin123`
