CREATE TABLE POST (
    ID NUMBER,
    CONSTRAINT POST_ID PRIMARY KEY (id),
    MESSAGE NVARCHAR2 (300),
    DATE_POSTED DATE,
    LOCATION NVARCHAR2 (30),
    USER_POSTED NUMBER,
    CONSTRAINT USER_POSTED_FK FOREIGN KEY (USER_POSTED) REFERENCES USERS (ID),
    USER_PAGE_POSTED NUMBER,
    CONSTRAINT USER_PAGE_POSTED_FK FOREIGN KEY (USER_PAGE_POSTED) REFERENCES USERS (ID)
);