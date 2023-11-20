# Android Calculator App with User Authentication
## 👉 Overview
This Android Calculator app is an extended version of a simple calculator (check repo : https://github.com/HafsaTATA/Calculator_app.git ), now featuring user authentication and management. The app is developed in Java and uses an SQLite database to manage user access.

## 👉 Features

- **Login/Signup Interface**: Users can log in using their email and password or sign up by providing their email, password, name, and family name.

- **User Authentication**: The app verifies the user's identity by checking their credentials against an SQLite database. New users must sign up, and existing users can log in.

- **Administrator and Regular Users**: Users are categorized as administrators or regular users. Administrators can add or remove users, while regular users have access to the calculator.

- **Secure Sign-Up**: The sign-up interface ensures that the entered email doesn't already exist in the SQLite database, preventing duplicate accounts.

- **Calculator Interface**: After successful login or signup, regular users are redirected to the calculator interface where they can perform mathematical operations.

## 📱 Usage

1. **Login/Signup**: Launch the app and enter your email and password to log in. If you don't have an account, use the signup option to create one.

2. **Calculator Interface**: Upon successful login or signup, regular users are redirected to the calculator interface.

3. **Administrator Interface**: If you log in as an administrator, you'll be directed to the admin interface, allowing you to manage user accounts.

## Getting Started

### 💻 Prerequisites

- Android Studio
- SQLite Database

### 🔧 Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/your_username/your_repository.git

2. Open the project in Android Studio.

3. Build and run the app on an Android Emulator or a physical Android device.


## 📰 License
This project is open-source and available under the MIT License.

## 💁Contributing
If you'd like to contribute to this project, please fork the repository and create a pull request. You can also open issues for bug reports or feature requests.
