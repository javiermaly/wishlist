## ⚡️About The Project
This is a web application that
exposes a REST-ful API over HTTP for creating book’s wishlists for different users.
Actually is running with a file database from H2 that can be easily changed to another.

The books are fetched using the Google Books API.

## 💾  Database Relations
![DB MER](dist/db_mer.png "DB MER")


## 🧬 Solution Middleware
As this project was created with Springboot, it is provided with an embedded Tomcat in a fat jar with all the dependencies.

We use Nginx as a reverse proxy with cache capabilities to reduce the hits to Google Books API regarding to save hits/money.

### 📚 Built With

* [SpringBoot](https://spring.io/)
* [MySql](https://www.mysql.com/)
* [H2](https://www.h2database.com/)
* [MapStruct](https://mapstruct.org/)
* [Lombok](https://projectlombok.org/)
* [Postman](https://www.postman.com/)
* [Intellij Idea](https://www.jetbrains.com/es-es/idea/)

### ⚙️Prerequisites
* Google Books API key generated and enabled is needed to use the 'Search' endpoint.
   ```url 
   https://developers.google.com/books/docs/v1/using#APIKey
   ```

### ⚙️Installation
1. Clone the repo
   ```sh
   git clone https://github.com/github_username/repo_name.git
   git checkout develop
   ```
2. Run Containers
   ```sh
   docker-compose up -d
   ```
   * Permissions on './mvnw' can be required, in that case, run: chmod 777 ./mvnw
   

3. Enjoy ;-)


### ⌚ 🔔 Metrics and Monitoring
* [Health](http://localhost:8081/wishlist/management/health)
   ```
   {
      "status": "UP",
      "components": {
         "db": {
         "status": "UP",
         "details": {
         "database": "MySQL",
         "validationQuery": "isValid()"
         }
      },
      "diskSpace": {
      "status": "UP",
      "details": {
         "total": 511123124224,
         "free": 313053065216,
         "threshold": 10485760,
         "exists": true
        }
      },
      "ping": {
         "status": "UP"
         }
      }
   }
   ```
* [Prometheus](http://localhost:8081/wishlist/management/prometheus)
* [Grafana](http://localhost:9090/)

####Is needed to import a dashboard pre-created in order to have some metrics already represented.
To do that we need to import this file into this url: ./grafana/springboot-apm-dashboard_rev3.json
```
    http://localhost:3000/dashboard/import
```
After that you will can access the dashboard on this url
``` 
    http://localhost:3000/d/X034JGT7Gz/springboot-apm-dashboard?orgId=1
```

![GRAFANA](dist/grafanaScreenExample.png "Grafana screen example")


## 👀 Examples
### Api-docs (Swagger)
* [Swagger-ui](http://localhost:8181/wishlist/swagger-ui/)
* [Api-docs](http://localhost:8181/wishlist/v2/api-docs)

#### There's a Postman project collection in the ./dist/wishlist.postman_collection.json file with examples that can be imported into Postman to test.


## 🔒 🔑 Auth
### ➡️ POST /wishlist/v1/auth/signup
The sign-up endpoint creates a new user with the credentials needed to sign in
to the application.

Request
   ```
       {
           "username": "robpike",
           "password": "passtheword"
       }
   ```
Response
   ```
       empty
   ```
Status Codes
* 201 Created - The user was created successfully. 
* 400 Bad Request - The request is invalid. 
* 409 Conflict - The username is already taken.
* 500 Internal Server Error - See logs.
   
   ```
      curl -X POST http://localhost:8181/wishlist/v1/auth/signup -H 'Content-Type: application/json' -d '{"username":"javiermaly","password":"password"}'
   ```
### ➡️POST /wishlist/v1/auth/signin
The sign-in endpoint authenticates an existent user using the provided credentials.

Request
   ```
       {
           "username": "robpike",
           "password": "passtheword"
       }
   ```
Response
   ```
       {
           "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZpZXJtYWx5IiwiaWF0IjoxNjUyNDk5OTAyLCJleHAiOjE2NTMyNzc1MDd9.PhXRLaczqARBn1vnRx_BfMkUpDHT9pdKVPTjEVoDfxQRtqxBRV8OGniiy3xW9d9OTGE7SkADR7r8J9Jhq7dBVA"
       }
   ```
Status Codes
* 200 ok - The user has signed in successfully.
* 400 Bad Request - The request is invalid.
* 500 Internal Server Error - See logs.

   ```
      curl -X POST http://localhost:8181/wishlist/v1/auth/signin -H 'Content-Type: application/json' -d '{"username":"javiermaly","password":"password"}'
   ```

## 📚 Books
### ➡️ GET /wishlist/v1/books
As an authenticated user, I can list all the books stored on the databaase,
requesting by page and size of page.

Params
   ```
      /wishlist/v1/books?size=10&page=2
   ```
Headers 🔑
   ```
      'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZpZXJtYWx5IiwiaWF0IjoxNjUyNDk5OTAyLCJleHAiOjE2NTMyNzc1MDd9.PhXRLaczqARBn1vnRx_BfMkUpDHT9pdKVPTjEVoDfxQRtqxBRV8OGniiy3xW9d9OTGE7SkADR7r8J9Jhq7dBVA'
   ```
Response
   ```
      [
          {
             "id": "14",
             "gbid": "1492fd10-34e3-4c69-81be-5a2cfc162a00",
             "authors": [
                 "Javier Maly"
             ],
             "title": "Software Developers Soft Skills",
             "publisher": "O'Reilly"
         }
      ]
   ```
Status Codes
* 200 ok - Returns the list of books.
* 401 Unauthorized - The auth token is invalid.
* 500 Internal Server Error - See logs.
   ```
      curl -X GET  http://localhost:8181/wishlist/v1/books -H 'Content-Type: application/json' -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZpZXJtYWx5IiwiaWF0IjoxNjUyNTUyOTI5LCJleHAiOjE2NTMzMzA1MzR9.rOFisAoa3P-NbKIDAtfqSjX7hL2xWvy2m1jb--Hy_Jx8rtLYJWIUF8wpzgJBIC6O2A4sx_WwKFxnjkchg_MhZw'
   ```


### ➡️ GET /wishlist/v1/books/volumes
As an authenticated user, I want to search for books using the Google API https://developers.google.com/books.

Params
   ```
      /wishlist/v1/books/volumes?key={yourGoogleApiKey}&intitle={}&inauthor={}robert&inpublisher={}
   ```
Headers 🔑
   ```
      'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZpZXJtYWx5IiwiaWF0IjoxNjUyNDk5OTAyLCJleHAiOjE2NTMyNzc1MDd9.PhXRLaczqARBn1vnRx_BfMkUpDHT9pdKVPTjEVoDfxQRtqxBRV8OGniiy3xW9d9OTGE7SkADR7r8J9Jhq7dBVA'
   ```
Response
   ```
        [
            {
                "id": "39",
                "gbid": "cI_BugEACAAJ",
                "authors": [
                    "Vaskaran Sarcar"
                ],
                "title": "Java Design Patterns",
                "publisher": "Apress"
            },
           ...
            {
                "id": "47",
                "gbid": "qAVt1B1s47UC",
                "authors": [
                    "Mark Grand"
                ],
                "title": "Java Enterprise Design Patterns",
                "publisher": "John Wiley & Sons"
            }
        ]
   ```

```
    GoogleBooks API may not include publisher for some books.
```
Status Codes
* 200 ok - Returns the list of books.
* 400 Bad request - No search/invalid criteria or key missing.
* 401 Unauthorized - The auth token is invalid.
* 500 Internal Server Error - See logs.

   ```
      curl -X GET  http://localhost:8181/wishlist/v1/books/volumes?key=AIzaSyDYk3-lNMQynqmE_sz5-Ebn2T5EYa_s4n8&intitle=java&inauthor=&inpublisher= -H 'Content-Type: application/json' -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZpZXJtYWx5IiwiaWF0IjoxNjUyNTUyOTI5LCJleHAiOjE2NTMzMzA1MzR9.rOFisAoa3P-NbKIDAtfqSjX7hL2xWvy2m1jb--Hy_Jx8rtLYJWIUF8wpzgJBIC6O2A4sx_WwKFxnjkchg_MhZw'
   ```


## 📋 Wishlists
### ➡️ POST /wishlist/v1/whishlists
As an authenticated user, I want to create a wishlist of books.

Headers 🔑
   ```
      'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZpZXJtYWx5IiwiaWF0IjoxNjUyNDk5OTAyLCJleHAiOjE2NTMyNzc1MDd9.PhXRLaczqARBn1vnRx_BfMkUpDHT9pdKVPTjEVoDfxQRtqxBRV8OGniiy3xW9d9OTGE7SkADR7r8J9Jhq7dBVA'
   ```
Request
   ```
         {
             "name": "My Birthday Wishlist"
         }
   ```

Response
   ```
     Empty
   ```
Status Codes
* 201 Created - The wishlist was created.
* 401 Unauthorized - The auth token is invalid.
* 500 Internal Server Error - See logs.

   ```
      curl -X POST http://localhost:8181/wishlist/v1/whishlists -H 'Content-Type: application/json' -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZpZXJtYWx5IiwiaWF0IjoxNjUyNTUyOTI5LCJleHAiOjE2NTMzMzA1MzR9.rOFisAoa3P-NbKIDAtfqSjX7hL2xWvy2m1jb--Hy_Jx8rtLYJWIUF8wpzgJBIC6O2A4sx_WwKFxnjkchg_MhZw' -d '{"name": "My Birthday Wishlist"}'
   ```

### ➡️ POST /wishlist/v1/whishlists/{wishlistId}/books
As an authenticated user, I want to add a book from a wishlist of mine.

wishlistId matches the wishlist id from our system.


Headers 🔑
   ```
      'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZpZXJtYWx5IiwiaWF0IjoxNjUyNDk5OTAyLCJleHAiOjE2NTMyNzc1MDd9.PhXRLaczqARBn1vnRx_BfMkUpDHT9pdKVPTjEVoDfxQRtqxBRV8OGniiy3xW9d9OTGE7SkADR7r8J9Jhq7dBVA'
   ```
Request: Is an array of Longs matching the bookId from our system.
   ```
         [
            2,
            3,
            4
         ]
   ```

Response
   ```
      Empty
   ```
Status Codes
* 200 OK - The wishlist was created.
* 404 Not Found - Wishlist not founded.
* 400 Bad Request - Provided bookId invalid. 
* 401 Unauthorized - The auth token is invalid.
* 500 Internal Server Error - See logs.

   ```
   curl -X POST http://localhost:8181/wishlist/v1/whishlists/7/books -H 'Content-Type: application/json' -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZpZXJtYWx5IiwiaWF0IjoxNjUyNTUyOTI5LCJleHAiOjE2NTMzMzA1MzR9.rOFisAoa3P-NbKIDAtfqSjX7hL2xWvy2m1jb--Hy_Jx8rtLYJWIUF8wpzgJBIC6O2A4sx_WwKFxnjkchg_MhZw' -d '[2]'
   ```
### ➡️ DELETE /wishlist/v1/whishlists/{wishlistId}/books
As an authenticated user, I want to delete a book from a wishlist of mine.

wishlistId matches the wishlist id from our system.

Headers 🔑
   ```
      'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZpZXJtYWx5IiwiaWF0IjoxNjUyNDk5OTAyLCJleHAiOjE2NTMyNzc1MDd9.PhXRLaczqARBn1vnRx_BfMkUpDHT9pdKVPTjEVoDfxQRtqxBRV8OGniiy3xW9d9OTGE7SkADR7r8J9Jhq7dBVA'
   ```
Request: Is an array of Longs matching the bookId from our system.
   ```
      [
         2,
         3,
         4
      ]
   ```

Response
   ```
      Empty
   ```
Status Codes
* 200 OK - The wishlist was created.
* 404 Not Found - Wishlist not founded.
* 400 Bad Request - Provided bookId invalid.
* 400 Bad Request - Provided bookId not in the wishlist.
* 401 Unauthorized - The auth token is invalid.
* 500 Internal Server Error - See logs.

   ```
      curl -X DELETE http://localhost:8181/wishlist/v1/whishlists/7/books -H 'Content-Type: application/json' -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZpZXJtYWx5IiwiaWF0IjoxNjUyNTUyOTI5LCJleHAiOjE2NTMzMzA1MzR9.rOFisAoa3P-NbKIDAtfqSjX7hL2xWvy2m1jb--Hy_Jx8rtLYJWIUF8wpzgJBIC6O2A4sx_WwKFxnjkchg_MhZw' -d '[6]'
   ```

### ➡️ GET /wishlist/v1/whishlists
As an authenticated user, I want to list all the wishlists created by me.

Headers 🔑
   ```
      'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZpZXJtYWx5IiwiaWF0IjoxNjUyNDk5OTAyLCJleHAiOjE2NTMyNzc1MDd9.PhXRLaczqARBn1vnRx_BfMkUpDHT9pdKVPTjEVoDfxQRtqxBRV8OGniiy3xW9d9OTGE7SkADR7r8J9Jhq7dBVA'
   ```
Request
   ```
      Empty
   ```

Response
   ```
   [
       {
           "id": 12,
           "name": "My Birthday Wishlist",
           "books": [
               {
                   "id": "14",
                   "gbid": "1492fd10-34e3-4c69-81be-5a2cfc162a00",
                   "authors": [
                       "Javier Maly"
                   ],
                   "title": "Software Developers Soft Skills",
                   "publisher": "O'Reilly"
               }
           ]
       },
       {
           "id": 15,
           "name": "Ana's Birthday's Wishlist",
           "books": []
       }
   ]
   ```
Status Codes
* 200 OK - The wishlist was created.
* 401 Unauthorized - The auth token is invalid.
* 500 Internal Server Error - See logs.
   
   ```
      curl -X GET http://localhost:8181/wishlist/v1/whishlists -H 'Content-Type: application/json' -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZpZXJtYWx5IiwiaWF0IjoxNjUyNTUyOTI5LCJleHAiOjE2NTMzMzA1MzR9.rOFisAoa3P-NbKIDAtfqSjX7hL2xWvy2m1jb--Hy_Jx8rtLYJWIUF8wpzgJBIC6O2A4sx_WwKFxnjkchg_MhZw'
   ```

### ➡️ GET /wishlist/v1/whishlists/{wishlistId}
As an authenticated user, I want to list the books of a wishlist of mine.

wishlistId matches the wishlist id from our system.

Headers 🔑
   ```
      'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZpZXJtYWx5IiwiaWF0IjoxNjUyNDk5OTAyLCJleHAiOjE2NTMyNzc1MDd9.PhXRLaczqARBn1vnRx_BfMkUpDHT9pdKVPTjEVoDfxQRtqxBRV8OGniiy3xW9d9OTGE7SkADR7r8J9Jhq7dBVA'
   ```
Request
   ```
      Empty
   ```

Response
   ```
   [
      {
          "id": "14",
          "gbid": "1492fd10-34e3-4c69-81be-5a2cfc162a00",
          "authors": [
              "Javier Maly"
          ],
          "title": "Software Developers Soft Skills",
          "publisher": "O'Reilly"
      }
   ]
   ```
Status Codes
* 200 OK - Books of the wishlist returned.
* 400 Bad Request - Wishlist does not correspond to the user.
* 404 Not Found - Wishlist not found.
* 401 Unauthorized - The auth token is invalid.
* 500 Internal Server Error - See logs.
   
   ```
      curl -X GET http://localhost:8181/wishlist/v1/whishlists/2 -H 'Content-Type: application/json' -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZpZXJtYWx5IiwiaWF0IjoxNjUyNTUyOTI5LCJleHAiOjE2NTMzMzA1MzR9.rOFisAoa3P-NbKIDAtfqSjX7hL2xWvy2m1jb--Hy_Jx8rtLYJWIUF8wpzgJBIC6O2A4sx_WwKFxnjkchg_MhZw'
   ```

### ➡️ DELETE /wishlist/v1/whishlists/{wishlistId}
As an authenticated user, I want to delete a wishlist created by me.

wishlistId matches the wishlist id from our system.

Headers 🔑
   ```
      'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZpZXJtYWx5IiwiaWF0IjoxNjUyNDk5OTAyLCJleHAiOjE2NTMyNzc1MDd9.PhXRLaczqARBn1vnRx_BfMkUpDHT9pdKVPTjEVoDfxQRtqxBRV8OGniiy3xW9d9OTGE7SkADR7r8J9Jhq7dBVA'
   ```
Request
   ```
      Empty
   ```

Response
   ```
      Empty
   ```
Status Codes
* 200 OK - Wishlist deleted.
* 400 Bad Request - Wishlist does not correspond to the user.
* 404 Not Found - Wishlist not found.
* 401 Unauthorized - The auth token is invalid.
* 500 Internal Server Error - See logs.
   
   ```
   curl -X DELETE http://localhost:8181/wishlist/v1/whishlists/2 -H 'Content-Type: application/json' -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZpZXJtYWx5IiwiaWF0IjoxNjUyNTUyOTI5LCJleHAiOjE2NTMzMzA1MzR9.rOFisAoa3P-NbKIDAtfqSjX7hL2xWvy2m1jb--Hy_Jx8rtLYJWIUF8wpzgJBIC6O2A4sx_WwKFxnjkchg_MhZw'
   ```