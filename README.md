# SaaS Multi-Tenant BI Platform

面向成长型企业的轻量级 BI 平台，采用 Java + Vue3 技术栈，支持 1000+ 租户规模。

## Tech Stack

| Layer | Technology |
|-------|------------|
| Backend | Java 17 + Spring Boot 3.2 + MyBatis-Plus |
| Frontend | Vue 3 + Vite + TypeScript + Pinia |
| Database | MySQL 8.0 |
| Cache | Redis (optional) |

## Multi-Tenant Isolation

| Type | Isolation | Use Case |
|------|-----------|----------|
| SHARED | Shared Schema | Normal tenants (~80%) |
| SCHEMA | Independent Schema | Medium customers |
| DATABASE | Independent Database | Enterprise / High security |

## Project Structure

```
├── saas-backend/           # Java backend
│   └── src/main/java/com/saas/
│       ├── common/        # Common modules
│       │   ├── core/      # Core base classes
│       │   ├── tenant/    # Multi-tenant support
│       │   └── security/  # Security components
│       ├── module/        # Business modules
│       │   ├── auth/      # Authentication
│       │   ├── system/    # System management
│       │   └── tenant/    # Tenant management
│       └── saas-app/      # Application entry
│
├── saas-frontend/         # Vue3 frontend
│   ├── src/
│   │   ├── api/           # API modules
│   │   ├── components/    # Common components
│   │   ├── layouts/       # Layouts
│   │   ├── router/        # Router
│   │   ├── stores/        # Pinia stores
│   │   └── views/         # Pages
│   └── design-system/     # Design system
│
└── sql/                   # Database scripts
```

## Getting Started

### Prerequisites

- JDK 17+
- Node.js 18+
- MySQL 8.0+
- Maven 3.8+

### Database Setup

```bash
mysql -u root -p < sql/V1__init_schema.sql
mysql -u root -p saas_db < sql/V2__init_data.sql
```

### Backend

```bash
cd saas-backend
mvn spring-boot:run
```

API: http://localhost:8080
Swagger: http://localhost:8080/doc.html

### Frontend

```bash
cd saas-frontend
npm install
npm run dev
```

### Default Account

- Username: `admin`
- Password: `admin123`

## API Documentation

After starting the backend, visit: http://localhost:8080/doc.html

## License

MIT
