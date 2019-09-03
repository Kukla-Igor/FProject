CREATE TABLE USERS (
    ID NUMBER,
    CONSTRAINT USER_ID PRIMARY KEY (id),
    FIRST_NAME NVARCHAR2 (20),
    LAST_NAME NVARCHAR2 (20),
    PHONE NVARCHAR2 (20),
    COUNTRY NVARCHAR2 (40),
    CITY NVARCHAR2 (40),
    AGE NUMBER,
    DATE_REGISTERED DATE,
    DATE_LAST_ACTIVE DATE,
    RELATIONSHIP_STATUS NVARCHAR2 (40),
    RELIGION NVARCHAR2 (40),
    SCHOOL NVARCHAR2 (40),
    UNIVERSITY NVARCHAR2 (40)
);