# **Ares - Mars Photo Exploration App**

Ares is a Mars photo exploration app that allows users to browse and favorite photos taken by various rovers on Mars. This app is built using Spring Boot with JPA for the backend, MySQL for the database, and Angular for the frontend. It leverages data from the Mars Photo API, a third-party API that provides access to the latest photos taken by Mars rovers.

## **Database Design**

The application's database has three main tables: User, Favorites, and Category.

### **User Table**

- **`id`**: Unique identifier for each user.

### **Favorites Table**

- **`id`**: Unique identifier for each favorite entry.
- **`date`**: Date when the photo was favorited.
- **`categoryId`**: Foreign key referencing the Category table to link the favorite with a specific category.
- **`photoId`**: Foreign key referencing the Photo table to identify the favorited photo.
- **`userId`**: Foreign key referencing the User table to associate the favorite with a specific user.

### **Photo Table**

- **`id`**: The identifier for each photo from the third-party API. (Note: The photo IDs are not auto-incremented, and the IDs used are from the third-party API.)
- **`imgSrc`**: URL to the photo.
- **`camera`**: The camera used to capture the photo.
- **`rover`**: The name of the rover.

### **Category Table**

- **`id`**: Unique identifier for each category.
- **`date`**: Date when the category was created.
- **`name`**: The name of the category.

## **REST API**

The backend exposes a RESTful API to interact with the Ares app. Below are the available endpoints:

### **`GET /api/v1/rovers/latestPhotos`**

- Returns the newest photos from each rover, sorted by most recently active first.

### **`GET /api/v1/userFavorites`**

- Returns favorited photos from the third-party API and the category they're in using the user's favorites from the database.

### **`POST /api/v1/makeFavorite/{id}`**

- Adds the specified photo **`id`** as a favorite in the database.

### **`DELETE /api/v1/removeFavorite/{id}`**

- Removes the specified photo **`id`** from the user's favorites in the database.

### **`POST /api/v1/createCategory`**

- Adds a new category to the database.

### **`PUT /api/v1/updateFavorite/{id}`**

- Updates the category of the favorite identified by the **`id`**.

### **`GET /api/v1/searchPhotos`**

- Returns photos that match the specified search parameters from the third-party API.

## **Frontend - Angular**

The frontend of Ares is built using Angular, a popular front-end framework. Angular provides a robust set of tools for building dynamic and responsive single-page applications.

The Angular frontend consumes the REST API provided by the backend to display Mars rover photos, manage favorites, and explore Mars in an intuitive user interface.

## **Third-Party API**

The app interacts with the Mars Photo API to retrieve the latest photos from Mars rovers. For API details, refer to the **[third-party API documentation](https://github.com/corincerami/mars-photo-api)**.

## **Technologies Used**

- Spring Boot with JPA for the backend implementation
- Angular for the frontend implementation
- MySQL database for data storage
- Third-party Mars Photo API for retrieving Mars rover photos

## **Setup**

To set up the Ares app, follow these steps:

1. Clone the repository from GitHub.
2. Set up a MySQL database and update the database connection configuration in the Spring Boot application properties.
3. Build and run the Spring Boot backend.
4. Set up Node.js and Angular CLI for the frontend.
5. Build and run the Angular frontend.
