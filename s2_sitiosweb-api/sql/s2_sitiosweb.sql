DELETE FROM PROJECTENTITY_USERENTITY;
DELETE FROM INTERNALSYSTEMSENTITY;
DELETE FROM HARDWAREENTITY;
DELETE FROM ITERATIONENTITY;
DELETE FROM REQUESTENTITY;
DELETE FROM PROJECTENTITY;
DELETE FROM USERENTITY;
DELETE FROM UNITENTITY;
DELETE FROM PROVIDERENTITY;

--select * from PROJECTENTITY;
-- Se crea el primer proyecto
INSERT INTO PROJECTENTITY(ID,COMPANY,INTERNALPROJECT)
VALUES (100000,'IBM',1);
--Se crea el segundo proyecto
INSERT INTO PROJECTENTITY(ID,COMPANY,INTERNALPROJECT)
VALUES (100001,'GOOGLE',0);

--Se crea hardware para el primer proyecto
INSERT INTO HARDWAREENTITY (ID, CORES, CPU, IP, PLATAFORMA, RAM,PROJECT_ID)
VALUES (100000,4,'Intel 80486DX2',15725320214,'WINDOWS',124,100000);
INSERT INTO HARDWAREENTITY (ID, CORES, CPU, IP, PLATAFORMA, RAM,PROJECT_ID)
VALUES (100001,8,'Intel Core i5',15725320217,'SOLARIS',100,100000);
INSERT INTO HARDWAREENTITY (ID, CORES, CPU, IP, PLATAFORMA, RAM,PROJECT_ID)
VALUES (100002,6,'IBM PowerPC 604e',15725320217,'IOSX',99,100000);
INSERT INTO HARDWAREENTITY (ID, CORES, CPU, IP, PLATAFORMA, RAM,PROJECT_ID)
VALUES (100003,5,'Intel Core i5',15725320217,'UBUNTU',95,100000);
INSERT INTO HARDWAREENTITY (ID, CORES, CPU, IP, PLATAFORMA, RAM,PROJECT_ID)
VALUES (100004,9,'Intel 80486DX2',15725320217,'DEBIAN',78,100000);
INSERT INTO HARDWAREENTITY (ID, CORES, CPU, IP, PLATAFORMA, RAM,PROJECT_ID)
VALUES (100005,10,'Intel Core i5',15725320217,'SOLARIS',89,100000);

--Se crea hardware para el segundo proyecto
INSERT INTO HARDWAREENTITY (ID, CORES, CPU, IP, PLATAFORMA, RAM,PROJECT_ID)
VALUES (100006,4,'Intel 80486DX2',15725320214,'WINDOWS',100,100001);
INSERT INTO HARDWAREENTITY (ID, CORES, CPU, IP, PLATAFORMA, RAM,PROJECT_ID)
VALUES (100007,8,'Intel Core i5',15725320217,'UBUNTU',85,100001);
INSERT INTO HARDWAREENTITY (ID, CORES, CPU, IP, PLATAFORMA, RAM,PROJECT_ID)
VALUES (100008,6,'IBM PowerPC 604e',15725320217,'REDHAT',97,100001);
INSERT INTO HARDWAREENTITY (ID, CORES, CPU, IP, PLATAFORMA, RAM,PROJECT_ID)
VALUES (100009,5,'Intel Core i5',15725320217,'centOS',98,100001);
INSERT INTO HARDWAREENTITY (ID, CORES, CPU, IP, PLATAFORMA, RAM,PROJECT_ID)
VALUES (100010,9,'Intel 80486DX2',15725320217,'UBUNTU',84,100001);
INSERT INTO HARDWAREENTITY (ID, CORES, CPU, IP, PLATAFORMA, RAM,PROJECT_ID)
VALUES (100011,10,'Intel Core i7',15725320217,'DEBIAN',79,100001);

--lider proyecto 1
INSERT INTO USERENTITY(ID,DTYPE,EMAIL,IMAGE,LOGIN,NAME,PHONE,LEADER)
VALUES (100000,'DeveloperEntity','nf.abondano','https://previews.123rf.com/images/agencyby/agencyby1302/agencyby130200524/18099898-ingeniero-en-blanco-casco-plano-manos-concepto-de-construcci%C3%B3n-exitosa.jpg','nf.abondano','nicolas','41066578',1);
--se agrega de nuev
--se agrega el lider como tal a la tabla 
UPDATE PROJECTENTITY
SET LEADER_ID = 100000
WHERE ID = 100000;

update userentity set phone = '3001235682' where id = 100002;
update userentity set phone = '3001235679' where id = 100003;
update userentity set phone = '3001235680' where id = 100004;
update userentity set phone = '3001235681' where id = 100005;

--lider del proyecto 2
INSERT INTO USERENTITY(ID,DTYPE,EMAIL,IMAGE,LOGIN,NAME,PHONE,LEADER)
VALUES (100001,'DeveloperEntity','d.galindo','https://image.freepik.com/foto-gratis/paneles-cielo-azul_79405-9694.jpg','a.galindo','daniel','41066588',1);
-- se agrega el lider del proyecto 2
-- se agrega el lider como tal a la tabla 
UPDATE PROJECTENTITY
SET LEADER_ID = 100001
WHERE ID = 100001;
--desarrolladores del proyecto 1
INSERT INTO USERENTITY(ID,DTYPE,EMAIL,IMAGE,LOGIN,NAME,PHONE,LEADER)
VALUES (100002,'DeveloperEntity','af.orozcog','https://image.freepik.com/foto-gratis/ingeniero-industrial-cascos-seguridad-casco-usa-computadora-portatil-pantalla-tactil_61243-421.jpg','af.orozcog','Andres','41066589',0);
INSERT INTO USERENTITY(ID,DTYPE,EMAIL,IMAGE,LOGIN,NAME,PHONE,LEADER)
VALUES (100003,'DeveloperEntity','a.maritnez','https://image.freepik.com/foto-gratis/green-energy-paneles-solares-cielo-azul_79405-5404.jpg','a.martinez','Andres','41066590',0);
--se añaden los desarrolladores del proyecto 1
INSERT INTO PROJECTENTITY_USERENTITY(PROJECTS_ID,DEVELOPERS_ID)
VALUES (100000,100002);
INSERT INTO PROJECTENTITY_USERENTITY(PROJECTS_ID,DEVELOPERS_ID)
VALUES (100000,100003);

--desarrolladores del proyecto 2
INSERT INTO USERENTITY(ID,DTYPE,EMAIL,IMAGE,LOGIN,NAME,PHONE,LEADER)
VALUES (100004,'DeveloperEntity','d.delcastillo','https://image.shutterstock.com/image-photo/image-africanamerican-business-leader-looking-600w-131916311.jpg','d.delcastillo','daniel','41066591',0);
INSERT INTO USERENTITY(ID,DTYPE,EMAIL,IMAGE,LOGIN,NAME,PHONE,LEADER)
VALUES (100005,'DeveloperEntity','s.bautista','https://st2.depositphotos.com/1091429/8652/i/950/depositphotos_86521056-stock-photo-construction-engineer-in-hardhat.jpg','s.bautista','sebastian','41066592',0);
--se añaden los desarrolladores del proyecto 2
INSERT INTO PROJECTENTITY_USERENTITY(PROJECTS_ID,DEVELOPERS_ID)
VALUES (100001,100004);
INSERT INTO PROJECTENTITY_USERENTITY(PROJECTS_ID,DEVELOPERS_ID)
VALUES (100001,100005);
--se crean los sistemas internos y se asigna un proyecto
INSERT INTO INTERNALSYSTEMSENTITY(ID,TYPE,PROJECT_ID)
VALUES (100000,'autonomo',100000);
INSERT INTO INTERNALSYSTEMSENTITY(ID,TYPE,PROJECT_ID)
VALUES (100001,'autonomo',100000);
INSERT INTO INTERNALSYSTEMSENTITY(ID,TYPE,PROJECT_ID)
VALUES (100002,'externo',100001);

--se crean proveedores para los proyectos
INSERT INTO PROVIDERENTITY(ID,NAME)
VALUES (100000,'google');
INSERT INTO PROVIDERENTITY(ID,NAME)
VALUES (100001,'microsoft');

-- se agrega el proveedor como tal a la tabla 
UPDATE PROJECTENTITY
SET PROVIDER_ID = 100000
WHERE ID = 100000;
-- se agrega el proveedor como tal a la tabla
UPDATE PROJECTENTITY
SET PROVIDER_ID = 100001
WHERE ID = 100001;

--Se crean las iteraciones para el proyecto 1
insert into ITERATIONENTITY (id, BEGINDATE, CHANGES, ENDDATE, OBJETIVE, VALIDATIONDATE,PROJECT_ID) values (100001, '2019-02-27 21:24:04', 'cambios en el hardware', '2019-01-28 05:35:55', 'mejorar la aplicación', '2019-11-27 10:09:34',100000);
insert into ITERATIONENTITY (id, BEGINDATE, CHANGES, ENDDATE, OBJETIVE, VALIDATIONDATE,PROJECT_ID) values (100002, '2019-05-13 17:34:58', 'cambios en el software', '2018-12-26 10:22:50', 'mejorar la aplicación', '2019-04-06 21:09:53',100000);
insert into ITERATIONENTITY (id, BEGINDATE, CHANGES, ENDDATE, OBJETIVE, VALIDATIONDATE,PROJECT_ID) values (100003, '2018-12-08 14:17:17', 'cambios en la implementacón de la interfaz', '2019-09-24 02:14:34', 'mejorar la aplicación', '2019-11-11 00:51:33',100000);
insert into ITERATIONENTITY (id, BEGINDATE, CHANGES, ENDDATE, OBJETIVE, VALIDATIONDATE,PROJECT_ID) values (100004, '2019-10-15 11:26:55', 'cambios en el back del proyecto', '2019-04-30 09:41:50', 'mejorar la aplicación', '2019-09-13 01:30:27',100000);
insert into ITERATIONENTITY (id, BEGINDATE, CHANGES, ENDDATE, OBJETIVE, VALIDATIONDATE,PROJECT_ID) values (100005, '2019-03-01 12:35:28', 'cambios en la estructuración de las relaciones', '2019-04-16 04:26:17', 'mejorar la aplicación', '2018-12-29 14:58:38',100000);

--se crean las iteraicones para el proyecto 2
insert into ITERATIONENTITY (id, BEGINDATE, CHANGES, ENDDATE, OBJETIVE, VALIDATIONDATE,PROJECT_ID) values (100006, '2019-05-29 22:17:36', 'cambios en el hardware', '2019-11-24 21:32:44', 'mejorar la aplicación', '2019-03-21 20:52:03',100001);
insert into ITERATIONENTITY (id, BEGINDATE, CHANGES, ENDDATE, OBJETIVE, VALIDATIONDATE,PROJECT_ID) values (100007, '2019-05-17 15:37:47', 'cambios en el software', '2019-05-30 17:27:03', 'mejorar la aplicación', '2019-04-06 00:59:39',100001);
insert into ITERATIONENTITY (id, BEGINDATE, CHANGES, ENDDATE, OBJETIVE, VALIDATIONDATE,PROJECT_ID) values (100008, '2019-06-24 20:39:51', 'cambios en la implementacón de la interfaz', '2019-02-24 05:32:16', 'mejorar la aplicación', '2019-09-01 19:46:05',100001);
insert into ITERATIONENTITY (id, BEGINDATE, CHANGES, ENDDATE, OBJETIVE, VALIDATIONDATE,PROJECT_ID) values (100009, '2019-03-18 05:47:11', 'cambios en el back del proyecto', '2019-03-21 20:57:21', 'mejorar la aplicación', '2019-04-25 00:24:58',100001);
insert into ITERATIONENTITY (id, BEGINDATE, CHANGES, ENDDATE, OBJETIVE, VALIDATIONDATE,PROJECT_ID) values (1000010, '2019-02-04 18:39:15', 'cambios en la estructuración de las relaciones', '2019-08-27 07:21:28', 'mejorar la aplicación', '2019-11-10 07:44:29',100001);

--Se crean las unidades a las que pertenecen los requesters
insert into UNITENTITY (ID,NAME) values (100000,'ing sistemas');
insert into UNITENTITY (ID,NAME) values (100001,'mecanica');

--Se crean los requesters 
insert into USERENTITY (ID, DTYPE, EMAIL, IMAGE, LOGIN, NAME, PHONE, LEADER,UNIT_ID) values (100006, 'RequesterEntity', 'jorge@uni', 'https://www.pandasecurity.com/mediacenter/src/uploads/2016/02/boss.jpg', 'jorge', 'Jorge', '4565654', 0,100000);
insert into USERENTITY (ID, DTYPE, EMAIL, IMAGE, LOGIN, NAME, PHONE, LEADER,UNIT_ID) values (100007, 'RequesterEntity', 'villalobos@uni', 'https://i1.wp.com/devbasu.com/wp-content/uploads/2015/04/describe-your-ideal-boss.jpg?w=2400&ssl=1', 'villa', 'Villalobos', '45621568', 0,100001);

--se crean los request

insert into REQUESTENTITY (ID, BEGINDATE, BUDGET, DESCRIPTION, DUEDATE, ENDDATE, NAME, PURPOSE, REQUESTTYPE, STATUS, UNIT, WEBCATEGORY,PROJECT_ID,REQUESTER_ID) values (100005, '2019-08-27 07:34:35', 244, 'regresiones lineales sobre los datos', '2019-10-19 04:03:07', '2019-04-17 00:43:17', 'analisis datos', 'analisis de datos de la pagina web', 'Development', 'Denied', 'Ing sistemas', 'Descriptive',100000,100006);
insert into REQUESTENTITY (ID, BEGINDATE, BUDGET, DESCRIPTION, DUEDATE, ENDDATE, NAME, PURPOSE, REQUESTTYPE, STATUS, UNIT, WEBCATEGORY,PROJECT_ID,REQUESTER_ID) values (100003, '2019-06-29 17:56:47', 1161, 'cambios en los tiempos de respuesta de la pagina', '2018-12-25 18:57:12', '2019-11-24 05:43:13', 'mejoramiento respuesta', 'cambiar el tiempo de respuesta de la pagina', 'Production', 'Denied', 'Ing industrial', 'Application',100000,100006);
insert into REQUESTENTITY (ID, BEGINDATE, BUDGET, DESCRIPTION, DUEDATE, ENDDATE, NAME, PURPOSE, REQUESTTYPE, STATUS, UNIT, WEBCATEGORY,PROJECT_ID,REQUESTER_ID) values (100002, '2019-03-18 17:40:08', 2343, 'implemmentación de tecnoligia responsive en la pagina', '2019-03-27 12:53:17', '2019-11-06 15:24:39', 'tecnoligia responsive', 'mejorar el responsive de la pagina', 'Change', 'Production', 'departamento de arquitectura empresarial', 'Application',100001,100007);
insert into REQUESTENTITY (ID, BEGINDATE, BUDGET, DESCRIPTION, DUEDATE, ENDDATE, NAME, PURPOSE, REQUESTTYPE, STATUS, UNIT, WEBCATEGORY,PROJECT_ID,REQUESTER_ID) values (100004, '2019-05-02 06:37:24', 4494, 'mejoramiento de la interfaz grafica del proyecto', '2019-03-24 20:46:06', '2019-01-25 15:15:28', 'interfaz grafica proyecto', 'mejorar la interfaz grafica de la aplicacion', 'Production', 'Pending', 'Ing electronica', 'Event',100001,100007);

select * from projectentity;