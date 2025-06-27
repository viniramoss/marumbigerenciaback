# 🚀 Instruções para Redeploy no Railway

## ⚠️ Importante
O campo `pago` foi adicionado na entidade `Funcionario` mas precisa ser deployado no Railway para funcionar.

## 📋 Passos para Redeploy:

### 1. **Via Git (Recomendado)**
```bash
# 1. Commit das mudanças
git add .
git commit -m "feat: adicionar campo pago para funcionários"

# 2. Push para o repositório
git push origin main
```
O Railway fará o redeploy automaticamente após o push.

### 2. **Via Railway Dashboard**
1. Acesse [railway.app](https://railway.app)
2. Entre no projeto `marumbigerenciaback-production`
3. Vá na aba **Deployments**
4. Clique em **Deploy Latest**

### 3. **Verificar se funcionou**
Após o redeploy, teste acessando:
```
https://marumbigerenciaback-production.up.railway.app/api/funcionarios
```

## 🗃️ Migração do Banco (se necessário)

Se o Hibernate não conseguir adicionar a coluna automaticamente, execute no banco:

```sql
-- Adicionar a coluna 'pago' 
ALTER TABLE funcionarios ADD COLUMN IF NOT EXISTS pago BOOLEAN;

-- Definir valor padrão para registros existentes
UPDATE funcionarios SET pago = false WHERE pago IS NULL;
```

## 🔍 Logs para Debug

Para ver os logs do deploy:
1. Railway Dashboard → Seu projeto
2. Aba **Deployments** 
3. Clique no deploy mais recente
4. Vá em **View Logs**

## ✅ Teste Final

Depois do redeploy, teste na folha de pagamento:
1. Vá para a página de **Folha de Pagamento**
2. Tente marcar um funcionário como **pago**
3. Verifique se aparece na **Dashboard** > **Fluxo de Caixa** > **Salários Pagos** 