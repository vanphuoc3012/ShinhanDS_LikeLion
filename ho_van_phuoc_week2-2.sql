
DROP TABLE OrderLine;

DROP TABLE orders;

DROP TABLE FoodItems;

DROP TABLE campus;

DROP TABLE members;

DROP TABLE position;

DROP TABLE prices;

DROP SEQUENCE Prices_FoodItemTypeID_SEQ;

CREATE TABLE campus (
    CampusID varchar2(5),
    CampusName varchar2(100),
    Street varchar2(100),
    City varchar2(100),
    State varchar2(100),
    Zip varchar2(100),
    Phone varchar2(100),
    CampusDiscount decimal(2,2),
    CONSTRAINT Campus_PK PRIMARY KEY (CampusID)
);

CREATE TABLE position(
    PositionID varchar2(5),
    Position varchar2(100),
    YearlyMembershipFee DECIMAL(7,2),
    CONSTRAINT Position_PK PRIMARY KEY (PositionID)
);

CREATE TABLE members(
    MemberID varchar(5),
    LastName varchar2(100),
    FirstName varchar2(100),
    CampusAddress varchar2(100),
    CampusPhone varchar2(100),
    CampusID varchar(5),
    PositionID varchar(5),
    ContractDuration INTEGER,
    CONSTRAINT Member_PK PRIMARY KEY (MemberID)
);

ALTER TABLE members
    ADD CONSTRAINT fk_position
        FOREIGN KEY (PositionID)
        REFERENCES position(PositionID);

CREATE TABLE prices(
  FoodItemTypeID NUMBER(20),
  MealType varchar2(100),
  MealPrice decimal(7,2),
  CONSTRAINT food_price_pk PRIMARY KEY (FoodItemTypeID) 
);

CREATE TABLE FoodItems(
    FoodItemID varchar(5),
    FoodItemName varchar(100),
    FoodItemTypeID NUMBER(20),
    CONSTRAINT food_item_pk PRIMARY KEY (FoodItemID),
    CONSTRAINT food_item_type_fk FOREIGN KEY(FoodItemTypeID) REFERENCES prices(foodItemTypeID)
);

CREATE TABLE Orders(
    OrderID varchar2(5),
    MemberID varchar2(5),
    OrderDate varchar2(25),
    CONSTRAINT order_pk PRIMARY KEY (OrderID),
    CONSTRAINT member_fk FOREIGN KEY (MemberID) REFERENCES members(MemberID)
);

CREATE TABLE OrderLine(
    OrderID varchar2(5),
    FoodItemsID varchar2(5),
    Quantity INTEGER,
    CONSTRAINT orderLine_pk PRIMARY KEY (OrderID, FoodItemsID),
    CONSTRAINT order_fk FOREIGN KEY(OrderID) REFERENCES Orders(OrderID),
    CONSTRAINT food_items_fk FOREIGN KEY(FoodItemsID) REFERENCES FoodItems(FoodItemID)
);

CREATE SEQUENCE Prices_FoodItemTypeID_SEQ
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;
  
INSERT INTO Campus VALUES ('1', 'IUPUI', '425 University Blvd.','Indianapolis', 'IN', '46202', '317-274-4591',.08 );
INSERT INTO Campus VALUES ('2', 'Indiana University', '107 S. Indiana Ave.','Bloomington', 'IN', '47405', '812-855-4848',.07 );
INSERT INTO Campus VALUES ('3', 'Purdue University', '475 Stadium Mall Drive','West Lafayette', 'IN', '47907', '765-494-1776',.06 );

INSERT INTO Position
VALUES ('1', 'Lecturer', 1050.50);

INSERT INTO Position
VALUES ('2', 'Associate Professor', 900.50);

INSERT INTO Position
VALUES ('3', 'Assistant Professor', 875.50);

INSERT INTO Position
VALUES ('4', 'Professor', 700.75);

INSERT INTO Position
VALUES ('5', 'Full Professor', 500.50);


INSERT INTO members
VALUES ('1', 'Ellen', 'Monk', '009 Purnell', '812-123-1234', '2', '5', 12);

INSERT INTO members
VALUES ('2', 'Joe', 'Brady', '008 Statford Hall', '765-234-2345', '3', '2', 10);

INSERT INTO members
VALUES ('3', 'Dave', 'Davidson', '007 Purnell', '812-345-3456', '2', '3', 10);

INSERT INTO members
VALUES ('4', 'Sebastian', 'Cole', '210 Rutherford Hall', '765-234-2345', '3', '5', 10);

INSERT INTO members
VALUES ('5', 'Michael', 'Doo', '66C Peobody', '812-548-8956', '2', '1', 10);

INSERT INTO members
VALUES ('6', 'Jerome', 'Clark', 'SL 220', '317-274-9766', '1', '1', 12);

INSERT INTO members
VALUES ('7', 'Bob', 'House', 'ET 329', '317-278-9098', '1', '4', 10);

INSERT INTO members
VALUES ('8', 'Bridget', 'Stanley', 'SI 234', '317-274-5678', '1', '1', 12);

INSERT INTO members
VALUES ('9', 'Bradley', 'Wilson', '334 Statford Hall', '765-258-2567', '3', '2', 10);


INSERT INTO Prices
VALUES (Prices_FoodItemTypeID_SEQ.NEXTVAL, 'Beer/Wine', 5.50);

INSERT INTO Prices
VALUES (Prices_FoodItemTypeID_SEQ.NEXTVAL, 'Dessert', 2.75);

INSERT INTO Prices
VALUES (Prices_FoodItemTypeID_SEQ.NEXTVAL, 'Dinner', 15.50);

INSERT INTO Prices
VALUES (Prices_FoodItemTypeID_SEQ.NEXTVAL, 'Soft Drink', 2.50);

INSERT INTO Prices
VALUES (Prices_FoodItemTypeID_SEQ.NEXTVAL, 'Lunch', 7.25);


INSERT INTO FoodItems
VALUES ('10001', 'Lager', '1');

INSERT INTO FoodItems
VALUES ('10002', 'Red Wine', '1');

INSERT INTO FoodItems
VALUES ('10003', 'White Wine', '1');

INSERT INTO FoodItems
VALUES ('10004', 'Coke', '4');

INSERT INTO FoodItems
VALUES ('10005', 'Coffee', '4');

INSERT INTO FoodItems
VALUES ('10006', 'Chicken a la King', '3');

INSERT INTO FoodItems
VALUES ('10007', 'Rib Steak', '3');

INSERT INTO FoodItems
VALUES ('10008', 'Fish and Chips', '3');

INSERT INTO FoodItems
VALUES ('10009', 'Veggie Delight', '3');

INSERT INTO FoodItems
VALUES ('10010', 'Chocolate Mousse', '2');

INSERT INTO FoodItems
VALUES ('10011', 'Carrot Cake', '2');

INSERT INTO FoodItems
VALUES ('10012', 'Fruit Cup', '2');

INSERT INTO FoodItems
VALUES ('10013', 'Fish and Chips', '5');

INSERT INTO FoodItems
VALUES ('10014', 'Angus and Chips', '5');

INSERT INTO FoodItems
VALUES ('10015', 'Cobb Salad', '5');


INSERT INTO Orders
VALUES ( '1', '9', 'March 5, 2005' );

INSERT INTO Orders
VALUES ( '2', '8', 'March 5, 2005' );

INSERT INTO Orders
VALUES ( '3', '7', 'March 5, 2005' );

INSERT INTO Orders
VALUES ( '4', '6', 'March 7, 2005' );

INSERT INTO Orders
VALUES ( '5', '5', 'March 7, 2005' );

INSERT INTO Orders
VALUES ( '6', '4', 'March 10, 2005' );

INSERT INTO Orders
VALUES ( '7', '3', 'March 11, 2005' );

INSERT INTO Orders
VALUES ( '8', '2', 'March 12, 2005' );

INSERT INTO Orders
VALUES ( '9', '1', 'March 13, 2005' );

INSERT INTO OrderLine
VALUES ( '1', '10001', 1 );

INSERT INTO OrderLine
VALUES ( '1', '10006', 1 );

INSERT INTO OrderLine
VALUES ( '1', '10012', 1 );

INSERT INTO OrderLine
VALUES ( '2', '10004', 2 );

INSERT INTO OrderLine
VALUES ( '2', '10013', 1 );

INSERT INTO OrderLine
VALUES ( '2', '10014', 1 );

INSERT INTO OrderLine
VALUES ( '3', '10005', 1 );

INSERT INTO OrderLine
VALUES ( '3', '10011', 1 );

INSERT INTO OrderLine
VALUES ( '4', '10005', 2 );

INSERT INTO OrderLine
VALUES ( '4', '10004', 2 );

INSERT INTO OrderLine
VALUES ( '4', '10006', 1 );

INSERT INTO OrderLine
VALUES ( '4', '10007', 1 );

INSERT INTO OrderLine
VALUES ( '4', '10010', 2 );

INSERT INTO OrderLine
VALUES ( '5', '10003', 1 );

INSERT INTO OrderLine
VALUES ( '6', '10002', 2 );

INSERT INTO OrderLine
VALUES ( '7', '10005', 2 );

INSERT INTO OrderLine
VALUES ( '8', '10005', 1 );

INSERT INTO OrderLine
VALUES ( '8', '10011', 1 );

INSERT INTO OrderLine
VALUES ( '9', '10001', 1 );

SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE, TABLE_NAME
FROM USER_CONSTRAINTS
WHERE TABLE_NAME = 'ORDERLINE'
    OR TABLE_NAME = 'ORDERS'
    OR TABLE_NAME = 'FOODITEMS'
    OR TABLE_NAME = 'PRICES'
    OR TABLE_NAME = 'POSITION'
    OR TABLE_NAME = 'CAMPUS'
    OR TABLE_NAME = 'MEMBERS'
ORDER BY 3;

SELECT owner, table_name
FROM all_tables
WHERE owner = 'PHUOC';

SELECT sequence_owner, sequence_name
FROM all_sequences;

SELECT 
    FirstName, LastName, position, campus_name
FROM members;

SELECT FirstName, LastName, Position, CampusName,  TO_CHAR(YearlyMembershipFee / 12, 'fm99D00') Monthly_Dues
from MEMBERS
    JOIN POSITION USING(PositionID)
    JOIN CAMPUS USING(CampusID)
order by 4 desc, 2 asc;
    
