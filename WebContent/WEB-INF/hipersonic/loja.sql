# SQL command to create the tables:

CREATE TABLE usuario (
      login VARCHAR(10) NOT NULL,
      senha VARCHAR(10) NOT NULL,
      nome VARCHAR(50) NOT NULL,
      cartao VARCHAR(19) NOT NULL,
      email VARCHAR(40) NOT NULL,
      telefone VARCHAR(15) NOT NULL,
PRIMARY KEY(login));

CREATE TABLE role (
      login VARCHAR(20) NOT NULL,
      role VARCHAR(20) NOT NULL,
PRIMARY KEY(login,role),
FOREIGN KEY (login) REFERENCES usuario(login)
                     ON DELETE CASCADE);

CREATE TABLE categoria (
      id BIGINT AUTO_INCREMENT NOT NULL,
      nome VARCHAR(20) NOT NULL,
      descricao VARCHAR(50) NOT NULL,
PRIMARY KEY(id));

CREATE TABLE produto (
      id BIGINT AUTO_INCREMENT NOT NULL,
      nome VARCHAR(20) NOT NULL,
      descricao VARCHAR(50) NOT NULL,
      cor VARCHAR(10) NOT NULL,
      peso DOUBLE NOT NULL,
      preco DOUBLE NOT NULL,
PRIMARY KEY(id));

CREATE TABLE produto_categoria (
      produto BIGINT NOT NULL,
      categoria BIGINT NOT NULL,
PRIMARY KEY(produto,categoria),
 FOREIGN KEY (produto) REFERENCES produto(id)
                     ON DELETE CASCADE,
 FOREIGN KEY (categoria) REFERENCES categoria(id)
                     ON DELETE CASCADE);

CREATE TABLE promocao (
      produto BIGINT NOT NULL,
      percentual INT NOT NULL,
PRIMARY KEY(produto),
 FOREIGN KEY (produto) REFERENCES produto(id)
                     ON DELETE CASCADE);

CREATE TABLE pedido (
      id BIGINT AUTO_INCREMENT NOT NULL,
      cliente VARCHAR(20) NOT NULL,
      rua VARCHAR(50) NOT NULL,
      numero INT NOT NULL,
      cidade VARCHAR(20) NOT NULL,
      estado VARCHAR(2) NOT NULL,
      cep VARCHAR(9) NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY (cliente) REFERENCES usuario(login)
                     ON DELETE CASCADE);
CREATE TABLE produto_pedido (
      pedido BIGINT NOT NULL,
      produto BIGINT NOT NULL,
      quantidade INT NOT NULL,
PRIMARY KEY(pedido,produto),
FOREIGN KEY (pedido) REFERENCES pedido(id)
                     ON DELETE CASCADE,
FOREIGN KEY (produto) REFERENCES produto(id)
                     ON DELETE CASCADE);


