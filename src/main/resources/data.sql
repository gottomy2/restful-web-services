INSERT INTO app_user(id, birth_date, name) VALUES (10001,current_date(),'gottomy2');
INSERT INTO app_user(id, birth_date, name) VALUES (10002,current_date(),'user123');
INSERT INTO app_user(id, birth_date, name) VALUES (10003,current_date(),'user567');

INSERT INTO post(id,description,user_id) VALUES(20001,'TEST POST 123', 10001);
INSERT INTO post(id,description,user_id) VALUES(20002,'Want to get AWS Certified', 10002);
INSERT INTO post(id,description,user_id) VALUES(20003,'Want to learn Vaadin', 10002);