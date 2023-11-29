DROP TABLE Alumno;
DROP TABLE Grupo;
--Crear Tabla de Alumno


CREATE TABLE Alumno (
    num_mat NUMBER(3),
    nombre VARCHAR2(10),
    ciudad VARCHAR2(10),
    cod_grupo VARCHAR (3),
    precio NUMERIC(5),
    PRIMARY KEY (num_mat)
);

SELECT * FROM Alumno;

DESCRIBE Alumno;

INSERT INTO Alumno VALUES (1,'Juan','Madrid','I11',2500);
INSERT INTO Alumno VALUES (3,'ANA','Leganés','I21',80000);
INSERT INTO Alumno VALUES (2,'MARIA','Leganés','I22',30000);
INSERT iNTO Alumno VALUES (2,'PEDRO','Getafe','I21',20000);
INSERT INTO Alumno VALUES (5,'SALOME','Madrid','I21',25000);

CREATE TABLE Grupo (
    cod_grupo VARCHAR2(3) PRIMARY KEY,
    curso NUMBER(6),
    turno VARCHAR(1)
);

SELECT * FROM Grupo;

CREATE TABLE Grupo;

INSERT INTO Grupo VALUES('I11',1,'M');
INSERT INTO Grupo VALUES('I12',1,'M');
INSERT INTO Grupo VALUES('I13',3,'M');
INSERT INTO Grupo VALUES('I21',1,'T');
INSERT INTO Grupo VALUES('I22',2,'T');
INSERT INTO Grupo VALUES('I31',3,'T');

SELECT * FROM Grupo;

ALTER TABLE Alumno ADD CONSTRAINT FK_Alumno FOREIGN KEY(cod_grupo)REFERENCES Grupo ON DELETE SET NULL;

SELECT * FROM user_constraints;

INSERT INTO Alumno VALUES (10,'Prueba','Madrid','I23',10000);

SELECT * FROM Alumno;

UPDATE Alumno SET nombre = 'Paco' WHERE num_mat = 10;

SELECT * FROM Alumno WHERE num_mat = 10;

DELETE FROM Alumno WHERE num_mat = 10;

SELECT * FROM Alumno;

SELECT DISTINCT ciudad FROM Alumno;
SELECT ciudad FROM Alumno;

SELECT nombre, ciudad FROM Alumno;

SELECT nombre, ciudad FROM Alumno ORDER BY nombre; --ABC

SELECT nombre, ciudad FROM Alumno ORDER BY nombre DESC; --ZYX

DESCRIBE ALUMNO;

select*from alumno;
SELECT nombre,ciudad FROM Alumno ORDER BY nombre;

SELECT nombre,ciudad FROM Alumno ORDER BY nombre, ciudad;

SELECT nombre,precio * 0.10,precio FROM Alumno ORDER BY 2, nombre;
SELECT nombre,precio * 0.10 as PrecioReducido FROM Alumno ORDER BY PrecioReducido, 1;

SELECT*FROM Alumno WHERE cod_grupo = 'I21';

SELECT*FROM Alumno WHERE precio>25000;

SELECT*FROM Alumno WHERE precio <> 25000;

SELECT*FROM Alumno WHERE ciudad = 'Madrid' AND ciudad = 'Leganés'; --No fgunciona porque un Alumno no puede tener dos ciudades

INSERT INTO Alumno VALUES (4,'Salomé','Alicante','I22',NULL);

SELECT * FROM Alumno WHERE precio IS NULL;

SELECT*FROM Alumno WHERE precio IS NOT NULL;

SELECT * FROM Alumno WHERE precio BETWEEN 20000 AND 30000;

SELECT * FROM Alumno WHERE ciudad LIKE '%e%';

SELECT * FROM Alumno WHERE ciudad LIKE '%e';

SELECT * FROM Alumno WHERE ciudad LIKE 'L%';

SELECT * FROM Alumno WHERE ciudad IN ('Madird','Leganés');
SELECT*FROM Alumno WHERE ciudad NOT IN ('Madrid','Leganés');







