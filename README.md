# Marumbi Backend

Um servidor Java Spring Boot com PostgreSQL para o projeto Marumbi, configurado para deploy na Railway.

## Estrutura do Projeto

```
marumbi-backend/
├── src/
│   └── main/
│       ├── java/
│       │   └── br/
│       │       └── com/
│       │           └── marumbi/
│       │               ├── controller/
│       │               │   ├── ItemController.java
│       │               │   └── StatusController.java
│       │               ├── model/
│       │               │   └── Item.java
│       │               ├── repository/
│       │               │   └── ItemRepository.java
│       │               ├── service/
│       │               │   └── ItemService.java
│       │               └── MarumbiBackendApplication.java
│       └── resources/
│           └── application.properties
├── pom.xml
├── Procfile
└── README.md
```

## Configuração do Banco de Dados

O projeto está configurado para conectar-se a um banco de dados PostgreSQL na Railway com as seguintes credenciais:

- **URL**: jdbc:postgresql://yamabiko.proxy.rlwy.net:53670/railway
- **Usuário**: postgres
- **Porta**: 53670
- **Database**: railway

## Endpoints da API

- `GET /` - Verifica se o servidor está rodando
- `GET /db-status` - Verifica a conexão com o banco de dados
- `GET /api/items` - Lista todos os itens
- `GET /api/items/{id}` - Obtém um item por ID
- `POST /api/items` - Cria um novo item
- `PUT /api/items/{id}` - Atualiza um item existente
- `DELETE /api/items/{id}` - Exclui um item

## Como Executar Localmente

1. Clone o repositório
2. Execute: `mvn spring-boot:run`
3. Acesse: http://localhost:8080

## Deploy na Railway

1. Crie uma conta na Railway
2. Crie um novo projeto
3. Conecte seu repositório GitHub
4. Configure as variáveis de ambiente:
   ```
   SPRING_DATASOURCE_URL=jdbc:postgresql://yamabiko.proxy.rlwy.net:53670/railway
   SPRING_DATASOURCE_USERNAME=postgres
   SPRING_DATASOURCE_PASSWORD=ofjYLQLWvYvxjXHwTXUlVyAnnGwuIDsC
   ```
5. Configure:
   - Build Command: `./mvnw clean package -DskipTests`
   - Start Command: `java -jar target/marumbi-backend-0.0.1-SNAPSHOT.jar`

O deploy será automático após cada push para a branch principal.