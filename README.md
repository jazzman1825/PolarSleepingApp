# PolarDemo
Company Oriented Project 2023.

This project was built using Kotlin, Jetpack Compose and utilizes AppAuth in order to perform OAuth2 authorization with Polar Accesslink. The app fetches Polar's user sleeping data, acquired from Polar Wristwatch and visualises it for the user. 

Using AppAuth, the user is redirected to PolarFlow authentication where email and password are required. 

![1](https://user-images.githubusercontent.com/71312224/234663414-692b6665-cb15-456a-9b28-23046f1887bf.png)
![2](https://user-images.githubusercontent.com/71312224/234663429-578e3602-9a3e-41d4-9bd7-3f5b64112d4f.png)

![3](https://user-images.githubusercontent.com/71312224/234663663-05d249be-47e3-40b2-87fe-33dd82af546e.png)

The data displayed

After the log-in, the session is stored in user's android device and later log-ins do not require the password while the authorization token is valid.


https://user-images.githubusercontent.com/71312224/234663783-78592f34-3a8e-46b6-aee9-b727c0fdaef3.mp4

