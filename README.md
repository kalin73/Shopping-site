# Shopping-site
E-commerce website for IT products. Users can make an account, login after they verify their email, search through different categories of products and place an "order".
## Authors

- [@Kalin](https://github.com/kalin73) (Back-end)
- [@Strahil](https://github.com/Strahil731) (Front-end)


## Tech Stack

**Back-end:** Java, Spring web

**Front-end:** HTML, CSS, JavaScript, Thymeleaf

**Database:** PostgreSQL

**Authentication and authorization:** Spring Security 6


## Run Locally
To run the application first you have to create google account which will be used for sending emails from the application. Then open your google account and go to: **security / signing in to Google section / click 2-Step Verification / scroll down and click on app passwords / generate.**

- install **Git** on your system: https://git-scm.com/download/win

- install **PostgreSQL** server on your system: https://www.enterprisedb.com/downloads/postgres-postgresql-downloads

- download and install **IntelliJ IDEA Community Edition**:
  https://www.jetbrains.com/idea/download/?section=windows

- open IntelliJ and click on **Get from VCS**

- copy and paste the repository url 
```bash
  https://github.com/kalin73/Shopping-site.git
```

- when you open the project IntelliJ may ask you to setup JDK, click setup, choose **JDK 17** and wait

- open the main class: **Shopping/src/main/java/com/example/shopping/ShoppingApplication**

- then click the three dots right from debug symbol on the top bar and choose **Run with Parameters**

- add the following environment variables in the format **"NAME=VALUE;NAME2=VALUE2"**:

    `DB_HOST` - host of your PostgreSQL database

    `DB_PORT` - port of the database (for PostgreSQL usually is **5432**)

    `DB_NAME` - database name

    `DB_USERNAME` - username for the database

    `DB_PASSWORD` - password for the database

    `EMAIL` - the gmail you created at the start

    `PASSWORD` - generated password for applications for that gmail **(not the account password)**

- click run
If every environment variable is added the application will run without errors.


## Screenshots
Home page
![App Screenshot](https://github.com/kalin73/Shopping-site/blob/main/Screenshots/Home%20page.png?raw=true)

Categories page
![App Screenshot](https://github.com/kalin73/Shopping-site/blob/main/Screenshots/Categories.png?raw=true)

Products page
![App Screenshot](https://github.com/kalin73/Shopping-site/blob/main/Screenshots/Products%20page.png?raw=true)

Product info page
![App Screenshot](https://github.com/kalin73/Shopping-site/blob/main/Screenshots/Product%20page.png?raw=true)
