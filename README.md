# Four Leaves Shoe - Project Overview
- URL: https://shoeselling-fourleavesshoes.onrender.com
- The old repository: https://github.com/gavin0100/sellingshoe
- This project aims to replicate the functionality of an e-commerce website. It offers a comprehensive suite of features that cater to both customers and store managers.

# Technologies Used

- Front-end: Thymeleaf (using template bootstrap)
- Backend: Spring Boot
- Database: SQL
- Store log: ELK stack (Elasticsearch, Logstash, Kibana)
- Run project: Docker
- Store image: MinIO
- Redis 

## Admin Account

**Username**: doananh0100  
**Password**: Duc2112002@

## Main Features

### ER Diagram

![database.png](image%2Fdatabase.png)

### Design and User Interface
- Built according to the MVC model and designing UI/UX for the application.

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

## 1. Utilized Spring Security and JWT tokens for managing authentication and authorization.

### 1.1. Security FilterChain, Filters, Exceptions

- Default Security FilterChain
- [Refer to] - Linh Vu. "3. Spring Security: Security Filter - Register a Custom SecurityFilter into a SecurityFilterChain", 24/03/2024. [Online]. Link: https://www.youtube.com/watch?v=fxROf5ygVSg&t=4s . [Access: 05/09/2024  ]

<img src="image/img_12.png" height="700">

- Here is my filter chain.

![img_13.png](image/img_13.png)

![data angular thread-security spring.png](image/data%20angular%20thread-security%20spring.png)



### 1.2. JWT tokens and permissions

![jwt thread.png](image%2Fjwt%20thread.png)

![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/6f8c3ec7-f06c-45e6-9a11-48639e2da7d8)
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/ecdb5f49-59fa-489a-94bf-a311627eba71)
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/e2549f2f-50c5-4578-8395-58ecddd0dd1c)

### 1.3. Incorrect login
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/6c3bef7b-83a1-4464-8237-d6b4bf347c50)


## 2. Provided functionalities of an e-commerce website, including viewing items, managing the shopping cart, order placing for customers, and shop management for employees.

### 2.1. Account registration and login
- Registration
  ![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/44cac01e-4a48-4e31-a2e4-35bb4dce51b7)

- Register successfully
  ![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/81e3651e-b642-4ca3-b671-d324e229c314)

- Login
  ![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/76a69b20-ea21-4a29-a88f-ac4caeece27f)

- Login successfully
  ![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/28d5c7bc-cc4f-4492-8dcc-67cb8b27144b)


### 2.2. View homepage, products, product details
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/649e918b-01c5-4766-95c3-945f24b1aacf)
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/78a28ae4-6295-409b-a970-4606099c1d07)
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/51e3d658-0ab0-45a9-b908-e2853010a049)

### 2.3. View, add, remove products from the shopping cart
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/bfe2bd94-dda7-441e-91ef-065a8ef23873)

### 2.4. Place Cash on Delivery (COD) orders
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/1da2c500-4693-40ba-a68c-5f3c932f48d5)
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/87d43512-0fb6-4de5-8ad8-e13dafcd3ac1)

## 3. Payment is handled through Momo e-wallet and Vnpay payment gateway.
If you run this project in local, you have to map your url from https to http, in order to receive response returned from the IPN of Momo and Vnpay.
![img.png](image/img.png)

If you run this project locally, you need to map your URL from HTTPS to HTTP to receive responses from the IPN of Momo and Vnpay.
Here is MomoService.java
![img_1.png](image/img_1.png)

Here is VnpayService.java
![img_2.png](image/img_2.png)

Beside Serveo, you can use Ngrok familiar to proxy.

Command line: ngrok http http://localhost:8080

![img_ngrok.png](image%2Fimg_ngrok.png)


### 3.1. Payment through Momo e-wallet

![data angular thread-momo.png](image%2Fdata%20angular%20thread-momo.png)

![chonPhuongThucThanhToan](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/33abae17-62af-4195-acee-5e37726ed427)
![trangThanhToanMomo](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/4ddf59cb-08c2-44fe-943a-f94108b8750d)

- At the homepage of Momo's application

<img src="https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/17d5da92-6362-4b37-b3f7-4a8758f03780" height="500">

<img src="https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/ca0e5fd5-1f6c-49ec-81e2-879097cb90d3" height="500">

<img src="https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/69c3c9bc-a7e4-4899-977f-4f9cbd74152a" height="500">
  

- After check out successfully, the webpage is reset.
  ![thanhToanMomoThanhCong](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/19f16a25-1eb1-41ef-b327-348b9ff7f5dc)
  ![trangThaiOrder](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/b7746bf4-a866-4fef-ada7-6652f76d69d1)

### 3.2. Payment through Vnpay payment gateway

![vnpay.png](image%2Fvnpay.png)

![chonPhuongThucThanhToan](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/6ac7a1e3-75c6-49be-b608-3c26518a95e4)
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/f0b1959e-a6c5-4051-9d36-0c2fa3003ebf)
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/f153790b-b772-44de-ac79-2a5f26232f2d)
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/8c740ddd-1ae5-4d29-bdbd-5adcbb71713c)
![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/67947bcb-cccb-4413-8e8e-3af923a3521d)

## 4. Working with Gmail, Excel, and PDF applications to manage orders for customers and data management for the store.

### 4.1. Working with Gmail
- [Refer to] - GP Coder. "Hướng dẫn sử dụng thư viện Java Mail", 02/04/2018. [Online]. Link: https://gpcoder.com/3753-huong-dan-su-dung-thu-vien-java-mail/ . [Access: 05/09/2024  ]

- ![img.png](image/19_9_2024/img.png)

- Sending message to SMTP server via TCP/IP, encode messages by TLS port.
- Using JavaMail API by injecting the `javax.mail` dependency.
- Port: 587 (TLS port); 465 (SSL port); 25 (unencrypted port). TLS is more secure than SSL.
- Enabling authentication with Google Application's password.
- Using MimeMessage to store all information and sending it via Transport.

```agsl
<dependency>
    <groupId>com.sun.mail</groupId>
    <artifactId>javax.mail</artifactId>
    <version>1.6.2</version>
</dependency>

Session session = Session.getInstance(properties, new Authenticator() {
     @Override
    protected PasswordAuthentication getPasswordAuthentication(){
        return new  PasswordAuthentication("voduc0100@gmail.com", "arozojkhspxuuxeg");
    }
});

MimeMessage message = new MimeMessage(session);
message.setFrom; message.addRecipient; message.setSubject; message.setContent
Transport.send(message);
```


- Change password
  ![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/dce95b65-8b18-4d8c-b594-225c3522287c)
  ![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/260b303f-5c80-48e8-b90f-d724d076f896)

- Send invoices after purchasing
  ![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/74141053-0ceb-459f-8ea7-8e6e59ff4b7e)

### 4.2. Working with Excel (CSV)

- Import CSV files in page's management for category
  - To handle file uploads in a Spring Boot application, using a form with the method="post" and enctype="multipart/form-data" attributes:
    ```agsl
    <form method="post" enctype="multipart/form-data">
          <input type="file" name="file">
    </form>
    ```
  - In Spring Boot controller, using the @RequestParam annotation to handle the uploaded file `@RequestParam("file") MultipartFile file`
  - Using POI library to read/write file CSV
    ```agsl
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-ooxml</artifactId>
        <version>5.2.5</version>
    </dependency>
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi</artifactId>
        <version>5.2.5</version>
    </dependency> 
    ```
  - Using java.io.InputStream to read file via byte
  - Using IOException to catch read/write error.
  - File xlsx -> Object: XSSFWorkbook
  - Sheet -> Object: XSSFSheet;
  - Reading file is perform via poi.ss.usermodel.Row and poi.ss.usermodel.Cell

![img_3.png](image/img_3.png)

![img_4.png](image/img_4.png)

![img_6.png](image%2Fimg_6.png)

![img_7.png](image/img_7.png)

![img_8.png](image/img_8.png)

- Export CSV files in page's management for employees
  - Setting HttpServletResponse.Header:
    - setContentType("text/csv; charset=UTF-8")
    - setCharacterEncoding("UTF-8")
    - headerKey = "Content-Disposition"
    - headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
  - Using Writer = OutputStreamWriter {HttpServletResponse.getOutputStream() --> byte[], "UTF-8"}: convert HttpServletResponse to byte[] in order to add ICsvBeanWriter's data
  - ICsvBeanWriter write CsvHeader, write content List<User>
  - Writer add ICsvBeanWriter via Writer.flush() method.
  - Automatically send HttpServletResponse.


![exportCSV1.png](image%2FexportCSV1.png)

![exportCSV2.png](image%2FexportCSV2.png)

![exportCSV3.png](image%2FexportCSV3.png)




### 4.3. Working with PDF

- Print invoices in PDF format
  - Using iText 7 Core library in order to write data for PDF file
    ```agsl
    <dependency>
        <groupId>com.itextpdf</groupId>
        <artifactId>itext7-core</artifactId>
        <version>7.1.16</version>
        <type>pom</type>
    </dependency>
    ```
  - Using Document (created from PdfDocument (created from PdfWriter))
  - Use FontProgram, Paragraph, and Table to write content to the PDF file. Add content to the document using the Document.add() method.
  - Convert the PdfWriter output to a ByteArrayInputStream and pass it into the body of a ResponseEntity.
  - Setting
    - headder: "Content-Disposition", "attachment;filename=invoice.pdf"
    - Send ResponseEntity
      - headers
      - contentType(MediaType.APPLICATION_PDF)
      - body(new InputStreamResource(bis)



![img_9.png](image/img_9.png)

![img_10.png](image/img_10.png)

![img_11.png](image/img_11.png)

## 5. Wrote APIs for Android app integration.
- Example of getting product's list api
  ![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/c6be7ed9-6874-4ce8-9146-4f0a503b3c36)

- Example of getting staff's list api
  ![image](https://github.com/VoVanDuc20110635/DoAnXinViec_fourleavesshoes/assets/116067030/34a04b7c-32f4-458b-bd47-5821c54cdd65)


## 6. Used OAuth2 for logging in with Google, Facebook (under development).

## 7. Used Twilio to send SMS messages through a phone number for verification support (under development).

## 8. Used Elasticsearch, Kibana, and Logstash to store logs from a Spring Boot application

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

## 9. Redis, MinIO

### Redis: 5.0.14.1

- Used with the detail of products and lists of products at the home page.
- When administrators update a specified product, the backend updates the database before deleting keys in the redis cache.
- Using Jedis library to integrate Redis and using spring-boot-starter-data-redis to simplifies the configuration and usage of Redis in Spring applications.
  ```agsl
  <dependency>
      <groupId>redis.clients</groupId>
      <artifactId>jedis</artifactId>
  </dependency>
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-redis</artifactId>
  </dependency>
  
  RedisTemplate<String, Object>
  .opsForValue().set(key, value)              => void
  .expire(key, timeoutInDays, TimeUnit.DAYS)  => void
  .opsForValue().get(key)                     => Object
  .delete(key)				                  => void
  .hasKey(key)                                => boolean
  .opsForList().range(key, 0, -1)             => List<Object>

  HashOperations<String, String, Object>
  .put(key, field, value)			          => void
  .hasKey(key, field)                         => boolean
  .entries(key)                               => Map<String, Object>
  .get(key, field)                            => Object     
  .entries(key).keySet()                      => Set<String> ~ list of fields
  .delete(key, field)                         => void
  .hasKey(key)                                => boolean
  ```

![redisCLI.png](image/19_9_2024/redisCLI.png)

### Minio: RELEASE.2024-08-26T15-33-07Z
- Used to store images of products.
- Run this command line in PowerShell: .\minio.exe server D:\MinIO --console-address :9001


![minioBucket.png](image/19_9_2024/minioBucket.png)

![img_2.png](image/19_9_2024/img_2.png)

- Let's create a product

![img_3.png](image/19_9_2024/img_3.png)

- Here's the way that product's images is added in MinIO
  ```agsl
  <form method="post" enctype="multipart/form-data">
  
  @PostMapping("/create")
  public String create(@ModelAttribute("product") Product product,BindingResult bindingResult,
                           @RequestParam("avatarFile") MultipartFile avatarFile)
  
  MultipartFile --> InputStream 
  
  MinioClient.putObject(PutObjectArgs.builder()
                      .bucket(bucketName)
                      .object(avatarFile.getOriginalFilename())
                      .stream(inputStream, inputStream.available(), -1)
                      .build());
  String avatarLink = urlHostImage + bucketName+ "/" + avatarFile.getOriginalFilename();
  product.setImage(avatarLink);
  ```

![img_4.png](image/19_9_2024/img_4.png)

![img_5.png](image/19_9_2024/img_5.png)

- In this case, the Redis cache doesn't contain this product with product's id equal to 125. 

![img_6.png](image/19_9_2024/img_6.png)

- Let's access the page of the product's details to save it to Redis cache.

![img_7.png](image/19_9_2024/img_7.png)

![img_8.png](image/19_9_2024/img_8.png)

- Let's update this product and this key is deleted in Redis cache

![img_9.png](image/19_9_2024/img_9.png)

![img_10.png](image/19_9_2024/img_10.png)

- When any users access to the page of this product's detail, it is saved again in Redis cache.

![img_12.png](image/19_9_2024/img_12.png)

![img_11.png](image/19_9_2024/img_11.png)

- When administrators update images, the old image in MinIO's bucket will be deleted and the new one will be added to this storage.
  - Let's change the product's image: carousel-3.png to carousel-5.jpg
    - Update the product's image
      - 1. If the product’s image name contains “fourleavesshoedocker”, retrieve the image name and delete it from MinIO storage using minioClient.removeObject(io.minio.RemoveObjectArgs).
      - 2. Add the new product image, following the same steps as in the “create product” section.

![img_13.png](image/19_9_2024/img_13.png)

![img_14.png](image/19_9_2024/img_14.png)

![img_15.png](image/19_9_2024/img_15.png)

![img_16.png](image/19_9_2024/img_16.png)


## 10. Unit test
Automatically performed unit tests using JUnit, Mockito, and GitHub Actions.

I used Junit, Mockito to perform UnitTest in CartService.

![img_17.png](image/19_9_2024/img_17.png)

In GithubAction, I configured to automatically perform these unit tests.

![img_18.png](image/19_9_2024/img_18.png)


## 11. Deployment
### Deployed all services using Docker Compose and Ngrok (Full demo)
- Run these command lines: 
  - docker-compose up                            --> run these services: spring boot, mysql, redis, minio
  - docker-compose -f elk-docker-compose.yml up  --> run these services: logstash, elasticsearch, kibana 

![img_19.png](image/19_9_2024/img_19.png)

![img_ngrok.png](image%2Fimg_ngrok.png)

- Let's change the value of environment's variable in docker-compose.yml: SERVEO_LINK

![img_23.png](image/19_9_2024/img_23.png)

- This is the default page of Ngrok, let's press "Visit Site"

![img_24.png](image/19_9_2024/img_24.png)

![img_25.png](image/19_9_2024/img_25.png)

### Deployed SpringBoot and Thymeleaf with Render. (Incomplete demo)

![img_20.png](image/19_9_2024/img_20.png)

https://shoeselling-fourleavesshoes.onrender.com

![img_21.png](image/19_9_2024/img_21.png)

![img_26.png](image/19_9_2024/img_26.png)










