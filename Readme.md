# Kanban Board

[![Build and Test with Gradle](https://github.com/Samuel3/kanban-board/actions/workflows/gradle.yml/badge.svg)](https://github.com/Samuel3/kanban-board/actions/workflows/gradle.yml)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-brightgreen)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9-blue)
![Angular](https://img.shields.io/badge/Angular-17-red)
![GitHub](https://img.shields.io/github/license/Samuel3/kanban-board)

## Description
This is a simple Kanban board that allows you to create, edit, and delete tasks. You can also move tasks between columns. The board is saved in local storage so you can come back to it later.

## License
This project is licensed under the Affero General Public License v3.0 - see the [LICENSE](LICENSE) file for details.

## Run

Start the backend server:
```bash
cd backend
./gradlew bootRun
```

Start the frontend server:
```bash
cd frontend
npm install
npm run start-dev
```

Now open a browser under `http://localhost:4200` to see the frontend and `http://localhost:8080` to see the backend. The frontend will automatically connect to the backend.