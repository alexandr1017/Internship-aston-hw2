
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