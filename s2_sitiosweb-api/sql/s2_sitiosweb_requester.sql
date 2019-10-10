-- Elimination of all request data:
DELETE FROM RequestEntity;
-- Insertion of data:
INSERT INTO RequestEntity (beginDate, budget, description, dueDate, endDate,
name, purpose, requestType, status, unit, webCategory) -- requester and project are skipped.
VALUES ('10/9/2017', 1000, 'Test1', '10/9/2018', '10/9/2019',
 'test1', 'Test', 'Change', 'Pending', 'ISIS', 'Event');


