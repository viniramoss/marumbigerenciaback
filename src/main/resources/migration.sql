-- Script para adicionar a coluna 'pago' na tabela funcionarios
-- Execute este script manualmente no banco se o Hibernate não conseguir fazer a migração automática

-- Adicionar a coluna 'pago' como nullable inicialmente
ALTER TABLE funcionarios ADD COLUMN IF NOT EXISTS pago BOOLEAN;

-- Definir valor padrão para registros existentes
UPDATE funcionarios SET pago = false WHERE pago IS NULL;

-- Opcional: Tornar a coluna NOT NULL após definir valores padrão
-- ALTER TABLE funcionarios ALTER COLUMN pago SET NOT NULL; 