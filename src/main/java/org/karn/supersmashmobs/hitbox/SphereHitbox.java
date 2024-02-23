package org.karn.supersmashmobs.hitbox;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class SphereHitbox {
    public static List<Entity> getEntities(World level, Vec3d pos1, double radius){
        List<Entity> list = CubicHitbox.getEntities(level,new Vec3d(pos1.x -radius,pos1.y-radius,pos1.z-radius),new Vec3d(pos1.x+radius,pos1.y+radius,pos1.z+radius));
        return list.stream().filter(entity -> Math.sqrt(entity.squaredDistanceTo(pos1)) < radius).toList();
    }

    public static List<LivingEntity> getLivingEntities(World level, Vec3d pos1, double radius){
        return (List<LivingEntity>) getEntities(level,pos1,radius).stream().filter(entity -> entity.isLiving());
    }

    public static List<PlayerEntity> getPlayers(World level, Vec3d pos1, double radius){
        return (List<PlayerEntity>) getEntities(level,pos1,radius).stream().filter(entity -> entity.isPlayer());
    }
}
