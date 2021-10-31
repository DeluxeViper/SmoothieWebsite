# Smoothie Makers Full Stack Website

A Progressive Web Application (that means you can *download* the website) that brings all Smoothie Makers alike to come together, create healthy & nutritious recipes, and share them with each other.

  <p align="center">
    <img src="/HomePage.png" width="700"/>
  </p>

# TLDR

* A full-stack website created using a **Vaadin** (**Java, Javascript, CSS, HTML**) frontend with a **Spring Boot** (**Java, JPQL, Spring Data JPA**) backend with a **MySQL** database
* Utilized **AWS Elastic Beanstalk** to deploy the Spring Boot + Vaadin application web application, as well as **AWS RDS** to deploy the **MySQL** database
* Leveraged user authentication (**OAuth 2.0 & Custom Auth**) and authorization using Spring Security 
* Automated retrieving nutrition facts and ingredients from google to insert into **MySQL** database using **Python** and the **BeautifulSoup4** as well as **Selenium** libraries

###### Check out the deployed Smoothie Makers Website Application here:
http://smoothiemakerswebsite-env.eba-m6qvhd3n.us-east-2.elasticbeanstalk.com/home

## Features

### The Landing page
<kbd>
  <img src="/LandingPageGif&#32;(1).gif" width="700"/>
</kbd>

### Register Your Account
<kbd>
  <img src="/RegisterPage.png" width="700"/>
</kbd>

### Log In
<kbd>
<img src="/LoginPage.png" width="700"/>
</kbd>

### Add a Smoothie and Ingredients
<kbd>
  <img src="/CreateYourOwnSmoothiePage.png" width="700"/>
</kbd>

### Your Saved Recipes all in one place
<kbd>
  <img src="/YourSavedRecipes.png" width="700"/>
</kbd>

### Share your smoothie - Create a smoothie post
<kbd>
<img src="/CreateYourOwnSmoothiePost.png" width="700"/>
</kbd>

### Check out smoothies made from others
<kbd>
  <img src="/PublicForumPage.png" width="700"/>
</kbd>

# Technical Design Features

### Built With
* Java
* Spring Framework (Spring Boot, Spring Security, Spring Data JPA, Spring OAuth2.0, Spring Web)
* Python (Selenium, BeautifulSoup4)
* MySQL
* Javascript
* HTML/CSS
* AWS (Elastic Beanstalk, RDS)

### UML Diagram (excluding the Post entity)
<kbd>
  <img src="/SmoothieUMLDiagram.png" width="700"/>
</kbd>

### Prototyped using Adobe XD (credits to Hudson Pu for the design & idea)

https://xd.adobe.com/view/2cf20e61-cfc9-4557-ae07-6fe6c6153ca4-79a1/screen/583b7fe3-4b83-4bc9-830f-0c94b8cba074/
