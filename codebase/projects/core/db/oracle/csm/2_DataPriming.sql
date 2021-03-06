
INSERT INTO csm_application(APPLICATION_ID,APPLICATION_NAME,APPLICATION_DESCRIPTION,DECLARATIVE_FLAG,ACTIVE_FLAG,UPDATE_DATE)
VALUES (1,'c3pr','c3prv2 application',0,0,sysdate);
select CSM_APPLICATI_APPLICATION__SEQ.nextval from dual;

insert into csm_protection_element(PROTECTION_ELEMENT_ID, PROTECTION_ELEMENT_NAME,PROTECTION_ELEMENT_DESCRIPTION,OBJECT_ID,APPLICATION_ID,UPDATE_DATE)
values(1, 'c3pr','c3pr Admin Application','c3pr',1,sysdate);
select CSM_PROTECTIO_PROTECTION_E_SEQ.nextval from dual;

insert into csm_group(GROUP_ID,GROUP_NAME,APPLICATION_ID,UPDATE_DATE)
values(1,'c3pr_admin',1,sysdate);

INSERT INTO csm_application(APPLICATION_ID,APPLICATION_NAME,APPLICATION_DESCRIPTION,DECLARATIVE_FLAG,ACTIVE_FLAG,UPDATE_DATE)
VALUES (2,'csmupt','CSM UPT Super Admin Application',0,0,sysdate);
select CSM_APPLICATI_APPLICATION__SEQ.nextval from dual;

insert into csm_protection_element(PROTECTION_ELEMENT_ID, PROTECTION_ELEMENT_NAME,PROTECTION_ELEMENT_DESCRIPTION,OBJECT_ID,APPLICATION_ID,UPDATE_DATE)
values(2, 'c3pr','UPT Admin Application','c3pr',2,sysdate);
select CSM_PROTECTIO_PROTECTION_E_SEQ.nextval from dual;


INSERT INTO csm_privilege (privilege_id, privilege_name, privilege_description, update_date)
VALUES(1,'CREATE','This privilege grants permission to a user to create an entity. This entity can be an object, a database entry, or a resource such as a network connection', sysdate);
SELECT CSM_PRIVILEGE_PRIVILEGE_ID_SEQ.nextval FROM dual;

INSERT INTO csm_privilege (privilege_id, privilege_name, privilege_description, update_date)
VALUES(2,'ACCESS','This privilege allows a user to access a particular resource.  Examples of resources include a network connection, database connection, socket, module of the application, or even the application itself', sysdate);
SELECT CSM_PRIVILEGE_PRIVILEGE_ID_SEQ.nextval FROM dual;

INSERT INTO csm_privilege (privilege_id, privilege_name, privilege_description, update_date)
VALUES(3,'READ','This privilege permits the user to read data from a file, URL, socket, database, or an object. This can be used at an entity level signifying that the user is allowed to read data about a particular entry which can be object or database row, etc', sysdate);
SELECT CSM_PRIVILEGE_PRIVILEGE_ID_SEQ.nextval FROM dual;

INSERT INTO csm_privilege (privilege_id, privilege_name, privilege_description, update_date)
VALUES(4,'WRITE','This privilege allows a user to write data to a file, URL, socket, database, or object. This can also be used at an entity level signifying that the user is allowed to write data about a particular entity which may include an object, database row, etc', sysdate);
SELECT CSM_PRIVILEGE_PRIVILEGE_ID_SEQ.nextval FROM dual;

INSERT INTO csm_privilege (privilege_id, privilege_name, privilege_description, update_date)
VALUES(5,'UPDATE','This privilege grants permission at an entity level and signifies that the user is allowed to update and modify data for a particular entity.  Entities may include an object, an attribute of the object, a database row, etc', sysdate);
SELECT CSM_PRIVILEGE_PRIVILEGE_ID_SEQ.nextval FROM dual;

INSERT INTO csm_privilege (privilege_id, privilege_name, privilege_description, update_date)
VALUES(6,'DELETE','This privilege permits a user to delete a logical entity. This entity can be an object, a database entry, a resource such as a network connection, etc', sysdate);
SELECT CSM_PRIVILEGE_PRIVILEGE_ID_SEQ.nextval FROM dual;

INSERT INTO csm_privilege (privilege_id, privilege_name, privilege_description, update_date)
VALUES(7,'EXECUTE','This privilege allows a user to execute a particular resource. The resource can be a method, function, behavior of the application, URL, button etc', sysdate);
SELECT CSM_PRIVILEGE_PRIVILEGE_ID_SEQ.nextval FROM dual;

 
