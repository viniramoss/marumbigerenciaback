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

-- Inserir dados de exemplo para encargos
INSERT INTO encargos (tipo, valor, loja, mes_ano, observacoes, data_criacao) VALUES
('INSS', 850.00, 'UN1', '2024-01', 'INSS sobre folha de pagamento', '2024-01-15'),
('FGTS', 650.00, 'UN1', '2024-01', 'FGTS sobre folha de pagamento', '2024-01-15'),
('VALE_TRANSPORTE', 420.00, 'UN1', '2024-01', 'Vale transporte funcionários', '2024-01-15'),
('INSS', 720.00, 'UN2', '2024-01', 'INSS sobre folha de pagamento', '2024-01-15'),
('FGTS', 580.00, 'UN2', '2024-01', 'FGTS sobre folha de pagamento', '2024-01-15'),
('VALE_TRANSPORTE', 380.00, 'UN2', '2024-01', 'Vale transporte funcionários', '2024-01-15'),
('INSS', 870.00, 'UN1', '2024-02', 'INSS sobre folha de pagamento', '2024-02-15'),
('FGTS', 670.00, 'UN1', '2024-02', 'FGTS sobre folha de pagamento', '2024-02-15')
ON CONFLICT DO NOTHING;

-- Inserir dados de exemplo para outros itens
INSERT INTO outros (tipo, descricao, valor, funcionario_id, loja, mes_ano, observacoes, data_criacao) VALUES
('BONUS', 'Bônus de vendas', 500.00, 1, 'UN1', '2024-01', 'Bônus por superação de metas', '2024-01-31'),
('FERIAS', 'Férias Maria Silva', 2500.00, 1, 'UN1', '2024-01', 'Férias do mês de janeiro', '2024-01-31'),
('DESTAQUE', 'Funcionário destaque', 300.00, 3, 'UN2', '2024-01', 'Melhor atendimento do mês', '2024-01-31'),
('VALE_TRANSPORTE', 'Vale transporte extra', 150.00, NULL, 'UN1', '2024-01', 'Vale transporte adicional', '2024-01-31'),
('BONUS', 'Bônus de vendas', 400.00, 5, 'UN2', '2024-01', 'Bônus por superação de metas', '2024-01-31'),
('ABONO', 'Abono salarial', 200.00, 4, 'UN1', '2024-01', 'Abono por horas extras', '2024-01-31'),
('AJUDA_CUSTO', 'Ajuda de custo viagem', 250.00, 6, 'UN2', '2024-01', 'Ajuda para viagem a trabalho', '2024-01-31'),
('OUTROS', 'Prêmio assiduidade', 100.00, NULL, 'UN2', '2024-01', 'Prêmio por não faltar', '2024-01-31')
ON CONFLICT DO NOTHING; 