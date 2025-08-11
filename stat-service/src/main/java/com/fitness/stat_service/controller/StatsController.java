package com.fitness.stat_service.controller;

import com.fitness.stat_service.model.Stats;
import com.fitness.stat_service.repository.StatsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resumen")
public class StatsController {

    private final StatsRepository statsRepository;

    // Inyecta el repositorio para usarlo dentro del controlador
    public StatsController(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    // Endpoint GET /resumen/{username}
    @GetMapping("/{username}")
    public ResponseEntity<Stats> getResumen(@PathVariable String username) {
        // Busca el resumen por usuario
        return statsRepository.findByUsername(username)
                .map(ResponseEntity::ok)  // Si existe, devuelve 200 con el objeto Stats
                .orElse(ResponseEntity.notFound().build()); // Si no, devuelve 404
    }
    @PostMapping
    public ResponseEntity<Stats> createStats(@RequestBody Stats stats) {
        Stats saved = statsRepository.save(stats);
        return ResponseEntity.ok(saved);
}
}
