CREATE TABLE IF NOT EXISTS consulta(
    id BIGINT NOT NULL AUTO_INCREMENT,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL,
    data_da_consulta DATETIME NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(paciente_id) REFERENCES paciente(id),
    FOREIGN KEY(medico_id) REFERENCES medico(id)
)