# 🚗 AutoRent Web — Spring Boot

Car Rental System converted from JavaFX desktop → full web app.
Works on **any device** — phone, tablet, laptop.

---

## Run Locally

```bash
cd AutoRentWeb
mvn spring-boot:run
```

Then open your browser: **http://localhost:8080**

---

## Deploy to Railway (Free — Get a Live URL)

### Step 1 — Push to GitHub
1. Create a free account at https://github.com
2. Create a new repository called `autorent`
3. Run these commands in your terminal:

```bash
cd AutoRentWeb
git init
git add .
git commit -m "AutoRent web app"
git remote add origin https://github.com/YOUR_USERNAME/autorent.git
git push -u origin main
```

### Step 2 — Deploy on Railway
1. Go to https://railway.app → Sign up free (use GitHub login)
2. Click **"New Project"** → **"Deploy from GitHub repo"**
3. Select your `autorent` repository
4. Railway auto-detects Java + Maven → builds automatically
5. Click **"Generate Domain"** → you get a URL like:
   `https://autorent-production.up.railway.app`

**Share that URL** — anyone on any device can use your app! ✅

---

## Project Structure

```
AutoRentWeb/
├── pom.xml                          ← Maven config (Spring Boot)
├── Procfile                         ← Railway/Heroku deploy config
└── src/main/
    ├── java/carrental/
    │   ├── App.java                 ← Spring Boot entry point
    │   ├── model/                   ← Car, Customer, Rental (reused)
    │   ├── service/                 ← CarRentalSystem (reused)
    │   └── controller/              ← Web routes
    └── resources/
        ├── templates/               ← HTML pages (Thymeleaf)
        └── static/
            ├── css/style.css        ← Mobile-first styling
            └── js/app.js            ← Price preview + mobile nav
```

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17 + Spring Boot 3 |
| Templating | Thymeleaf |
| Frontend | HTML + CSS + Vanilla JS |
| Build | Maven |
| Deploy | Railway.app (free) |
