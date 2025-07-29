CREATE DATABASE cliente_db;
commit;
CREATE USER 'cliente_user'@'localhost' IDENTIFIED BY 'secure_password_cliente';
commit;
GRANT ALL PRIVILEGES ON cliente_db.* TO 'cliente_user'@'localhost';
commit;
FLUSH PRIVILEGES;