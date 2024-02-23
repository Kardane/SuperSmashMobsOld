package org.karn.supersmashmobs.hitbox;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CylinderHitbox {
    public static List<Entity> getEntities(World level, Vec3d pos1, double radius, double height){
        List<Entity> list = CubicHitbox.getEntities(level,new Vec3d(pos1.x -radius,pos1.y,pos1.z-radius),new Vec3d(pos1.x+radius,pos1.y+height,pos1.z+radius));
        List<Entity> cylinder = new ArrayList<Entity>();
        list.forEach(entity -> {
            double dx = entity.getX() - pos1.x;
            double dz = entity.getZ() - pos1.z;
            if(dx*dx+dz*dz <= radius * radius) cylinder.add(entity);
        });
        return cylinder;
    }

    public static List<LivingEntity> getLivingEntities(World level, Vec3d pos1, double radius, double height){
        return (List<LivingEntity>) getEntities(level,pos1,radius,height).stream().filter(entity -> entity.isLiving());
    }

    public static List<PlayerEntity> getPlayers(World level, Vec3d pos1, double radius, double height){
        return (List<PlayerEntity>) getEntities(level,pos1,radius,height).stream().filter(entity -> entity.isPlayer());
    }
}
