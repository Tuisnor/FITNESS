package com.fitness.workout_service.controller;

import com.fitness.workout_service.model.Workout;
import com.fitness.workout_service.repository.WorkoutRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    @Autowired
    private WorkoutRepository workoutRepository;
    
    private final Counter workoutSessionsCounter;

    @Autowired
    public WorkoutController(MeterRegistry meterRegistry) {
        this.workoutSessionsCounter = Counter.builder("workout_sessions_total")
                .description("Total number of workout sessions created")
                .register(meterRegistry);
    }

    @PostMapping
    public Workout createWorkout(@RequestBody Workout workout) {
        workoutSessionsCounter.increment();
        return workoutRepository.save(workout);
    }

    @GetMapping
    public List<Workout> getByUser(@RequestParam Long userId) {
        return workoutRepository.findByUserId(userId);
    }
}
