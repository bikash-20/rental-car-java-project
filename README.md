# 🚗 AutoRent Web – Spring Boot Demo

**A full‑stack Java car‑rental web application** that demonstrates modern Java development skills:

- **Spring Boot 3.2.5** backend with RESTful controllers.
- **Thymeleaf** templating for server‑side rendered UI.
- **JPA/Hibernate** persistence using an embedded H2 database.
- **PDF generation** with iText 7 (receipts, invoices).
- **Service layer** (`CarRentalSystem`, `PdfService`, `RevenueService`) showing clean architecture.
- **Unit‑test ready** project structure ready for JUnit 5.
- **Maven** build lifecycle, custom `Procfile` for Railway deployment.
- **Responsive UI** built with vanilla HTML/CSS/JS (no heavy front‑end framework).

---

## How to Run Locally
```bash
cd "AutoRentWeb"
# Build and start the application
mvn spring-boot:run
```
Open your browser at **http://localhost:8080**.

---

## Deploy on Railway (Free Live URL)
1. Push the repo to GitHub (see below).
2. Sign in at https://railway.app and create a new project → *Deploy from GitHub*.
3. Select the repository; Railway auto‑detects a Java + Maven project, builds, and provides a live URL.

---

## Project Structure
```
AutoRentWeb/
├── pom.xml                # Maven dependencies (Spring Boot, JPA, iText, H2)
├── Procfile               # Railway/Heroku start command
└── src/main/
    ├── java/carrental/
    │   ├── App.java                 # Spring Boot entry point
    │   ├── model/                   # Car, Booking, Rental entities
    │   ├── service/                 # Business logic (CarRentalSystem, PdfService, RevenueService)
    │   └── controller/              # Web controllers (UI endpoints)
    └── resources/
        ├── templates/               # Thymeleaf HTML pages
        └── static/                  # CSS & JS assets
```

---

## Tech Stack
| Layer      | Technology                     |
|------------|--------------------------------|
| Backend    | Java 17 + Spring Boot 3       |
| Persistence| JPA/Hibernate + H2 (in‑memory) |
| Templating | Thymeleaf                     |
| PDF        | iText 7                       |
| Build      | Maven                         |
| Deploy     | Railway.app (free)            |

---

## What This Shows About My Java Skills
- **Clean architecture**: separation of concerns via service, controller, and model layers.
- **Dependency management**: Maven with proper version constraints, custom repositories.
- **Database handling**: JPA annotations, repository pattern, transaction management.
- **File I/O & PDF generation**: dynamic receipt creation.
- **DevOps basics**: Docker‑compatible `Procfile`, CI‑ready repository, Railway deployment.
- **Testing mindset**: project scaffold ready for unit/integration tests.

---

## Quick Git Commands to Publish
```bash
cd "AutoRentWeb"
# initialize if not already
git init
git add .
git commit -m "Add README with project features and Java skill showcase"
git remote add origin https://github.com/bikash-20/rental-car-java-project.git
git push -u origin main
```

Now the repository on GitHub reflects the latest README and code.
