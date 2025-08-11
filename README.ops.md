# README.ops.md

## Despliegue Prometheus + Node Exporter para monitorear workout_service

---

### 1. Clonar el repositorio

```bash
git clone <https://github.com/Tuisnor/FITNESS>
cd fitness-app
```

---

### 2. Ejecutar servicios y Prometheus

```bash
# Levantar servicios principales (Postgres, workout_service, etc.)
docker-compose up -d

# Levantar Prometheus y Node Exporter para monitoreo
docker-compose -f docker-compose.ops.yml up -d
```

---

### 3. Variables y puertos

| Servicio         | Puerto externo | Puerto interno | Comentarios                      |
|------------------|----------------|----------------|---------------------------------|
| Postgres         | 5432           | 5432           | Base de datos                   |
| workout_service  | 8082           | 8082           | Aplicación principal            |
| Prometheus       | 9090           | 9090           | Panel y API de monitoreo        |
| Node Exporter    | 9100           | 9100           | Métricas del host               |

Variables importantes en los servicios:

- En workout_service:
  - `spring.datasource.url=jdbc:postgresql://postgres:5432/fitness`
  - Métricas expuestas en `/actuator/prometheus`

- En Prometheus (prometheus.yml):
  - Scrapea targets en workout_service, node-exporter, etc.

---

### 4. Validación / Prueba funcional

Para validar que Prometheus está vigilando los servicios correctamente:

Ejecuta la siguiente consulta en la terminal:

```bash
curl "http://localhost:9090/api/v1/query?query=up"
```

Deberías ver una respuesta JSON similar a esta:

```json
{
  "status": "success",
  "data": {
    "resultType": "vector",
    "result": [
      { "metric": { "job": "workout_service" }, "value": [ <timestamp>, "1" ] },
      { "metric": { "job": "node-exporter" }, "value": [ <timestamp>, "1" ] }
    ]
  }
}
```

- `"value": "1"` indica que el target está activo y Prometheus puede monitorearlo.
- Deben aparecer al menos dos targets: `workout_service` y `node-exporter`.

---

### 5. Monitorear alertas

- Abre el panel de Prometheus en: [http://localhost:9090/alerts](http://localhost:9090/alerts)
- La alerta `HighWorkoutSessions` estará en estado **Pending** mientras no se cumpla la condición.
- Para probar la alerta, fuerza que el contador `workout_sessions_total` supere 10 durante 5 minutos o modifica temporalmente la regla para hacerla disparar rápido:

```yaml
expr: workout_sessions_total > 10
for: 5m
```

- Al cumplirse, la alerta pasará a **Firing** y cambiará a rojo.

---

### 6. Alertas 

- Pantalla de **Targets** mostrando `workout_service` y `node-exporter` activos.
- Pantalla de **Alerts** con alerta en estado **Pending**.
- Pantalla de alerta en estado **Firing** tras forzar el umbral.

---

