1- Add your postgres database connection details to the application.properties file located in the Recources directory.
2- Run the program.
3- Import the postman collection (Library Management System.postman_collection.json) to your postman to try out the endpoints within. Or follow the following instructions for the end points. 

API Endpoints:

● Book management endpoints:
● GET /api/books: Retrieve a list of all books.
● GET /api/books/{id}: Retrieve details of a specific book by ID.
● POST /api/books: Add a new book to the library.
● PUT /api/books/{id}: Update an existing book's information.
● DELETE /api/books/{id}: Remove a book from the library.

● Patron management endpoints:
● GET /api/patrons: Retrieve a list of all patrons.
● GET /api/patrons/{id}: Retrieve details of a specific patron by ID.
● POST /api/patrons: Add a new patron to the system.
● PUT /api/patrons/{id}: Update an existing patron's information.
● DELETE /api/patrons/{id}: Remove a patron from the system.

● Borrowing endpoints:
● POST /api/borrow/{bookId}/patron/{patronId}: Allow a patron to borrow a book.
● PUT /api/return/{bookId}/patron/{patronId}: Record the return of a borrowed book by a patron.
