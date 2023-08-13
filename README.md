# User Image Upload to AWS S3 with Spring Boot and React

This project demonstrates a simple web application that allows users to upload their profile images, which are then stored in an Amazon S3 bucket organized by user IDs. The backend is built using Spring Boot, and the frontend is developed using React.

## Project Overview

The application is divided into two main components: the backend built with Spring Boot and the frontend built with React. The backend provides an API for uploading images to Amazon S3, while the frontend offers a user interface for users to interact with the application.

### Backend (Spring Boot)

The Spring Boot backend is responsible for handling image uploads and interacting with Amazon S3. It exposes the following API endpoints:

- `POST /api/v1/user-profile/{userId}/image/upload`: Uploads a user's image to their dedicated folder in the S3 bucket.

- `GET /api/v1/user-profile/{userId}/image/download`: Retrieves a user's image from the S3 bucket.

### Frontend (React)

The React frontend provides a user-friendly interface for users to upload their profile images. It uses the `axios` library to communicate with the Spring Boot backend. The key components are:

- `UserProfiles`: Displays a list of user profiles, including their images and names.

- `MyDropzone`: Allows users to drag and drop or select an image file for upload.

## Setup Instructions

### Backend (Spring Boot)

1. Clone the repository to your local machine.
2. Open the `backend` folder in your preferred IDE.
3. Configure your AWS credentials in the `application.properties` file.
4. Run the Spring Boot application.

### Frontend (React)

1. Open the `frontend` folder in your terminal.
2. Run `npm install` to install the required dependencies.
3. Update the backend API URL in the frontend code to match your setup.
4. Run `npm start` to start the React development server.

## AWS Configuration

1. Create an S3 bucket for storing user images.
2. Configure your AWS credentials on your local machine.
3. Set up appropriate CORS rules for your S3 bucket to allow frontend requests.

# User Image Upload to AWS S3 with Spring Boot and React

[Project Description]

## Screenshots

### User Profile Page

![image](https://github.com/siddh1017/AWS-S3-image-upload-Spring-API/assets/110898485/bceabe49-4387-4be2-837d-753635672496)


### Image Upload Interface

![image](https://github.com/siddh1017/AWS-S3-image-upload-Spring-API/assets/110898485/6eb640ff-7b2b-4e2f-8c97-717f79734104)
