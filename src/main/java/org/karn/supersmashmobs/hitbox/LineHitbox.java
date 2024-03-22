package org.karn.supersmashmobs.hitbox;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class LineHitbox {
    public static List<Entity> getEntities(World level, Vec3d start, Vec3d end, double width, int step){
        double dx = (end.x - start.x) / step;
        double dy = (end.y - start.y) / step;
        double dz = (end.z - start.z) / step;
        List<Entity> entities = new ArrayList<>();
        Vec3d current = start;
        for(var i = 0;i<step;i++){
            current = start.add(dx*i,dy*i,dz*i);
            Box box = new Box(current.add(width,width,width),current.add(-width,-width,-width));
            level.getOtherEntities(null,box).forEach(entity -> entities.add(entity));
            //level.getServer().overworld().sendParticles(ParticleTypes.END_ROD,current.x,current.y,current.z,1,0,0,0,0);
        }
        return entities.stream().distinct().toList();
    }

    public static List<LivingEntity> getLivingEntities(World level, Vec3d start, Vec3d end, double width, int step){
        List<LivingEntity> a = new ArrayList<>();
        getEntities(level,start,end,width,step).forEach(entity -> {
            if(entity instanceof LivingEntity){
                a.add((LivingEntity) entity);
            }
        });
        return a;
    }

    public static List<PlayerEntity> getPlayers(World level, Vec3d start, Vec3d end, double width, int step){
        List<PlayerEntity> a = new ArrayList<>();
        getEntities(level,start,end,width,step).forEach(entity -> {
            if(entity instanceof PlayerEntity){
                a.add((PlayerEntity) entity);
            }
        });
        return a;
    }
}
