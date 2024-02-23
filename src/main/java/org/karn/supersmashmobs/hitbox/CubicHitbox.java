package org.karn.supersmashmobs.hitbox;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class CubicHitbox {
    public static List<Entity> getEntities(World level, Vec3d pos1, Vec3d pos2){
        Box box = new Box(pos1,pos2);
        return level.getOtherEntities(null,box);
    }

    public static List<LivingEntity> getLivingEntities(World level, Vec3d pos1, Vec3d pos2){
        return (List<LivingEntity>) getEntities(level,pos1,pos2).stream().filter(e -> e.isLiving());
    }

    public static List<PlayerEntity> getPlayers(World level, Vec3d pos1, Vec3d pos2){
        return (List<PlayerEntity>) getLivingEntities(level,pos1,pos2).stream().filter(entity -> entity.isPlayer());
    }
}
