CREATE TABLE IF NOT EXISTS projeto (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(150) NOT NULL,
  data_criacao DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS funcionario (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(150) NOT NULL,
  cpf VARCHAR(11) NOT NULL UNIQUE,
  email VARCHAR(150) NOT NULL UNIQUE,
  salario NUMERIC(12,2) NOT NULL CHECK (salario >= 0)
);

CREATE TABLE IF NOT EXISTS projeto_funcionario (
  projeto_id BIGINT NOT NULL REFERENCES projeto(id) ON DELETE CASCADE,
  funcionario_id BIGINT NOT NULL REFERENCES funcionario(id) ON DELETE CASCADE,
  PRIMARY KEY (projeto_id, funcionario_id)
);

CREATE INDEX IF NOT EXISTS idx_pf_funcionario ON projeto_funcionario(funcionario_id);
