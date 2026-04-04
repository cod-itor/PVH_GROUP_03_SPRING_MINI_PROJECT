# PVH Group 03 Spring Mini Project

A comprehensive habit-tracking application built with Spring Boot that gamifies personal development through habit tracking, achievement systems, and user profiling. Users can track daily habits, earn XP for completions, level up, and unlock achievements.

---

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Installation & Setup](#-installation--setup)
- [Configuration](#-configuration)
- [API Endpoints](#-api-endpoints)
- [Database Schema](#-database-schema)
- [Authentication & Security](#-authentication--security)
- [External Integrations](#-external-integrations)
- [Architecture & Design Patterns](#-architecture--design-patterns)

---

## ✨ Features

### Core Functionality
- **User Authentication & Authorization** - JWT-based authentication with email verification via OTP
- **Habit Management** - Create, read, update, and delete habits with customizable frequency and targets
- **Habit Logging** - Track daily habit completions with automatic XP rewards (+10 XP per completion)
- **Gamification System** - Level progression system based on accumulated XP (Level = XP/100 + 1)
- **Achievement System** - Pre-defined achievements unlocked based on milestones and habits
- **User Profiles** - View user statistics, XP history, and achievement records
- **File Management** - AWS S3 integration for profile picture uploads and storage

### Advanced Features
- **Rate Limiting & Security** - Redis-based rate limiting on OTP requests (max 5 per minute)
- **Email Verification** - OTP-based email verification with Thymeleaf HTML templates
- **Real-time XP Tracking** - Live XP updates when habits are completed
- **Nested Data Loading** - Efficient MyBatis relationships for complex data structures
- **Global Exception Handling** - Centralized error management with custom exception types

---

## 🛠️ Tech Stack

### Backend Framework
- **Spring Boot** 4.0.5 - Modern Java framework for building microservices
- **Java** 21 - Latest LTS version with improved performance
- **Maven** - Dependency management and build automation

### Database & ORM
- **PostgreSQL** 15+ - Relational database with UUID extension support
- **MyBatis** 4.0.1 - Lightweight SQL mapper with annotation-based query execution
- **Liquibase** - Database version control and migration management

### Security & Authentication
- **Spring Security** - Comprehensive security framework for authentication and authorization
- **JWT (JSON Web Tokens)** - Stateless authentication with HS256 algorithm, 100-minute expiration
- **JJWT** - JWT creation and verification library

### Caching & Session Management
- **Redis** - In-memory data store for OTP storage (2-minute TTL), rate limiting, and session caching
- **Spring Data Redis** - Redis integration with Spring

### Email & Communication
- **Spring Mail** - Email sending capabilities
- **SMTP (Gmail)** - Gmail SMTP server for production email delivery
- **Thymeleaf** - Templating engine for HTML email rendering

### Cloud & File Storage
- **AWS SDK for Java** - Integration with AWS services
- **S3 (Simple Storage Service)** - Cloud file storage for user uploads
- **RustFS** - Local S3-compatible server for development/testing

### Validation & Utilities
- **Jakarta Validation API** - Bean validation with custom constraints
- **Lombok** - Code generation for getters, setters, and constructors
- **Slf4j & Logback** - Logging framework

### Testing
- **JUnit 5** - Testing framework
- **Spring Test** - Spring Boot testing utilities
- **Mockito** - Mocking framework for unit tests

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/org/ksga/pvh_group_03_spring_mini_project/
│   │   ├── PvhGroup03SpringMiniProjectApplication.java      # Spring Boot entry point
│   │   │
│   │   ├── beanConfig/                                      # Bean & Configuration Classes
│   │   │   ├── BeanConfig.java                             # Application-wide bean definitions
│   │   │   ├── S3Config.java                               # AWS S3 configuration
│   │   │   └── UUIDTypeHandler.java                        # Custom MyBatis UUID type handler
│   │   │
│   │   ├── controller/                                      # REST API Controllers (Layer 1)
│   │   │   ├── AuthController.java                         # Auth endpoints: login, register, verify OTP
│   │   │   ├── HabitController.java                        # Habit CRUD operations
│   │   │   ├── HabitLogController.java                     # Habit completion tracking
│   │   │   ├── AchievementController.java                  # Achievement retrieval
│   │   │   ├── ProfileController.java                      # User profile operations
│   │   │   └── S3FileController.java                       # File upload/download
│   │   │
│   │   ├── service/                                         # Business Logic Layer (Layer 2-3)
│   │   │   ├── AuthService.java                            # Authentication interface
│   │   │   ├── HabitService.java                           # Habit management interface
│   │   │   ├── HabitLogService.java                        # Habit logging interface
│   │   │   ├── AchievementService.java                     # Achievement interface
│   │   │   ├── ProfileService.java                         # Profile management interface
│   │   │   ├── S3FileService.java                          # File storage interface
│   │   │   └── impl/                                        # Service implementations
│   │   │       ├── AuthServiceImpl.java
│   │   │       ├── HabitServiceImpl.java
│   │   │       ├── HabitLogServiceImpl.java
│   │   │       ├── AchievementServiceImpl.java
│   │   │       ├── ProfileServiceImpl.java
│   │   │       └── S3FileServiceImpl.java
│   │   │
│   │   ├── repository/                                      # Data Access Layer (Layer 4)
│   │   │   ├── AppUserRepository.java                      # User data access (MyBatis)
│   │   │   ├── HabitRepository.java                        # Habit data access with @One relationships
│   │   │   ├── HabitLogRepository.java                     # Habit log data access with nested loading
│   │   │   ├── AchievementRepository.java                  # Achievement data access
│   │   │   └── ProfileRepository.java                      # Profile data access
│   │   │
│   │   ├── model/                                           # Entity & DTO Models
│   │   │   ├── entity/                                      # JPA/Hibernate entities
│   │   │   │   ├── AppUser.java                            # User account entity
│   │   │   │   ├── Achievement.java                        # Achievement template entity
│   │   │   │   ├── Habit.java                              # Habit definition entity
│   │   │   │   ├── HabitLog.java                           # Habit completion log entity
│   │   │   │   ├── Quiz.java                               # Quiz entity
│   │   │   │   ├── User.java                               # Legacy user entity
│   │   │   │   └── FileMetadata.java                       # File metadata entity
│   │   │   ├── request/                                     # Request DTOs
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── RegisterRequest.java
│   │   │   │   ├── OtpVerificationRequest.java
│   │   │   │   ├── HabitRequest.java
│   │   │   │   └── HabitLogRequest.java
│   │   │   └── response/                                    # Response DTOs
│   │   │       ├── AuthResponse.java
│   │   │       ├── HabitResponse.java
│   │   │       ├── HabitLogResponse.java
│   │   │       ├── AppUserResponse.java
│   │   │       ├── ApiResponse.java                        # Generic API response wrapper
│   │   │       └── AchievementResponse.java
│   │   │
│   │   ├── jwt/                                             # JWT & Authentication (Layer 5)
│   │   │   ├── JwtUtils.java                               # JWT token generation & validation
│   │   │   ├── JwtAuthFilter.java                          # JWT filter for request interception
│   │   │   └── JwtAuthEntryPoint.java                      # Exception handler for auth failures
│   │   │
│   │   ├── securityConfig/                                  # Security Configuration
│   │   │   └── SecurityConfig.java                         # Spring Security configuration
│   │   │
│   │   ├── exception/                                       # Exception Classes
│   │   │   ├── GlobalExceptionHandler.java                 # Centralized exception handling
│   │   │   ├── NotFoundException.java                      # Resource not found
│   │   │   ├── UserAlreadyExistException.java              # User already registered
│   │   │   ├── InvalidTokenException.java                  # Invalid JWT token
│   │   │   ├── OTPValidationException.java                 # OTP validation failed
│   │   │   ├── NotYetVerifiedException.java                # User not email verified
│   │   │   ├── HabitLogStatusException.java                # Invalid habit log status
│   │   │   ├── RateLimitExceededException.java             # Rate limit exceeded
│   │   │   └── [Additional custom exceptions]
│   │   │
│   │   ├── helper/                                          # Utility & Helper Classes
│   │   │   ├── AuthUtils.java                              # Authentication utilities, getCurrentUserIdentifier()
│   │   │   ├── OtpHelper.java                              # OTP generation and validation
│   │   │   ├── SendOTPMailUtils.java                       # Email sending utilities
│   │   │   └── Frequency.java                              # Enum for habit frequency (DAILY, WEEKLY, etc)
│   │   │
│   │   └── config/                                          # Additional configurations
│   │       └── [Configuration properties]
│   │
│   └── resources/
│       ├── application.properties                           # Spring Boot configuration
│       ├── schema.sql                                       # Database schema initialization
│       └── templates/
│           └── otp-email.html                              # Email template for OTP
│
├── test/
│   └── java/org/ksga/pvh_group_03_spring_mini_project/
│       └── PvhGroup03SpringMiniProjectApplicationTests.java # Integration tests
│
├── docker-compose.yml                                        # Docker services (Redis, S3)
├── pom.xml                                                   # Maven configuration & dependencies
├── mvnw & mvnw.cmd                                           # Maven wrapper scripts
└── .gitignore                                                # Git ignore file
```

### Directory Organization Logic

1. **Controller** - Receives HTTP requests, validates input, delegates to service
2. **Service** - Implements business logic, coordinates between repositories, enforces rules
3. **Repository** - Handles database operations via MyBatis, manages data persistence
4. **Model** - Defines data structures (entities, DTOs)
5. **JWT** - Manages authentication state and token validation
6. **Exception** - Centralized error handling and custom exception types
7. **Helper** - Reusable utilities (AuthUtils, OtpHelper, Email sending)

---

## 🚀 Installation & Setup

### Prerequisites
- **Java 21** or higher
- **Maven 3.8+**
- **PostgreSQL 15+** with UUID extension
- **Redis 7.0+** (optional, for development with caching)
- **Git**

### Step 1: Clone the Repository

```bash
git clone https://github.com/cod-itor/PVH_GROUP_03_SPRING_MINI_PROJECT.git
cd PVH_GROUP_03_SPRING_MINI_PROJECT
```

### Step 2: Install Dependencies

```bash
mvn clean install
```

### Step 3: Configure Environment Variables

Create a `.env` file in the project root (or set system environment variables):

```env
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/pvh_habit_db
DB_USERNAME=postgres
DB_PASSWORD=your_password

# JWT Configuration
JWT_SECRET=your_jwt_secret_key_here_min_32_chars
JWT_EXPIRATION=6000000

# Email Configuration
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your_app_password
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587

# AWS S3 Configuration
AWS_ACCESS_KEY=your_access_key
AWS_SECRET_KEY=your_secret_key
AWS_S3_REGION=us-east-1
AWS_S3_BUCKET=your_bucket_name

# Redis Configuration
REDIS_HOST=localhost
REDIS_PORT=6379
```

### Step 4: Set Up PostgreSQL Database

```bash
# Create database
createdb pvh_habit_db

# Run schema initialization
psql -U postgres -d pvh_habit_db -f src/main/resources/schema.sql
```

### Step 5: Run the Application

```bash
# Using Maven
mvn spring-boot:run

# Or build and run JAR
mvn clean package
java -jar target/PVH_GROUP_03_SPRING_MINI_PROJECT-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

### Step 6: (Optional) Docker Setup

```bash
# Start Redis and S3 services
docker-compose up -d

# View logs
docker-compose logs -f
```

---

## ⚙️ Configuration

### application.properties

```properties
# Server
server.port=8080
server.servlet.context-path=/api

# Database - PostgreSQL
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/pvh_habit_db}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:password}
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQL15Dialect

# JWT
jwt.secret=${JWT_SECRET:your_jwt_secret_key_here_minimum_32_characters}
jwt.expiration=${JWT_EXPIRATION:6000000}

# Email
spring.mail.host=${MAIL_HOST:smtp.gmail.com}
spring.mail.port=${MAIL_PORT:587}
spring.mail.username=${MAIL_USERNAME:your-email@gmail.com}
spring.mail.password=${MAIL_PASSWORD:your_app_password}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Redis
spring.redis.host=${REDIS_HOST:localhost}
spring.redis.port=${REDIS_PORT:6379}

# AWS S3
aws.s3.access-key=${AWS_ACCESS_KEY}
aws.s3.secret-key=${AWS_SECRET_KEY}
aws.s3.region=${AWS_S3_REGION:us-east-1}
aws.s3.bucket=${AWS_S3_BUCKET:pvh-bucket}
```

---

## 📡 API Endpoints

### Authentication Endpoints

```
POST   /api/auth/register              - Register new user
POST   /api/auth/login                 - User login
POST   /api/auth/verify-otp            - Verify email via OTP
POST   /api/auth/resend-otp            - Resend OTP
```

### Habit Management Endpoints

```
GET    /api/habits                     - Get all habits
POST   /api/habits                     - Create new habit
GET    /api/habits/{habit-id}          - Get habit details
PUT    /api/habits/{habit-id}          - Update habit
DELETE /api/habits/{habit-id}          - Delete habit
```

### Habit Logging Endpoints

```
POST   /api/habit-logs                 - Log habit completion (+10 XP)
GET    /api/habit-logs/{habit-id}      - Get habit log history
GET    /api/habit-logs/{habit-log-id}  - Get specific habit log
```

### Achievement Endpoints

```
GET    /api/achievements               - Get all available achievements
GET    /api/achievements/user          - Get user's earned achievements
```

### Profile Endpoints

```
GET    /api/profile                    - Get current user profile
PUT    /api/profile                    - Update user profile
GET    /api/profile/statistics         - Get user XP and level stats
```

### File Management Endpoints

```
POST   /api/files/upload               - Upload file to S3
GET    /api/files/{file-id}            - Download file from S3
DELETE /api/files/{file-id}            - Delete file from S3
```

---

## 🗄️ Database Schema

### Core Tables

#### `app_users`
Stores user account information and authentication data.
```sql
- user_id (UUID, PRIMARY KEY)
- email (VARCHAR, UNIQUE)
- password (VARCHAR, hashed)
- first_name (VARCHAR)
- last_name (VARCHAR)
- is_verified (BOOLEAN)
- total_xp (INTEGER, default 0)
- current_level (INTEGER, default 1)
- created_at (TIMESTAMP)
- updated_at (TIMESTAMP)
```

#### `habits`
Stores habit definitions created by users.
```sql
- habit_id (UUID, PRIMARY KEY)
- user_id (UUID, FOREIGN KEY)
- habit_name (VARCHAR)
- description (TEXT)
- frequency (VARCHAR) - DAILY, WEEKLY, MONTHLY
- target_count (INTEGER)
- created_at (TIMESTAMP)
- updated_at (TIMESTAMP)
```

#### `habit_logs`
Tracks each habit completion with XP rewards.
```sql
- habit_log_id (UUID, PRIMARY KEY)
- habit_id (UUID, FOREIGN KEY)
- user_id (UUID, FOREIGN KEY)
- log_date (TIMESTAMP)
- status (VARCHAR) - COMPLETED, MISSED, SKIPPED
- xp_earned (INTEGER, default 10)
- created_at (TIMESTAMP)
```

#### `achievements`
Pre-defined achievement templates.
```sql
- achievement_id (UUID, PRIMARY KEY)
- achievement_name (VARCHAR)
- description (TEXT)
- badge_icon (VARCHAR)
- xp_threshold (INTEGER)
- created_at (TIMESTAMP)
```

#### `app_user_achievements`
Junction table for user-achievement relationships.
```sql
- user_achievement_id (UUID, PRIMARY KEY)
- user_id (UUID, FOREIGN KEY)
- achievement_id (UUID, FOREIGN KEY)
- earned_at (TIMESTAMP)
- created_at (TIMESTAMP)
```

---

## 🔐 Authentication & Security

### JWT Flow
1. User registers/logs in with email and password
2. Server generates JWT token (HS256, 100-minute expiration)
3. Client includes token in `Authorization: Bearer <token>` header
4. JwtAuthFilter intercepts and validates token
5. SecurityContext stores authenticated user information
6. AuthUtils retrieves current user ID from SecurityContext

### OTP Verification
1. User registers email
2. System generates 6-digit OTP
3. OTP stored in Redis (2-minute TTL)
4. Rate limiting: max 5 OTP requests per 60 seconds
5. User verifies OTP via email
6. User account marked as verified

### Password Security
- Passwords hashed using BCrypt with strength factor 12
- Never stored in plain text
- Salted during hashing process

### Exception Handling
Custom exceptions caught by `GlobalExceptionHandler` and returned as standardized `ApiResponse`:
- `InvalidTokenException` - Malformed or expired JWT
- `NotYetVerifiedException` - User email not verified
- `UserAlreadyExistException` - Registration with existing email
- `OTPValidationException` - Incorrect OTP code
- `NotFoundException` - Resource not found

---

## 🌐 External Integrations

### AWS S3 Integration
- **Purpose**: Cloud storage for user profile pictures and files
- **Configuration**: S3Config.java with access key and secret key
- **Operations**: Upload, download, delete files
- **Development**: RustFS (local S3-compatible server)

### Email Service
- **Provider**: Gmail SMTP
- **Template Engine**: Thymeleaf for HTML rendering
- **Use Cases**: OTP delivery, password reset, notifications
- **Template Location**: `src/main/resources/templates/otp-email.html`

### Redis Caching
- **OTP Storage**: Temporary storage with 2-minute expiration
- **Rate Limiting**: Track OTP request count per user per minute
- **Session Management**: Optional JWT token caching

---

## 🏗️ Architecture & Design Patterns

### Layered Architecture (5-Tier)

```
┌─────────────────────────────┐
│   REST API Controllers      │  Layer 1: Interface
├─────────────────────────────┤
│   Service Implementations   │  Layer 2-3: Business Logic
├─────────────────────────────┤
│   MyBatis Repositories      │  Layer 4: Data Access
├─────────────────────────────┤
│   PostgreSQL Database       │  Layer 5: Persistence
└─────────────────────────────┘
    + JWT & Security Layer
    + Exception Handling Layer
    + Configuration Layer
```

### Design Patterns Used

1. **Dependency Injection** - Spring IoC container manages all beans
2. **Repository Pattern** - Data access abstraction via MyBatis
3. **Service Layer Pattern** - Business logic separation
4. **DTO Pattern** - Request/Response data transfer objects
5. **Enum Pattern** - Frequency (DAILY, WEEKLY, etc)
6. **Exception Handler Pattern** - Global exception handling
7. **Type Handler Pattern** - Custom UUIDTypeHandler for MyBatis

### MyBatis Features

- **Annotation-based Mapping** - @Select, @Insert, @Update, @Delete
- **Result Mapping** - @Results with @Result annotations
- **Nested Object Loading** - @One (one-to-one), @Many (one-to-many) relationships
- **Custom Type Handlers** - UUIDTypeHandler for PostgreSQL UUID ↔ Java UUID conversion
- **Dynamic SQL** - Conditional queries with `<if>` and `<where>` tags

### Gamification System

```
User Completes Habit
    ↓
HabitLogServiceImpl.createHabitLog() called
    ↓
AppUser receives +10 XP
    ↓
Level Recalculation: newLevel = (totalXp / 100) + 1
    ↓
Database Updated: user profile with new XP/level
    ↓
Achievement Check: unlocked if XP threshold reached
    ↓
Response: HabitLog with updated AppUser data
```

---

## 🐳 Docker Setup Guide

Run Docker containers for Redis and RustFS in 3 easy steps!

### 📋 What You Need

1. **Docker Desktop** installed from https://www.docker.com/products/docker-desktop
2. Open Command Prompt or PowerShell
3. Go to your project folder: `D:\PVH_GROUP_03_SPRING_MINI_PROJECT`

### ⚡ 3 Simple Steps

#### **Step 1: Open Command Prompt**
```cmd
cd D:\PVH_GROUP_03_SPRING_MINI_PROJECT
```

#### **Step 2: Start All Services (ONE Command)**
```cmd
docker-compose -f docker-compose.yml -f docker-compose.redis.yml up -d
```

#### **Step 3: Verify Everything Works**
```cmd
docker ps
```

**You should see 2 containers running:**
- `habit_tracker_redis` (Port 6379)
- `spring_mini_project_group03` (Ports 9000, 9001)

✅ **Done! Services are running.**

### 🔍 Quick Check Commands

#### Check if containers are running:
```cmd
docker ps
```

#### Check Redis is working:
```cmd
docker exec habit_tracker_redis redis-cli ping
```
*Should say: `PONG`*

#### Check RustFS is working:
```cmd
curl http://localhost:9000
```

#### See what's happening (logs):
```cmd
docker logs habit_tracker_redis
docker logs spring_mini_project_group03
```

### ⏹️ Stop Services

#### Stop all containers:
```cmd
docker-compose -f docker-compose.yml -f docker-compose.redis.yml down
```

#### Restart all containers:
```cmd
docker-compose -f docker-compose.yml -f docker-compose.redis.yml restart
```

#### Delete everything (data too):
```cmd
docker-compose -f docker-compose.yml -f docker-compose.redis.yml down -v
```

### 📍 Where to Access Services

| Service | Address |
|---------|---------|
| Redis | `localhost:6379` |
| RustFS API | `http://localhost:9000` |
| RustFS Console | `http://localhost:9001` |

### 🚀 Run Spring Application

After Docker containers are running:

```cmd
./mvnw spring-boot:run
```

Or build and run:
```cmd
./mvnw clean package
java -jar target/PVH_GROUP_03_SPRING_MINI_PROJECT-0.0.1-SNAPSHOT.jar
```

Access your app at: `http://localhost:8080`

### ❌ Common Problems & Fixes

#### **Problem: "Port already in use"**
- Kill the process using that port
- Or use different port in docker-compose file

#### **Problem: "Docker not running"**
- Start Docker Desktop application
- Wait 30 seconds
- Try again

#### **Problem: "Cannot connect to Docker daemon"**
- Right-click Command Prompt → Run as Administrator
- Try again

#### **Problem: Container stops immediately**
- Check logs: `docker logs spring_mini_project_group03`
- Pull latest image: `docker pull rustfs/rustfs:latest`
- Try again

### 📚 All Useful Commands

```cmd
# Start services
docker-compose -f docker-compose.yml -f docker-compose.redis.yml up -d

# Stop services
docker-compose -f docker-compose.yml -f docker-compose.redis.yml down

# View all containers
docker ps -a

# View logs
docker logs [container_name]

# Connect to Redis shell
docker exec -it habit_tracker_redis redis-cli

# Clean up everything
docker system prune -a
```

### ✅ Setup Checklist

- [ ] Docker Desktop is running
- [ ] Open Command Prompt in your project folder
- [ ] Run: `docker-compose -f docker-compose.yml -f docker-compose.redis.yml up -d`
- [ ] Check with: `docker ps`
- [ ] All done! ✅

---

**Last Updated**: 04 April 2026 By Dita Rector
