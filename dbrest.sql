create table department
(
    id   int auto_increment
        primary key,
    name varchar(150) not null
);

create table person
(
    id            int auto_increment
        primary key,
    first_name    varchar(150) not null,
    last_name     varchar(150) null,
    department_id int          null,
    constraint person_department_id_fk
        foreign key (department_id) references department (id)
);

