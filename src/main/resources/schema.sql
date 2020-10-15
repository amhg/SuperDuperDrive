CREATE TABLE IF NOT EXISTS USERS (
  userid INT PRIMARY KEY auto_increment,
  username VARCHAR(20),
  salt VARCHAR,
  password VARCHAR,
  firstname VARCHAR(20),
  lastname VARCHAR(20)
);


CREATE TABLE IF NOT EXISTS FILES (
 fileId INT PRIMARY KEY auto_increment,
 fileName VARCHAR NOT NULL,
 filePath VARCHAR NOT NULL,
 userid INT,
 foreign key (userid) references USERS(userid)
);


CREATE TABLE IF NOT EXISTS NOTES (
 noteId INT PRIMARY KEY auto_increment,
 noteTitle VARCHAR NOT NULL,
 noteDescription VARCHAR NOT NULL,
 userid INT,
 foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS (
 credentialid INT PRIMARY KEY auto_increment,
 url VARCHAR NOT NULL,
 username VARCHAR NOT NULL,
 password VARCHAR NOT NULL,
 credentialKey VARCHAR NOT NULL,
 userid INT,
 foreign key (userid) references USERS(userid)
);
