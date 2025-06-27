-- Script para adicionar a coluna 'pago' nas tabelas funcionarios, encargos e outros
-- Execute este script manualmente no banco se o Hibernate não conseguir fazer a migração automática

-- Adicionar a coluna 'pago' na tabela funcionarios
ALTER TABLE funcionarios ADD COLUMN IF NOT EXISTS pago BOOLEAN;
UPDATE funcionarios SET pago = false WHERE pago IS NULL;

-- Adicionar a coluna 'pago' na tabela encargos
ALTER TABLE encargos ADD COLUMN IF NOT EXISTS pago BOOLEAN;
UPDATE encargos SET pago = false WHERE pago IS NULL;

-- Adicionar a coluna 'pago' na tabela outros
ALTER TABLE outros ADD COLUMN IF NOT EXISTS pago BOOLEAN;
UPDATE outros SET pago = false WHERE pago IS NULL; 