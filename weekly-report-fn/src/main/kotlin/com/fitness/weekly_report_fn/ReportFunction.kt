package com.fitness.weeklyreportfn

import okhttp3.OkHttpClient
import okhttp3.Request
import com.fasterxml.jackson.module.kotlin.*
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import java.io.File
import java.time.LocalDate

data class Stats(
    val id: Long?,
    val username: String,
    val totalSessions: Int,
    val totalMinutes: Int,
    val totalCalories: Int
)

class ReportFunction {

    private val apiUrl = System.getenv("API_URL") ?: "http://stat-service:8083/resumen"

    private val client = OkHttpClient()

    fun fetchStats(username: String): Stats? {
        val request = Request.Builder()
            .url("$apiUrl/$username")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                println("❌ Error al obtener stats: ${response.code}")
                return null
            }

            val mapper = jacksonObjectMapper()
            return mapper.readValue(response.body!!.string())
        }
    }

    fun generateReport(username: String) {
        println("🔧 Generando reporte para $username desde $apiUrl")

        val stats = fetchStats(username) ?: return

        val date = LocalDate.now()
        val outputDir = File("/app/output")
        if (!outputDir.exists()) {
            println("📂 Carpeta de salida no existe, creando: ${outputDir.absolutePath}")
            outputDir.mkdirs()
        }

        val outputPath = File(outputDir, "weekly_report_${username}_$date.pdf").absolutePath

        println("📁 Guardando PDF en: $outputPath")
        println("📁 Ruta absoluta real: ${File(outputPath).absolutePath}")
        println("📁 Existe directorio padre: ${File(outputPath).parentFile.exists()}")

        generatePdf(stats, outputPath)

        println("✅ Reporte generado: $outputPath")
    }

    fun generatePdf(stats: Stats, path: String) {
        val document = PDDocument()
        val page = PDPage()
        document.addPage(page)

        PDPageContentStream(document, page).use { stream ->
            stream.beginText()
            stream.setFont(PDType1Font.HELVETICA_BOLD, 18f)
            stream.newLineAtOffset(50f, 700f)
            stream.showText("Reporte semanal para ${stats.username}")
            stream.endText()

            stream.beginText()
            stream.setFont(PDType1Font.HELVETICA, 12f)
            stream.newLineAtOffset(50f, 650f)
            stream.showText("Total sesiones: ${stats.totalSessions}")
            stream.newLineAtOffset(0f, -20f)
            stream.showText("Total minutos: ${stats.totalMinutes}")
            stream.newLineAtOffset(0f, -20f)
            stream.showText("Total calorías: ${stats.totalCalories}")
            stream.endText()
        }

        document.save(File(path))
        document.close()
    }
}

fun generatePdf(stats: Stats, path: String) {
    val document = PDDocument()
    val page = PDPage()
    document.addPage(page)

    PDPageContentStream(document, page).use { stream ->
        stream.beginText()
        stream.setFont(PDType1Font.HELVETICA_BOLD, 18f)
        stream.newLineAtOffset(50f, 700f)
        stream.showText("Reporte semanal para ${stats.username}")
        stream.endText()

        stream.beginText()
        stream.setFont(PDType1Font.HELVETICA, 12f)
        stream.newLineAtOffset(50f, 650f)
        stream.showText("Total sesiones: ${stats.totalSessions}")
        stream.newLineAtOffset(0f, -20f)
        stream.showText("Total minutos: ${stats.totalMinutes}")
        stream.newLineAtOffset(0f, -20f)
        stream.showText("Total calorías: ${stats.totalCalories}")
        stream.endText()
    }

    document.save(File(path))
    document.close()
}
