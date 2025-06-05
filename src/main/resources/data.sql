-- Script para inserir dados iniciais na tabela de funcionários
-- Este arquivo será executado automaticamente pelo Spring Boot

-- Inserir alguns funcionários de exemplo
INSERT INTO funcionarios (nome, salario, loja) VALUES 
('Maria Silva', 2500.00, 'UN1'),
('João Santos', 2800.00, 'UN1'),
('Ana Oliveira', 2300.00, 'UN2'),
('Carlos Pereira', 3200.00, 'UN1'),
('Fernanda Costa', 2600.00, 'UN2'),
('Roberto Almeida', 2900.00, 'UN2'),
('Julia Lima', 2400.00, 'UN1'),
('Pedro Martins', 3100.00, 'UN2'),
('Camila Rocha', 2700.00, 'UN1'),
('Diego Ferreira', 2550.00, 'UN2')
ON CONFLICT DO NOTHING; 