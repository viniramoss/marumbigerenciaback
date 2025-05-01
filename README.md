# Marumbi Backend - Versão Mínima

Esta é uma versão mínima do servidor Java Spring Boot para o projeto Marumbi, otimizada para deploy na Railway.

## Estrutura Simplificada

```
marumbi-backend/
├── src/
│   └── main/
│       ├── java/
│       │   └── br/
│       │       └── com/
│       │           └── marumbi/
│       │               └── MarumbiBackendApplication.java
│       └── resources/
│           └── application.properties
├── pom.xml
├── Dockerfile
├── railway.toml
└── README.md
```

## O que foi alterado

1. **Remoção temporária da dependência do banco de dados**:
   - Para garantir uma inicialização rápida e resolver problemas de healthcheck
   - O banco de dados será integrado após o deploy inicial bem-sucedido

2. **Simplificação da aplicação**:
   - Apenas com endpoints essenciais (/ e /health)
   - Sem dependências complexas

3. **Otimização para Railway**:
   - Configurações específicas para o ambiente Railway
   - Healthcheck configurado adequadamente

## Endpoints Disponíveis

- `GET /` - Página inicial simples
- `GET /health` - Endpoint de healthcheck

## Como Executar Localmente

```bash
mvn spring-boot:run
```

## Próximos Passos (Após Deploy Bem-Sucedido)

1. Reintegrar a conexão com o PostgreSQL
2. Adicionar as classes de modelo, repositório, serviço e controlador
3. Implementar as funcionalidades completas da API

## Deploy na Railway

Após o primeiro deploy bem-sucedido, você poderá adicionar o resto das funcionalidades gradualmente.