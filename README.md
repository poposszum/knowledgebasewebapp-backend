
## Getting Started
###### Please follow the instructions in order to run the full project on your local machine for testing purposes.
### Installing
###### If you do not have an up-to-date [JDk](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html), download and install Amazon Coretto's latest version. After the installation you should check if everything is alright, by typing in the git bash window the following command:
`java --version`
###### If you done everything right you should see something like this:

openjdk 11.0.3 2019-04-16 LTS
OpenJDK Runtime Environment Corretto-11.0.3.7.1 (build 11.0.3+7-LTS)
OpenJDK 64-Bit Server VM Corretto-11.0.3.7.1 (build 11.0.3+7-LTS, mixed mode)
###### Download the .jar file, and make sure the database is up and running.
###### In order to start the application navigate to the folder which contains the .jar file, start a git bash and type:
 `java -jar "your .jar file name".jar`
###### The application should start with no problem, and you can start the testing process.
###### You can reach the website on http://localhost8080
## Disclaimer
###### This is an early version with a lot of bugs!
###### After restarting the program, the content of the database will be cleaned!
## Users
###### The database should contain an ADMIN user and a USER user by default:
`email: admin@admin.com`
`password: admin123!`

`email: user@user.com`
`password: user123!`
