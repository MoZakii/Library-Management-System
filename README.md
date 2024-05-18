1- Add your postgres database connection details to the application.properties file located in the Recources directory. <br>
2- Run the program. <br>
3- Import the postman collection (Library Management System.postman_collection.json) to your postman to try out the endpoints within. Or follow the following instructions for the end points. <br>

API Endpoints:

● Book management endpoints:<br>
● GET /api/books: Retrieve a list of all books.<br>
● GET /api/books/{id}: Retrieve details of a specific book by ID.<br>
● POST /api/books: Add a new book to the library.<br>
● PUT /api/books/{id}: Update an existing book's information.<br>
● DELETE /api/books/{id}: Remove a book from the library.<br>

● Patron management endpoints:<br>
● GET /api/patrons: Retrieve a list of all patrons.<br>
● GET /api/patrons/{id}: Retrieve details of a specific patron by ID.<br>
● POST /api/patrons: Add a new patron to the system.<br>
● PUT /api/patrons/{id}: Update an existing patron's information.<br>
● DELETE /api/patrons/{id}: Remove a patron from the system.<br>

● Borrowing endpoints:<br>
● POST /api/borrow/{bookId}/patron/{patronId}: Allow a patron to borrow a book.<br>
● PUT /api/return/{bookId}/patron/{patronId}: Record the return of a borrowed book by a patron.<br>
