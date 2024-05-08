INSERT INTO books (title, author)
values
('First Book Title', 'Mr. First Author'),
('Second Book Title', 'Mr. Second Author');



INSERT INTO roles (rolename)
VALUES 
('ROLE_ADMIN'),
('ROLE_USER');



INSERT INTO users (email, password, enabled)
VALUES 
('andres@sheridan.ca', '$2a$10$vzpUjabrpKi6A6ehsqj6EO7GUlXfasns4T2eQCtiyQZDV15Y5ug7S', 1),
('luqman@sheridan.ca', '$2a$10$vzpUjabrpKi6A6ehsqj6EO7GUlXfasns4T2eQCtiyQZDV15Y5ug7S', 1);
--password is 1234



INSERT INTO user_role (userid, roleid)
VALUES
(1, 1), --andres is ROLE_ADMIN
(2, 2); --andres is ROLE_USER



INSERT INTO reviews (bookId, text)
values
(1, 'This is a review for book1 AKA First Book Title'),
(2, 'This is a review for book2 AKA Second Book Title');