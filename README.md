# Shopping-site
E-commerce website for IT products. Users can make an account, login after they verify their email, search through different categories of products and place an "order".
## Authors

- [@Kalin](https://github.com/kalin73) (Back-end)
- @Strahil (Front-end)


## Tech Stack

**Back-end:** Java, Spring Boot (Web, Data JPA)

**Front-end:** HTML, CSS, JavaScript, Thymeleaf

**Database:** PostgreSQL

**Authentication and authorization:** Spring Security 6


## Run Locally
To run the application first you have to create google account which will be used for sending emails from the application. Then open your google account and go to: **security / how you sign in to Google section / click 2-Step Verification / scroll down and click on app passwords / create.**

- install and start **Docker** on your system: https://www.docker.com/get-started/

- download the repository as zip and extract it
  
![App Screenshot](https://github.com/kalin73/Shopping-site/blob/main/Screenshots/gitCloneUrl.png?raw=true)

- go to **Shopping-site-main/Shopping** and open **docker-compose.yml**

- add values for the following environment variables:

    `EMAIL` - the gmail you created at the start

    `PASSWORD` - generated password for applications for that gmail **(not the account password)**

- close the **docker-compose.yml** file and open terminal from **Shopping** folder
- enter following command:
```bash
  docker-compose -f docker-compose.yml up
```
If every environment variable is added the application will run on **http://localhost:8080**

- to stop the application press **Ctrl + C** in the terminal or use command:
```bash
  docker stop shopping-app shopping-db
```

## Screenshots
Home page
![App Screenshot](https://github.com/kalin73/Shopping-site/blob/main/Screenshots/Home%20page.png?raw=true)

Categories page
![App Screenshot](https://github.com/kalin73/Shopping-site/blob/main/Screenshots/Categories.png?raw=true)

Products page
![App Screenshot](https://github.com/kalin73/Shopping-site/blob/main/Screenshots/Products%20page.png?raw=true)

Product info page
![App Screenshot](https://github.com/kalin73/Shopping-site/blob/main/Screenshots/Product%20page.png?raw=true)
