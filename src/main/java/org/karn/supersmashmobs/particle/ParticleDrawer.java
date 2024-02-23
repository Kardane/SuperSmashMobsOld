package org.karn.supersmashmobs.particle;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ParticleDrawer {
    public static void DotArrayDraw(ServerWorld world, ParticleEffect Particle, List<Map<String, Double>> array){
        DotArrayDraw(world,Particle,array,false,array.size());
    }

    public static void DotArrayDraw(ServerWorld world, ParticleEffect Particle, List<Map<String, Double>> array, boolean force){
        DotArrayDraw(world,Particle,array,force,array.size());
    }

    public static void DotArrayDraw(ServerWorld world, ParticleEffect Particle, List<Map<String, Double>> array, boolean force, Integer step) {
        for (int i = 0; i < step; i++) {
            Map<String, Double> Dot = array.get(0);
            DotDraw(world, Particle, Dot.get("x"), Dot.get("y"), Dot.get("z"), force ,new Vec3d(Dot.get("dx"),Dot.get("dy"),Dot.get("dz")));
            array.remove(0);
            if (array.size() < 1) {
                return;
            }
        }
        if (array.size() > 0) {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                public void run() {
                    DotArrayDraw(world, Particle, array, force,step);
                }
            };
            int delay = 50; // 0.05초 뒤에 실행
            timer.schedule(task, delay);
        }
    }

    private static void DotDraw(ServerWorld world, ParticleEffect Particle, double x, double y, double z, boolean force) {
        DotDraw(world,Particle,x,y,z,force,new Vec3d(0,0,0));
    }

    private static void DotDraw(ServerWorld world, ParticleEffect Particle, double x, double y, double z, boolean force, Vec3d movement) {
        for(int j = 0; j < world.getPlayers().size(); ++j) {
            ServerPlayerEntity player = world.getPlayers().get(j);
            world.spawnParticles(player, Particle, force, x, y, z, 0, movement.x, movement.y, movement.z,1);
        }

    }

    //rotate

    public static List<Map<String, Double>> ArrayRotate(List<Map<String, Double>> array, Vec2f rotation, Vec3d rotationPos) {
        return ArrayRotate(array, rotation.x, rotation.y, 0, rotationPos);
    }
    public static List<Map<String, Double>> ArrayRotate(List<Map<String, Double>> array, double x, double y, double z, Vec3d rotationPos) {
        double cosX = Math.cos((x / 180) * Math.PI);
        double sinX = Math.sin((x / 180) * Math.PI);
        double cosY = Math.cos((y / 180) * Math.PI);
        double sinY = Math.sin((y / 180) * Math.PI);
        double cosZ = Math.cos((z / 180) * Math.PI);
        double sinZ = Math.sin((z / 180) * Math.PI);
        for (Map<String, Double> dot : array) {
            double rx = dot.get("x") - rotationPos.x;
            double ry = dot.get("y") - rotationPos.y;
            double rz = dot.get("z") - rotationPos.z;
            double x1 = rx;
            double y1 = ry * cosX - rz * sinX;
            double z1 = ry * sinX + rz * cosX;
            double x2 = x1 * cosY + z1 * sinY;
            double y2 = y1;
            double z2 = -x1 * sinY + z1 * cosY;
            double x3 = x2 * cosZ - y2 * sinZ;
            double y3 = x2 * sinZ + y2 * cosZ;
            double z3 = z2;
            dot.put("x", x3 + rotationPos.x);
            dot.put("y", y3 + rotationPos.y);
            dot.put("z", z3 + rotationPos.z);
        }
        return array;
    }
}
