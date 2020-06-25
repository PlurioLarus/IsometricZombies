package com.zombies.game.particles;

import com.zombies.utils.IntVector;
import com.zombies.utils.Vector;

public class SpringParticle extends Particle {

    float velocity = 4;
    float initialHeight;

    public SpringParticle(IntVector tilePos, Vector position, float height) {
        super(tilePos, position, height, "dirt-particle");
        drawingOffset = new IntVector(0, 0);
        initialHeight = height;
    }

    @Override
    public boolean update(float deltaTime) {
        height += velocity * deltaTime * 8;
        if (height < initialHeight) {
            return true;
        }
        velocity -= 9.81 * deltaTime;
        return false;
    }
}
