
# 🏥 Swasth_ID_BackEnd

Swasth-ID is a secure and scalable Spring Boot-based backend system designed to manage Electronic Health Records (EHR). It enables doctors to scan a patient's unique QR code card to instantly access their complete medical history, prescribe medicines, and manage follow-up visits effectively.

---

## 📌 Features

- ✅ Role-based authentication and authorization (JWT + OAuth2)
- ✅ Doctor and Patient registration with profile management
- ✅ Patient QR card generation and scanning
- ✅ Medical history tracking with treatment records
- ✅ Follow-up scheduling and tracking
- ✅ RESTful APIs ready for integration
- ✅ Entity auditing (createdAt, updatedAt)
- ✅ Dockerized for easy deployment

---

## ⚙️ Technologies Used

- **Java 17+**
- **Spring Boot 3**
- **Spring Security (JWT & OAuth2)**
- **Spring Data JPA**
- **MySQL / PostgreSQL**
- **Lombok**
- **Jakarta Persistence (JPA)**
- **Docker**
- **MapStruct** (for DTO mapping)
- **Java Mail (SMTP Integration)**

---

## 🚀 Getting Started

### 🔧 Step 1: Clone the Repository

```bash
git clone https://github.com/sumanthakur0401/Swasth_ID_BackEnd.git
cd Swasth_ID_BackEnd
```

---

### ⚙️ Step 2: Configure Environment Variables

Create a `.env` file in the root directory and configure the following variables:

```env
SERVER_PORT=8080

DATABASE_URL=jdbc:mysql://localhost:3306/swasthdb
DATABASE_USERNAME=root
DATABASE_PASSWORD=yourpassword

JWT_SECRET=your_jwt_secret_key
TOKEN_EXPIRATION_TIME=3600000
REFRESH_TOKEN_EXPIRATION_TIME=86400000

MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_email_app_password

GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
GOOGLE_REDIRECT_URI=http://localhost:3000/oauth2/redirect

GITHUB_CLIENT_ID=your_github_client_id
GITHUB_CLIENT_SECRET=your_github_client_secret
GITHUB_REDIRECT_URI=http://localhost:3000/oauth2/redirect

FRONTEND_URL=http://localhost:3000
```

> ✅ You can also use tools like Docker Compose to manage these variables.

---

### 📦 Step 3: Build the Project

```bash
./mvnw clean install
```

---

### 🧪 Step 4: Run the Application

Option 1: Use Spring Boot:

```bash
./mvnw spring-boot:run
```

Option 2: Run as a JAR:

```bash
./mvnw clean package
java -jar target/swasth-id-backend.jar
```

---

## 📁 Project Structure

```
src/main/java/com/org/swasth_id_backend/
├── config/        # Security & app configuration
├── controller/    # REST controllers
├── dto/           # Data Transfer Objects
├── entity/        # JPA Entities (User, Doctor, Patient, Treatment)
├── mapper/        # MapStruct mappers
├── repository/    # JPA Repositories
├── service/       # Business logic
├── util/          # Utility functions (e.g. QR generation)
└── SwasthIdBackendApplication.java
```

---

## 🐳 Docker Setup

```bash
docker build -t swasth-backend .
docker run -p 8080:8080 --env-file .env swasth-backend
```

---

## 📬 Contact

Developed by **Tapan Kumar**, **Suman Thakur**, and **Deepanshu**, **Manan Tandon**
🧑‍💻 Java Full Stack Developer | Spring Boot | React | Docker
