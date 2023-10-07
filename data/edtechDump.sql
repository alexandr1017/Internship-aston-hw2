CREATE DATABASE IF NOT EXISTS `library_new`;
USE `library_new`;


CREATE TABLE course (
                        id INT NOT NULL PRIMARY KEY auto_increment,
                        name VARCHAR(50) NOT NULL,
                        duration INT NOT NULL,
                        price INT NOT NULL,
                        creating_date DATE NOT NULL,
                        teacher_id INT
);

CREATE TABLE student (
                         id INT NOT NULL PRIMARY KEY auto_increment,
                         name VARCHAR(50) NOT NULL,
                         age INT NOT NULL,
                         registration_date DATE NOT NULL
);

CREATE TABLE course_student (
                                course_id INT NOT NULL,
                                student_id INT NOT NULL,
                                PRIMARY KEY (course_id, student_id),
                                FOREIGN KEY (course_id) REFERENCES course(id),
                                FOREIGN KEY (student_id) REFERENCES student(id)
);


CREATE TABLE teacher (
                         id INT NOT NULL PRIMARY KEY auto_increment,
                         name VARCHAR(50) NOT NULL,
                         age INT NOT NULL,
                         hire_date DATE NOT NULL
);

INSERT INTO teacher (name, age, hire_date) VALUES
                                               ('Иван Сидоров',24, '2023-12-10'),
                                               ('Михаил Михаров',39, '2021-01-12'),
                                               ('Петр Петров',21, '2023-10-12');

INSERT INTO course (name, duration, price, creating_date, teacher_id) VALUES
                                                                          ('Программирование',12, 120000, '2012-09-10',1),
                                                                          ('Математика',10, 140000, '2020-11-22',1),
                                                                          ('Машинное обучение',6, 90000, '2018-10-20',1),
                                                                          ('Английский язык', 9, 100000, '2023-12-25',3),
                                                                          ('Базы данных',8, 80000, '2021-01-15',2),
                                                                          ('Web-разработка',10, 110000, '2022-07-18',2),
                                                                          ('Мобильная разработка',11, 130000, '2023-03-14',1);

INSERT INTO student (name, age, registration_date) VALUES
                                                       ('Сидор Ягишев',23, '2023-09-10'),
                                                       ('Александр Михайлов',29, '2021-06-12'),
                                                       ('Петр Алексеев',26, '2022-10-12'),
                                                       ('Иван Иванов',24, '2023-02-20'),
                                                       ('Мария Петрова',22, '2021-11-30'),
                                                       ('Анна Сидорова',27, '2022-05-15');


INSERT INTO course_student (course_id,student_id) VALUES
                                                      (4,2),
                                                      (1,1),
                                                      (2,3),
                                                      (3,4),
                                                      (5,5),
                                                      (6,6),
                                                      (7,1),
                                                      (1,2),
                                                      (2,4),
                                                      (3,6),
                                                      (4,5),
                                                      (5,3),
                                                      (6,1),
                                                      (7,2);

