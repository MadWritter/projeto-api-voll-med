CREATE TABLE IF NOT EXISTS paciente(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    celular VARCHAR(11) NOT NULL UNIQUE,
    CPF VARCHAR(11) NOT NULL UNIQUE,
    logradouro VARCHAR(100) NOT NULL,
    numero INT CHECK (numero BETWEEN 1 AND 99999), -- garante a adição de um número até 5 dígitos
    complemento VARCHAR(200),
    bairro VARCHAR(50) NOT NULL,
    cidade VARCHAR(50) NOT NULL,
    UF ENUM('AC', 'AL', 'AM', 'AP', 'BA', 'CE', 'DF', 'ES', 'GO', 'MA', 'MG', 'MS', 'MT', 'PA', 'PB', 'PE', 'PI', 'PR', 'RJ', 'RN', 'RO', 'RR', 'RS', 'SC', 'SE', 'SP', 'TO'),
    CEP CHAR(8) NOT NULL,
    PRIMARY KEY(id)
)