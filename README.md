# 🏋️‍♀️ Fitness App – Aplicación distribuida

Proyecto con arquitectura de microservicios para gestión de rutinas de ejercicio y generación de reportes semanales en PDF.

---

## 📦 Servicios

| Servicio         | Puerto | Descripción                                     |
|------------------|--------|-------------------------------------------------|
| PostgreSQL       | 5432   | Base de datos relacional                        |
| User Service     | 8081   | Gestión de usuarios                             |
| Workout Service  | 8082   | Registro de sesiones de ejercicio               |
| Stat Service     | 8083   | Calcula el resumen de actividad semanal         |
| Weekly Report Fn | N/A    | Función FaaS que genera PDF cada domingo        |

---

## 📌 Endpoints REST

### 🧍 User Service

| Método | Ruta              | Descripción        |
|--------|-------------------|--------------------|
| GET    | /usuarios         | Lista usuarios     |
| POST   | /usuarios         | Crear usuario      |

📤 **Ejemplo POST**:
```json
{
  "nombre": "Jose",
  "edad": 30
}
```

---

### 🏋️ Workout Service

| Método | Ruta              | Descripción           |
|--------|-------------------|-----------------------|
| GET    | /sesiones         | Lista sesiones        |
| POST   | /sesiones         | Crear nueva sesión    |

📤 **Ejemplo POST**:
```json
{
  "usuarioId": 1,
  "duracionMinutos": 60,
  "calorias": 500
}
```

---

### 📈 Stat Service

| Método | Ruta                      | Descripción                 |
|--------|---------------------------|-----------------------------|
| GET    | /resumen/{username}     | Retorna resumen semanal     |

📥 **Ejemplo respuesta**:
```json
{
  "id": 1,
  "username": "jose",
  "totalSessions": 5,
  "totalMinutes": 300,
  "totalCalories": 2000
}
```

---

## ⚙️ Weekly Report Function

- **Nombre:** `weekly-report-fn`
- **Trigger:** Cron (una vez cada domingo, 00:00)
- **Lenguaje:** Kotlin + PDFBox
- **Descripción:** Consulta el resumen semanal y genera un archivo PDF en la carpeta `output/`.

Al generarse, el PDF se guarda como:

```
weekly_report_<usuario>_<fecha>.pdf
```

---

## 🗓️ Tareas automatizadas (Cron)

La función `weekly-report-fn` se ejecuta automáticamente cada domingo gracias al cron configurado dentro del contenedor:

```bash
0 0 * * 0 /app/run-report.sh >> /var/log/cron.log 2>&1
```

Puedes probarla manualmente con:

```bash
docker compose exec weekly-report-fn /app/run-report.sh
```

---


## 🛠️ Tecnologías utilizadas

- Docker + Docker Compose
- Kotlin + Gradle
- Spring Boot
- PostgreSQL
- PDFBox
- Cron 


