from prometheus_client import start_http_server, Counter
import time

workout_sessions_total = Counter('workout_sessions_total', 'Total workout sessions')

if __name__ == '__main__':
    start_http_server(8000)  # Exponer métrica en http://localhost:8000/metrics
    while True:
        workout_sessions_total.inc()  # Incrementa la métrica cada 2 segundos
        time.sleep(2)
