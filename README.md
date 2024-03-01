# Shopping-site
E-commerce website for IT products.

The application is developed with Spring framework and PostgreSQL database as data storage.
For authentication and authorization is used Spring Security 6. Most of the web pages are created with Thymeleaf template engine and some (products and product info pages) with JavaScript fetching information from REST api.
## Authors

- [@Kalin](https://github.com/kalin73) (Back-end)
- [@Strahil](https://github.com/Strahil731) (Front-end)


## Environment Variables

To run this project, you will need to add the following environment variables to your .env file or IDE run configuraton.

`DB_USERNAME` - Username for the PostgreSQL database

`DB_PASSWORD` - Password for the PostgreSQL database

`EMAIL` - Email from which the application sends verification emails

`PASSWORD` - Password for that email

## Run Locally
To run the application first you have to create google account which will be used for sending emails from the application.
Then open your google account and go to **security / signing in to Google section / password & sign-in method section / click app passwords / generate.**

The easiest way to run the application is to run it on IntelliJ IDE
- download and install IntelliJ IDEA Community Edition:
  https://www.jetbrains.com/idea/download/?section=windows

-

## Screenshots
Home page
![App Screenshot](https://github.com/kalin73/Shopping-site/blob/main/Screenshots/Home%20page.png?raw=true)

Categories page
![App Screenshot](https://github.com/kalin73/Shopping-site/blob/main/Screenshots/Categories.png?raw=true)

Products page
![App Screenshot](https://github.com/kalin73/Shopping-site/blob/main/Screenshots/Products%20page.png?raw=true)

Product info page
![App Screenshot](https://github.com/kalin73/Shopping-site/blob/main/Screenshots/Product%20page.png?raw=true)
