CREATE TABLE POST (
    ID NUMBER,
    CONSTRAINT POST_ID PRIMARY KEY (id),
    MESSAGE NVARCHAR2 (300),
    DATE_POSTED DATE,
    USER_POSTED NUMBER,
    CONSTRAINT USER_POSTED_FK FOREIGN KEY (USER_POSTED) REFERENCES USERS (ID)
);