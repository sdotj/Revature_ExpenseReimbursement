package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * configuration connection for production
 * */
public class TestConfig {
    public static String url = "jdbc:h2:.\\Users\\sam\\Desktop";
    public static String username = "sa";
    public static String password = "sa";

    public static void h2InitDao() {

        try(Connection conn=
                    DriverManager.getConnection(url, username, password))
        {
            String sql= "create table ERS_USER_ROLES(\n" +
                    "\tERS_USER_ROLE_ID int primary key\n" +
                    "\t, USER_ROLE varchar(10) not null\n" +
                    ");\n" +
                    "\n" +
                    "--- Reimbursement Status Lookup Table\n" +
                    "create table ERS_REIMBURSEMENT_STATUS(\n" +
                    "\tREIMB_STATUS_ID int primary key\n" +
                    "\t, REIMB_STATUS varchar(10) not null\n" +
                    ");\n" +
                    "\n" +
                    "--- Reimbursement Type Lookup Table\n" +
                    "create table ERS_REIMBURSEMENT_TYPE(\n" +
                    "\tREIMB_TYPE_ID int primary key\n" +
                    "\t, REIMB_TYPE varchar(10) not null\n" +
                    ");\n" +
                    "\n" +
                    "create table ERS_USERS(\n" +
                    "\tERS_USER_ID serial primary key\n" +
                    "\t, ERS_USERNAME varchar(50) not null unique\n" +
                    "\t, ERS_PASSWORD varchar(50) not null\n" +
                    "\t, USER_FIRST_NAME varchar(100) not null\n" +
                    "\t, USER_LAST_NAME varchar(100) not null\n" +
                    "\t, USER_EMAIL varchar(150) not null\n" +
                    "\t, USER_ROLE_ID int not null\n" +
                    "\t, foreign key (USER_ROLE_ID) references ERS_USER_ROLES(ERS_USER_ROLE_ID)\n" +
                    ");\n" +
                    "\n" +
                    "create table ERS_REIMBURSEMENT(\n" +
                    "\tREIMB_ID serial primary key\n" +
                    "\t, REIMB_AMOUNT decimal not null\n" +
                    "\t, REIMB_SUBMITTED timestamp default now()\n" +
                    "\t, REIMB_RESOLVED timestamp\n" +
                    "\t, REIMB_DESCRIPTION varchar(250)\n" +
                    "\t, REIMB_RECEIPT bytea\n" +
                    "\t, REIMB_AUTHOR int not null\n" +
                    "\t, REIMB_RESOLVER int\n" +
                    "\t, REIMB_STATUS_ID int not null\n" +
                    "\t, REIMB_TYPE_ID int not null\n" +
                    "\t, foreign key (REIMB_AUTHOR) references ERS_USERS(ERS_USER_ID)\n" +
                    "\t, foreign key (REIMB_RESOLVER) references ERS_USERS(ERS_USER_ID)\n" +
                    "\t, foreign key (REIMB_STATUS_ID) references ERS_REIMBURSEMENT_STATUS(REIMB_STATUS_ID)\n" +
                    "\t, foreign key (REIMB_TYPE_ID) references ERS_REIMBURSEMENT_TYPE(REIMB_TYPE_ID)\n" +
                    ");\n" +
                    "\n" +
                    "--- Initial inserts of hardcoded values for lookup tables\n" +
                    "insert into ERS_USER_ROLES (ERS_USER_ROLE_ID, USER_ROLE) values (1, 'EMPLOYEE');\n" +
                    "insert into ERS_USER_ROLES (ERS_USER_ROLE_ID, USER_ROLE) values (2, 'MANAGER');\n" +
                    "\n" +
                    "insert into ERS_REIMBURSEMENT_STATUS (REIMB_STATUS_ID, REIMB_STATUS) values (1,'PENDING');\n" +
                    "insert into ERS_REIMBURSEMENT_STATUS (REIMB_STATUS_ID, REIMB_STATUS) values (2,'APPROVED');\n" +
                    "insert into ERS_REIMBURSEMENT_STATUS (REIMB_STATUS_ID, REIMB_STATUS) values (3,'DENIED');\n" +
                    "\n" +
                    "insert into ERS_REIMBURSEMENT_TYPE (REIMB_TYPE_ID, REIMB_TYPE) values (1, 'LODGING');\n" +
                    "insert into ERS_REIMBURSEMENT_TYPE (REIMB_TYPE_ID, REIMB_TYPE) values (2, 'TRAVEL');\n" +
                    "insert into ERS_REIMBURSEMENT_TYPE (REIMB_TYPE_ID, REIMB_TYPE) values (3, 'FOOD');\n" +
                    "insert into ERS_REIMBURSEMENT_TYPE (REIMB_TYPE_ID, REIMB_TYPE) values (4, 'OTHER');" +
                    "\n" +
                    "insert into ers_users values(default, 'testemployee', 'password', 'John', 'Doe', 'test@test.com', 1);";

            Statement state = conn.createStatement();
            state.execute(sql);
        }catch(SQLException e) {
            e.printStackTrace();
        }

    }

    public static void h2DestroyDao() {
        try(Connection conn=
                    DriverManager.getConnection(url,username, password))
        {
            String sql= "DROP TABLE ers_reimbursement;\n" +
                    "DROP TABLE ers_users;\n" +
                    "DROP TABLE ers_reimbursement_status;\n" +
                    "DROP TABLE ers_reimbursement_type;\n" +
                    "DROP TABLE ers_user_roles;";

            Statement state = conn.createStatement();
            state.execute(sql);
        }catch(SQLException e) {
            e.printStackTrace();
        }

    }

}

