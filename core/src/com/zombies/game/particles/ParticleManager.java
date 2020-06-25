package com.zombies.game.particles;

import com.zombies.main.Game;
import com.zombies.utils.Vector;

import java.util.LinkedList;
import java.util.List;

public class ParticleManager {

    private final Game game;

    private final List<Particle> particles = new LinkedList<>();

    public ParticleManager(Game game) {
        this.game = game;
    }

    public void spawnGrawlingParticles(Vector position) {
        for (int i = 0; i < 5; i++) {
            Vector random = Vector.randomUnitSphere().times(0.4f).plus(position);
            Particle particle = new SpringParticle(random.roundToIntVector(),
                                                   random,
                                                   game.getWorld().getHeight(random.roundToIntVector()));
            game.getTileMap().addDrawable(particle, random.roundToIntVector());
            particles.add(particle);
        }
    }

    public void update(float deltaTime) {
        List<Particle> removed = new LinkedList<>();
        for (Particle p : particles) {
            boolean result = p.update(deltaTime);
            if (result) {
                removed.add(p);
            }
        }
        removed.forEach(p -> {
            game.getTileMap().removeDrawable(p, p.tilePos);
            particles.remove(p);
        });
    }

}
