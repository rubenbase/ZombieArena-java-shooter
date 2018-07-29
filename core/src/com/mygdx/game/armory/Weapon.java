package com.mygdx.game.armory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.entities.Zombie;
import com.mygdx.game.system.ResourceHandler;

import java.util.ArrayList;

/**
 * Created by ruben on 05/03/2017.
 */
public class Weapon {
    private int fireRate;
    private int damage;
    private int range;
    private int bullSpeed;
    private int animationId;
    private boolean isMeele;
    private Rectangle AABB;
    private long lastShoot;
    private boolean isHitting;
    private float state;
    private int knockBack;

    private ArrayList<Bullet> bullets;
    private Pool<Bullet> bulletPool;

    public Weapon(int animationId, int fireRate, int damage, int range, int bullSpeed, int knockback, boolean isMeele) {
        this.fireRate = fireRate;
        this.damage = damage;
        this.range = range;
        this.bullSpeed = bullSpeed;
        this.animationId = animationId;
        this.isMeele = isMeele;
        this.lastShoot = 0;
        this.isHitting = false;
        this.state = 0;
        this.knockBack = knockback;

        bullets = new ArrayList<Bullet>();
        bulletPool = new Pool<Bullet>() {
            @Override
            public Bullet newObject() {
                return new Bullet();
            }
        };
    }

    public void update(ArrayList<Zombie> zombies, float dTime) {
        if (isHitting && isMeele) {
            state += dTime;
            for (int j = 0; j < zombies.size(); j++) {
                if (zombies.get(j).isOverlaping(this.AABB)) {
                    zombies.get(j).hacerDanho(this.damage, this.knockBack);
                }
            }
        }
        if (!isMeele) {
            for (int j = 0; j < bullets.size(); j++) {
                boolean alive = bullets.get(j).update(dTime);
                if (!alive) {
                    bulletPool.free(bullets.get(j));
                    bullets.remove(j);
                }
            }
        }
        if ((System.nanoTime() - lastShoot) / 1000000 > 250 && isMeele) {
            isHitting = false;
        }
    }

    public void draw(SpriteBatch batch, float x, float y ,float rot) {
        batch.draw((TextureRegion) ResourceHandler.getAnimation(animationId).getKeyFrame(state, true), x, y, 30, 30, 60, 60, 1, 1, rot);
    }

    public void shoot(float x, float y, float degrees) {
        if ((System.nanoTime() - lastShoot) / 1000000 > this.fireRate) {
            lastShoot = System.nanoTime();
            if (!isMeele) {
                Bullet b = bulletPool.obtain();
                b.init(x, y, this, degrees);
                bullets.add(b);
            }
            else {
                this.isHitting = true;
            }
        }
    }

    public int getDamage() {
        return this.damage;
    }

    public int getFireRate() {
        return this.fireRate;
    }

    public int getRange() {
        return this.range;
    }

    public int getBullSpeed() {
        return this.bullSpeed;
    }

    public int getAnimationId() {
        return this.animationId;
    }

    public void setAABB(Rectangle AABB) {
        this.AABB = AABB;
    }

    public Rectangle getAABB() {
        return this.AABB;
    }

    public boolean isMeele() {
        return this.isMeele;
    }

    public ArrayList<Bullet> getBullets() {
        return this.bullets;
    }

    public int getKnockBack() {
        return this.knockBack;
    }
}