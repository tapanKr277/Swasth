# 🏥 Swasth ID - Frontend

**Swasth ID** is a medical records management web application that helps doctors and patients securely access and manage treatment histories. This is the **frontend** of the application, built using **React**, **Redux**, and **RTK Query**, integrating with a Spring Boot backend and secured via **JWT authentication**.

---

## 🚀 Features

- 🔐 **Secure Authentication** using JWT (with refresh token support)
- 👨‍⚕️ **Role-based access** (Doctor / Patient)
- 🧾 **Patient Card Scanning** to fetch medical history
- 💊 **Add Treatment Records** during consultation
- 📅 **Schedule Follow-ups**
- 🔍 **View and Manage Medical History**
- 🪄 **Auto login / Auto logout** with token validation
- ⚛️ Built with modern React + Redux Toolkit + RTK Query

---

## 🧩 Tech Stack

| Layer       | Technology                      |
|-------------|---------------------------------|
| Frontend    | React, Redux Toolkit, RTK Query |
| Styling     | Tailwind CSS                    |
| Auth        | JWT, Refresh Token              |
| State Mgmt  | Redux Toolkit                   |
| Data Fetch  | RTK Query + Axios               |
| Backend     | Spring Boot                     |
| Deployment  | Docker                          |

---

## 📁 Folder Structure
```bash

src/
├── app/                   # Store config and App-level setup
│   ├── store.js
│   └── rootReducer.js
├── features/              # Feature-based slices
│   ├── auth/              # Authentication logic
│   └── treatment/         # Treatment-related features
├── components/            # Shared UI components
├── pages/                 # Page-level components (Dashboard, Login, etc.)
├── utils/                 # JWTService and helper functions
└── index.js               # App entry point

```
---

## 🛠️ Installation & Running Locally

### Prerequisites

- Node.js >= 22.x
- npm or yarn

### Steps

```bash
# Clone the repo
git clone https://github.com/tapankr277/swasth.git

cd swasth/frontend

# Install dependencies
npm install

# Start development server
npm run dev
```

🔑 Environment Variables

Create a .env file in the root directory:

REACT_APP_API_BASE_URL=http://localhost:8080/api

🧪 Future Improvements

🌐 i18n support for multi-language

📱 Responsive UI for mobile/tablets

🔁 Auto-refresh token logic

🧾 PDF export for medical records

🧠 AI-based treatment suggestion (coming soon)

🧑‍💻 Author
Tapan Kumar
📫 tapankr277@gmail.com
