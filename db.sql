drop database if exists projectManager;
create database projectManager;
use projectManager;

create table rol(
	id int auto_increment primary key,
    name char(50) not null unique,
    description char(255) not null
);

create table user(
	id int auto_increment primary key,
    username char(80) not null,
    password char(255) not null,
    fullname char(150) not null,
    email char(150) not null unique,
    create_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp ,
    rol_id int references rol(id)
);

create table project(
	id int auto_increment primary key,
    name char(100) not null,
    leader_id int references user(id),
    description char(255) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp 
);

create table project_miembros(
	project_id int references project(id) on delete cascade,
    user_id int references user(id) on delete cascade,
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    primary key (project_id,user_id)
);

create table task(
	id int auto_increment primary key,
    name char(100) not null,
    description char(255) not null,
    create_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp ,
    project_id int references project(id) on delete cascade
);

create table task_assignment(
	task_id int references task(id) on delete cascade,
    user_id int references user(id) on delete cascade,
    primary key(task_id, user_id)
);

-- Insertando Datos
insert rol values (null,'ADMIN','Administrador con acceso total al sistema'),
				  (null,'COLABORADOR','Usuario del sistema');
                  

insert into user (username, password, fullname, email, rol_id) values 
				 ('admin', 'admin123', 'Jack Leandro Hernandez',"admin@example.com", 1),
				 ('carlos_dev', 'user123', 'Carlos Mendoza',"carlos@example.com", 2),
				 ('ana_coder', 'user123', 'Ana García',"ana@example.com", 2),
				 ('maria_qa', 'user123', 'Maria Rojas',"maria@example.com", 2),
				 ('pedro_backend', 'user123', 'Pedro Picapiedra',"pedro@example.com", 2),
				 ('sofia_frontend', 'user123', 'Sofia Loren',"sofia@example.com", 2);
