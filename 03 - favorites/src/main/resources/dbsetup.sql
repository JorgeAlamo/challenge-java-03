DROP TABLE IF EXISTS favorite;
CREATE TABLE favorite (
    id INT AUTO_INCREMENT PRIMARY KEY,
    service_cod VARCHAR(50),
    type VARCHAR(50),
    name VARCHAR(50),
    client_cod VARCHAR(50)
);

INSERT INTO favorite (service_cod,type,name,client_cod) VALUES
('30102','Luz','Pago Enel','AS9999A001'),
('42324','Otras Empresas','Pago Cibertec','DF9999B042'),
('63016','Telefono','Pago Movistar Hogar','RT9999F007');