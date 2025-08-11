package com.fitness.weekly_report_fn

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File
import java.time.LocalDate
import javax.mail.internet.MimeMessage

@Component
class WeeklyReportScheduler(
    private val reportFunction: ReportFunction,
    private val mailSender: JavaMailSender,
    @Value("\${app.email.recipient}") private val recipientEmail: String,
    @Value("\${app.email.sender}") private val senderEmail: String
) {

    // Ejecutar cada domingo a las 8:00 AM
    @Scheduled(cron = "0 0 8 ? * SUN")
    fun sendWeeklyReport() {
        val username = System.getenv("USERNAME") ?: "defaultUser"
        val date = LocalDate.now()
        val outputDir = File("/app/output")
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }
        val pdfPath = File(outputDir, "weekly_report_${username}_$date.pdf").absolutePath

        // Generar PDF
        val stats = reportFunction.fetchStats(username)
        reportFunction.generatePdf(stats, pdfPath)

        // Enviar email con PDF adjunto
        sendEmailWithAttachment(pdfPath)
    }

    private fun sendEmailWithAttachment(filePath: String) {
        val message: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setFrom(senderEmail)
        helper.setTo(recipientEmail)
        helper.setSubject("Reporte semanal de fitness")
        helper.setText("Adjunto el reporte semanal de fitness.")

        val file = File(filePath)
        helper.addAttachment(file.name, file)

        mailSender.send(message)
    }
}
