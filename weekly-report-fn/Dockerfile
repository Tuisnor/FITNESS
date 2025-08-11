FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y cron

WORKDIR /app

COPY build/libs/weekly-report-fn-0.0.1-SNAPSHOT-all.jar app.jar

COPY run-report.sh /app/run-report.sh
RUN chmod +x /app/run-report.sh

COPY crontab.txt /etc/cron.d/weekly-report-cron

RUN chmod 0644 /etc/cron.d/weekly-report-cron && \
    crontab /etc/cron.d/weekly-report-cron

CMD ["cron", "-f"]
