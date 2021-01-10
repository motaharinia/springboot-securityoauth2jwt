CREATE TABLE OAUTH_CLIENT_DETAILS (
                                      CLIENT_ID VARCHAR2(255) NOT NULL PRIMARY KEY,
                                      CLIENT_SECRET VARCHAR2(255) NOT NULL,
                                      RESOURCE_IDS VARCHAR2(255) DEFAULT NULL,
                                      SCOPE VARCHAR2(255) DEFAULT NULL,
                                      AUTHORIZED_GRANT_TYPES VARCHAR2(255) DEFAULT NULL,
                                      WEB_SERVER_REDIRECT_URI VARCHAR2(255) DEFAULT NULL,
                                      AUTHORITIES VARCHAR2(255) DEFAULT NULL,
                                      ACCESS_TOKEN_VALIDITY NUMBER(11) DEFAULT NULL,
                                      REFRESH_TOKEN_VALIDITY NUMBER(11) DEFAULT NULL,
                                      ADDITIONAL_INFORMATION VARCHAR2(4000) DEFAULT NULL,
                                      AUTOAPPROVE VARCHAR2(255) DEFAULT NULL
                                  );

INSERT INTO OAUTH_CLIENT_DETAILS (
    CLIENT_ID,CLIENT_SECRET,
    RESOURCE_IDS,
    SCOPE,
    AUTHORIZED_GRANT_TYPES,
    WEB_SERVER_REDIRECT_URI,AUTHORITIES,
    ACCESS_TOKEN_VALIDITY,REFRESH_TOKEN_VALIDITY,
    ADDITIONAL_INFORMATION,AUTOAPPROVE)
VALUES(
          'USER_CLIENT_APP','{bcrypt}$2a$10$ui8maQFlXodIGzirD1gPT.gBU2Z4fJjrEka0E5IFB3cj0NsvhIaau',
          'USER_CLIENT_RESOURCE,USER_ADMIN_RESOURCE',
          'role_admin,role_user',
          'authorization_code,password,refresh_token,implicit',
          NULL,NULL,
          900,3600,
          '{}',NULL);


INSERT INTO SECURITY_PERMISSION (TITLE) VALUES('can_create_user');
INSERT INTO SECURITY_PERMISSION (TITLE) VALUES('can_update_user');
INSERT INTO SECURITY_PERMISSION (TITLE) VALUES('can_read_user');
INSERT INTO SECURITY_PERMISSION (TITLE) VALUES('can_delete_user');


INSERT INTO SECURITY_ROLE (TITLE) VALUES('role_admin');
INSERT INTO SECURITY_ROLE (TITLE) VALUES('role_user');


INSERT INTO SECURITY_ROLE_JT_SECURITY_PERMISSION (SECURITY_PERMISSION_ID, SECURITY_ROLE_ID) VALUES(1,1); /* can_create_user assigned to role_admin */
INSERT INTO SECURITY_ROLE_JT_SECURITY_PERMISSION (SECURITY_PERMISSION_ID, SECURITY_ROLE_ID) VALUES(2,1); /* can_update_user assigned to role_admin */
INSERT INTO SECURITY_ROLE_JT_SECURITY_PERMISSION (SECURITY_PERMISSION_ID, SECURITY_ROLE_ID) VALUES(3,1); /* can_read_user assigned to role_admin */
INSERT INTO SECURITY_ROLE_JT_SECURITY_PERMISSION (SECURITY_PERMISSION_ID, SECURITY_ROLE_ID) VALUES(4,1); /* can_delete_user assigned to role_admin */
INSERT INTO SECURITY_ROLE_JT_SECURITY_PERMISSION (SECURITY_PERMISSION_ID, SECURITY_ROLE_ID) VALUES(3,2); /* can_read_user assigned to role_user */


INSERT INTO SECURITY_USER (USERNAME,PASSWORD,EMAIL,ENABLED,ACCOUNT_EXPIRED,CREDENTIAL_EXPIRED,ACCOUNT_LOCKED) VALUES ('admin','{bcrypt}$2a$10$ui8maQFlXodIGzirD1gPT.gBU2Z4fJjrEka0E5IFB3cj0NsvhIaau','william@gmail.com',1,0,0,0);
INSERT INTO SECURITY_USER (USERNAME,PASSWORD,EMAIL,ENABLED,ACCOUNT_EXPIRED,CREDENTIAL_EXPIRED,ACCOUNT_LOCKED) VALUES ('user','{bcrypt}$2a$10$ui8maQFlXodIGzirD1gPT.gBU2Z4fJjrEka0E5IFB3cj0NsvhIaau','john@gmail.com',1,0,0,0);


INSERT INTO SECURITY_USER_JT_SECURITY_ROLE (SECURITY_ROLE_ID, SECURITY_USER_ID)VALUES(1, 1); /* role_admin assigned to admin user */
INSERT INTO SECURITY_USER_JT_SECURITY_ROLE (SECURITY_ROLE_ID, SECURITY_USER_ID)VALUES(2, 2); /* role_user assigned to user user */