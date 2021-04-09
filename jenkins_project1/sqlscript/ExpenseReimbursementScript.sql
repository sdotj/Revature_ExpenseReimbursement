--- Sam Jenkins, Project 1

--- CREATION OF TABLES

--- User Roles Lookup Table
create table ERS_USER_ROLES(
	ERS_USER_ROLE_ID int primary key
	, USER_ROLE varchar(10) not null
);

--- Reimbursement Status Lookup Table
create table ERS_REIMBURSEMENT_STATUS(
	REIMB_STATUS_ID int primary key
	, REIMB_STATUS varchar(10) not null
);

--- Reimbursement Type Lookup Table
create table ERS_REIMBURSEMENT_TYPE(
	REIMB_TYPE_ID int primary key
	, REIMB_TYPE varchar(10) not null
);

--- User Table
create table ERS_USERS(
	ERS_USER_ID serial primary key
	, ERS_USERNAME varchar(50) not null unique
	, ERS_PASSWORD varchar(50) not null
	, USER_FIRST_NAME varchar(100) not null
	, USER_LAST_NAME varchar(100) not null
	, USER_EMAIL varchar(150) not null
	, USER_ROLE_ID int not null
	, foreign key (USER_ROLE_ID) references ERS_USER_ROLES(ERS_USER_ROLE_ID)
);

--- Reimbursement Table
create table ERS_REIMBURSEMENT(
	REIMB_ID serial primary key
	, REIMB_AMOUNT decimal not null
	, REIMB_SUBMITTED timestamp default now()
	, REIMB_RESOLVED timestamp
	, REIMB_DESCRIPTION varchar(250)
	, REIMB_RECEIPT bytea
	, REIMB_AUTHOR int not null
	, REIMB_RESOLVER int
	, REIMB_STATUS_ID int not null
	, REIMB_TYPE_ID int not null
	, foreign key (REIMB_AUTHOR) references ERS_USERS(ERS_USER_ID)
	, foreign key (REIMB_RESOLVER) references ERS_USERS(ERS_USER_ID)
	, foreign key (REIMB_STATUS_ID) references ERS_REIMBURSEMENT_STATUS(REIMB_STATUS_ID)
	, foreign key (REIMB_TYPE_ID) references ERS_REIMBURSEMENT_TYPE(REIMB_TYPE_ID)
);

--- Initial inserts of hardcoded values for lookup tables
insert into ERS_USER_ROLES (ERS_USER_ROLE_ID, USER_ROLE) values (1, 'EMPLOYEE');
insert into ERS_USER_ROLES (ERS_USER_ROLE_ID, USER_ROLE) values (2, 'MANAGER');

insert into ERS_REIMBURSEMENT_STATUS (REIMB_STATUS_ID, REIMB_STATUS) values (1,'PENDING');
insert into ERS_REIMBURSEMENT_STATUS (REIMB_STATUS_ID, REIMB_STATUS) values (2,'APPROVED');
insert into ERS_REIMBURSEMENT_STATUS (REIMB_STATUS_ID, REIMB_STATUS) values (3,'DENIED');

insert into ERS_REIMBURSEMENT_TYPE (REIMB_TYPE_ID, REIMB_TYPE) values (1, 'LODGING');
insert into ERS_REIMBURSEMENT_TYPE (REIMB_TYPE_ID, REIMB_TYPE) values (2, 'TRAVEL');
insert into ERS_REIMBURSEMENT_TYPE (REIMB_TYPE_ID, REIMB_TYPE) values (3, 'FOOD');
insert into ERS_REIMBURSEMENT_TYPE (REIMB_TYPE_ID, REIMB_TYPE) values (4, 'OTHER');


-- selects
select * from ers_user_roles;
select * from ers_reimbursement_status;
select * from ers_reimbursement_type;
select * from ers_users;
select * from ers_reimbursement;


--- test inserts
--employees
insert into ers_users values(1, 'sdotj', 'cheetah', 'Sam', 'Jenkins', 'sam@gmail.com', 1);
insert into ers_users values(default, 'willj', 'password', 'Will', 'Jenkins', 'will@gmail.com', 1);
insert into ers_users values(default, 'batman', 'nanana', 'Bruce', 'Wayne', 'bat@gmail.com', 1);

--managers
insert into ers_users values(default, 'nogov', 'woodworking', 'Ron', 'Swanson', 'ron@gmail.com', 2);
insert into ers_users values(default, 'bestboss', 'office', 'Michael', 'Scott', 'ms@gmail.com', 2);

--reimbursements
insert into ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_status_id, reimb_type_id)
values (default, 20.00, default, 'For Dinner', 1, 1, 3);

insert into ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_status_id, reimb_type_id)
values (default, 30.00, default, 'Gas Money', 4, 1, 3);


---test select all reimbursements for a certain user
select * from ers_reimbursement where reimb_author = 1;


update ers_reimbursement set reimb_resolver = 6 where reimb_id = 19;



