# Four Leaves Shoe - Project Overview
- URL: https://shoeselling-fourleavesshoes.onrender.com
- This project aims to replicate the functionality of an e-commerce website. It offers a comprehensive suite of features that cater to both customers and store managers.

# Technologies Used

- Front-end: Thymeleaf (using template bootstrap)
- Backend: Spring Boot
- Database: SQL
- Store log: ELK stack (Elasticsearch, Logstash, Kibana)
- Run project: Docker
- Store image: MinIO
- Redis 

## Features

### For Customers
- **Store Information**: Provides details about the store.
- **Order Placement**: Allows customers to place orders for their desired products.
- **Payment Gateway**: Facilitates payment transactions for purchases.

### For Store Management
- **User Information Management**: Handles the data related to store users.
- **Product Management**: Involves adding, updating, and removing products.
- **Order Management**: Tracks and manages customer orders.
- **User Role Assignments**: Administers the roles and permissions of users on the website.

## Admin Account

**Username**: doananh0100  
**Password**: Duc2112002@

## Main Features

### Design and User Interface
- Built according to the MVC model and designing UI/UX for the application.
- ![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/a12210f8-4a25-42f4-9170-506571f3f747)

### E-commerce Functionalities
- Provided functionalities of an e-commerce website, including viewing items, managing the shopping cart, order placing for customers, and shop management for employees.

### Security
- Utilized Spring Security and JWT tokens for managing authentication and authorization.

### Payment Integration
- Payment is handled through Momo e-wallet and Vnpay payment gateway.

### Integration with Other Services
- Working with Gmail, Excel, and PDF applications to manage orders for customers and data management for the store.
- Wrote APIs for Android app integration.

### Social Login and Communication
- Used OAuth2 for logging in with Google, Facebook (under development).
- Used Twilio to send SMS messages through a phone number for verification support (under development).

### ELK Stack
- Used Elasticsearch, Kibana, and Logstash to store logs from a Spring Boot application.

### Redis, MinIO
- Used Redis as a cache and MinIO for image storage.

### Unit test
- Automatically performed unit tests using JUnit, Mockito, and GitHub Actions.

### Deployment
- Deployed all services using Docker Compose and Ngrok. (Full demo)
- Deployed SpringBoot and Thymeleaf with Render. (Incomplete demo)

# ===================================================================================================================

## 1. Provided functionalities of an e-commerce website, including viewing items, managing the shopping cart, order placing for customers, and shop management for employees.

### 1.1. Account registration and login
- Registration
  ![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/44cac01e-4a48-4e31-a2e4-35bb4dce51b7)

- Register successfully
  ![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/81e3651e-b642-4ca3-b671-d324e229c314)

- Login
  ![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/76a69b20-ea21-4a29-a88f-ac4caeece27f)

- Login successfully
  ![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/28d5c7bc-cc4f-4492-8dcc-67cb8b27144b)


### 1.2. View homepage, products, product details
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/649e918b-01c5-4766-95c3-945f24b1aacf)
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/78a28ae4-6295-409b-a970-4606099c1d07)
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/51e3d658-0ab0-45a9-b908-e2853010a049)

### 1.3. View, add, remove products from the shopping cart
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/bfe2bd94-dda7-441e-91ef-065a8ef23873)

### 1.4. Place Cash on Delivery (COD) orders
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/1da2c500-4693-40ba-a68c-5f3c932f48d5)
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/87d43512-0fb6-4de5-8ad8-e13dafcd3ac1)

## 2. Payment is handled through Momo e-wallet and Vnpay payment gateway.
If you run this project in local, you have to map your url from http to https, in order to receive response returned from the IPN of Momo and Vnpay.
![img.png](image/img.png)

If you run this project locally, you need to map your URL from HTTP to HTTPS to receive responses from the IPN of Momo and Vnpay.
Here is MomoService.java
![img_1.png](image/img_1.png)

Here is VnpayService.java
![img_2.png](image/img_2.png)
### 2.1. Payment through Momo e-wallet
![chonPhuongThucThanhToan](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/33abae17-62af-4195-acee-5e37726ed427)
![trangThanhToanMomo](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/4ddf59cb-08c2-44fe-943a-f94108b8750d)

- At the homepage of Momo's application

<img src="https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/17d5da92-6362-4b37-b3f7-4a8758f03780" height="500">

<img src="https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/ca0e5fd5-1f6c-49ec-81e2-879097cb90d3" height="500">

<img src="https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/69c3c9bc-a7e4-4899-977f-4f9cbd74152a" height="500">
  

- After check out successfully, the webpage is reset.
  ![thanhToanMomoThanhCong](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/19f16a25-1eb1-41ef-b327-348b9ff7f5dc)
  ![trangThaiOrder](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/b7746bf4-a866-4fef-ada7-6652f76d69d1)

### 2.2. Payment through Vnpay payment gateway
![chonPhuongThucThanhToan](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/6ac7a1e3-75c6-49be-b608-3c26518a95e4)
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/f0b1959e-a6c5-4051-9d36-0c2fa3003ebf)
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/f153790b-b772-44de-ac79-2a5f26232f2d)
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/8c740ddd-1ae5-4d29-bdbd-5adcbb71713c)
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/67947bcb-cccb-4413-8e8e-3af923a3521d)

## 3. Working with Gmail, Excel, and PDF applications to manage orders for customers and data management for the store.

### 3.1. Working with Gmail
- Change password
  ![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/dce95b65-8b18-4d8c-b594-225c3522287c)
  ![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/260b303f-5c80-48e8-b90f-d724d076f896)

- Send invoices after purchasing
  ![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/74141053-0ceb-459f-8ea7-8e6e59ff4b7e)

### 3.2. Working with Excel (CSV)

- Export, Import CSV files in store management for employees

![img_3.png](image/img_3.png)

![img_4.png](image/img_4.png)

![img_6.png](image/img_6.png)

![img_7.png](image/img_7.png)

![img_8.png](image/img_8.png)



### 3.3. Working with PDF

- Print invoices in PDF format

![img_9.png](image/img_9.png)

![img_10.png](image/img_10.png)

![img_11.png](image/img_11.png)

## 4. Wrote APIs for Android app integration.
- Example of getting product's list api
  ![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/c6be7ed9-6874-4ce8-9146-4f0a503b3c36)

- Example of getting staff's list api
  ![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/34a04b7c-32f4-458b-bd47-5821c54cdd65)


## 5. Utilized Spring Security and JWT tokens for managing authentication and authorization.

### 5.1. Security FilterChain, Filters, Exceptions

- Default Security FilterChain
- [Refer to] - Linh Vu. "3. Spring Security: Security Filter - Register a Custom SecurityFilter into a SecurityFilterChain", 24/03/2024. [Online]. Link: https://www.youtube.com/watch?v=fxROf5ygVSg&t=4s . [Access: 05/09/2024  ]

<img src="image/img_12.png" height="700">

- Here is my filter chain.

![img_13.png](image/img_13.png)

![data angular thread-security spring.png](image/data%20angular%20thread-security%20spring.png)



### 5.2. JWT tokens and permissions
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/6f8c3ec7-f06c-45e6-9a11-48639e2da7d8)
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/ecdb5f49-59fa-489a-94bf-a311627eba71)
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/e2549f2f-50c5-4578-8395-58ecddd0dd1c)

### 5.3. Incorrect login
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/6c3bef7b-83a1-4464-8237-d6b4bf347c50)


## 6. Used OAuth2 for logging in with Google, Facebook (under development).

## 7. Used Twilio to send SMS messages through a phone number for verification support (under development).

## 9. Used Elasticsearch, Kibana, and Logstash to store logs from a Spring Boot application

This project demonstrates the use of the ELK stack (Elasticsearch, Logstash, and Kibana) to store and visualize logs generated by a Spring Boot application.

### Logstash Configuration File

Below is the `logstash-sample.conf` configuration file used when turning on the ELK stack:

```ruby
input {
  file {
    path => "D:/springboot/workspace/FourLeavesShoesDocker/FourLeavesShoeDocker/elk-stack.log"
    start_position => "beginning"
    codec => plain {  # Using plain to read raw log lines first
      charset => "UTF-8"
    }
  }
}

filter {
  mutate {
    # Remove \r and \n characters from the message field
    gsub => [
      "message", "\r", "",  # Remove \r
      "message", "\n", ""   # Remove \n
    ]
  }
}

output {
  stdout {
    codec => rubydebug
  }
  elasticsearch {
    hosts => ["http://localhost:9200"]
    index => "application"
  }
}
```

- In the file ` elasticsearch.yml ` located in the ` \elasticsearch-8.15.0\config\ ` folder, if you prefer not to see extensive logs related to informative messages, authentication and authorization, enrollment, and setup, you should disable the security features of Elasticsearch.
``` ruby
# Enable security features
xpack.security.enabled: false

xpack.security.enrollment.enabled: false
```

![image](https://github.com/user-attachments/assets/43899822-b701-47c5-a7e3-43c6a5a85897)

Next, start these three tools using the following commands:
- Start Elasticsearch: Run ` elasticsearch.bat ` in the ` elasticsearch-8.15.0\bin ` folder.
- Start Kibana: Run ` kibana.bat ` in the ` kibana-8.15.0\bin ` folder.
- Start Logstash: Run ` .\bin\logstash.bat -f .\config\logstash-sample.conf ` in the ` logstash-8.15.0 ` folder.

Here is the result:
- Elasticsearch:
![image](https://github.com/user-attachments/assets/096d0cdb-bd8c-497a-a987-9d970241f976)

- Kibana:
![image](https://github.com/user-attachments/assets/d00a4eb1-264d-44f0-8d03-5525f2b892be)

- Logstash:
![image](https://github.com/user-attachments/assets/6b1441b1-914b-44f1-a657-35e0286d4586)

In the configuration file within the Logstash folder, I have specified a new index named application. Here are the results:
![image](https://github.com/user-attachments/assets/5ea9094a-9091-4c3e-9474-d661bf5c6364)

In the SpringBoot application, i have register a log file to store log in the application.yml:
![image](https://github.com/user-attachments/assets/f5d755b3-0ca6-4185-8835-0219a329396b)

And this path in application.yml file matches the path in the `logstash-sample.conf` configuration file.
This means that all logs created within the try-catch block are written to the file elk-stack.log. Logstash then reads all the logs from this file and stores them in Elasticsearch. Kibana will visualize and analyze the data stored in Elasticsearch, which can be accessed by developers at ` http://localhost:5601/ `.

Elasticsearch is using 7.9 GB of disk space on my SSD for storing data.
![image](https://github.com/user-attachments/assets/e969aa8d-009e-4347-a2cf-797e28ac9d30)

At http://localhost:5601/, go to the Navigation Bar, select 'Analytics', and then choose 'Discover' to view and manage all the logs sent from the Spring Boot application.

![image](https://github.com/user-attachments/assets/7b53e028-c4a2-41df-87cd-f16ab83efce7)
![image](https://github.com/user-attachments/assets/fb7ae8cc-c98f-4e67-aec9-96b08c8dfae6)




