package org.karn.supersmashmobs.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import org.karn.supersmashmobs.effect.AbstractSSMEffect;
import org.karn.supersmashmobs.effect.SSMEffectType;

import java.util.*;


public class Misc {

    public static Vec3d getUnitVector(Vec3d vec1, Vec3d vec2){
        Vec3d a = new Vec3d(vec2.x-vec1.x,vec2.y-vec1.y,vec2.z-vec1.z);
        double m = MathHelper.sqrt((float) (a.x*a.x + a.y*a.y + a.z*a.z));
        return new Vec3d(-a.x/m,-a.y/m,-a.z/m);
    }
    public static Vec3d getLocalPos(Vec3d pos, Vec2f rot, double right, double up, double forward){
        return getLocalPos(pos,rot,new Vec3d(right,up,forward));
    }
    public static Vec3d getLocalPos(Vec3d pos, Vec2f rot, Vec3d movement) {
        float var4 = MathHelper.cos((rot.y + 90.0F) * (float) (Math.PI / 180.0));
        float var5 = MathHelper.sin((rot.y + 90.0F) * (float) (Math.PI / 180.0));
        float var6 = MathHelper.cos(-rot.x * (float) (Math.PI / 180.0));
        float var7 = MathHelper.sin(-rot.x * (float) (Math.PI / 180.0));
        float var8 = MathHelper.cos((-rot.x + 90.0F) * (float) (Math.PI / 180.0));
        float var9 = MathHelper.sin((-rot.x + 90.0F) * (float) (Math.PI / 180.0));
        Vec3d var10 = new Vec3d((double) (var4 * var6), (double) var7, (double) (var5 * var6));
        Vec3d var11 = new Vec3d((double) (var4 * var8), (double) var9, (double) (var5 * var8));
        Vec3d var12 = var10.crossProduct(var11).multiply(-1.0);
        double var13 = var10.x * movement.z + var11.x * movement.y + var12.x * movement.x;
        double var15 = var10.y * movement.z + var11.y * movement.y + var12.y * movement.x;
        double var17 = var10.z * movement.z + var11.z * movement.y + var12.z * movement.x;
        return new Vec3d(pos.x + var13, pos.y + var15, pos.z + var17);
    }
    public static boolean isVoidDamage(DamageSource source){
        return source.isOf(DamageTypes.GENERIC_KILL) || source.isOf(DamageTypes.GENERIC) || source.isOf(DamageTypes.OUT_OF_WORLD)|| source.isOf(DamageTypes.FALL);
    }
    public static int randomInt(int min, int max){
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static void playSoundToAll(MinecraftServer server, SoundEvent sound, SoundCategory category, float volume, float pitch){
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            player.playSound(sound, category, volume, pitch);
        }
    }

    // This method is used to spread players in a circle
    public static Map<PlayerEntity, Vec3d> spreadPlayers(ServerWorld world, List<ServerPlayerEntity> players, Vec2f center, float spreadDistance, float maxRange, int maxY) {
        Random random = Random.create();
        double d = (double)(center.x - maxRange);
        double e = (double)(center.y - maxRange);
        double f = (double)(center.x + maxRange);
        double g = (double)(center.y + maxRange);
        Pile[] piles = makePiles(random, players.size(), d, e, f, g);
        spread(center, (double)spreadDistance, world, random, d, e, f, g, maxY, piles);
        return getMinDistance(players, world, piles, maxY);
    }

    private static Map<PlayerEntity, Vec3d> getMinDistance(List<ServerPlayerEntity> players, ServerWorld world, Pile[] piles, int maxY) {
        double d = 0.0;
        int i = 0;
        Map<PlayerEntity, Vec3d> map = new HashMap<>();
        double e;
        for (PlayerEntity player: players) {
            Entity entity = (Entity) player;
            Pile pile;
            pile = piles[i++];
            map.put(player, new Vec3d((double)MathHelper.floor(pile.x) + 0.5, (double)pile.getY(world, maxY), (double)MathHelper.floor(pile.z) + 0.5));
            //entity.teleport(world, (double)MathHelper.floor(pile.x) + 0.5, (double)pile.getY(world, maxY), (double)MathHelper.floor(pile.z) + 0.5, Set.of(), entity.getYaw(), entity.getPitch());
            e = Double.MAX_VALUE;
            Pile[] var14 = piles;
            int var15 = piles.length;

            for(int var16 = 0; var16 < var15; ++var16) {
                Pile pile2 = var14[var16];
                if (pile != pile2) {
                    double f = pile.getDistance(pile2);
                    e = Math.min(f, e);
                }
            }
        }
        return map;
    }

    private static void spread(Vec2f center, double spreadDistance, ServerWorld world, Random random, double minX, double minZ, double maxX, double maxZ, int maxY, Pile[] piles) {
        boolean bl = true;
        double d = 3.4028234663852886E38;

        int i;
        for(i = 0; i < 10000 && bl; ++i) {
            bl = false;
            d = 3.4028234663852886E38;

            int k;
            Pile pile2;
            for(int j = 0; j < piles.length; ++j) {
                Pile pile = piles[j];
                k = 0;
                pile2 = new Pile();

                for(int l = 0; l < piles.length; ++l) {
                    if (j != l) {
                        Pile pile3 = piles[l];
                        double e = pile.getDistance(pile3);
                        d = Math.min(e, d);
                        if (e < spreadDistance) {
                            ++k;
                            pile2.x += pile3.x - pile.x;
                            pile2.z += pile3.z - pile.z;
                        }
                    }
                }

                if (k > 0) {
                    pile2.x /= (double)k;
                    pile2.z /= (double)k;
                    double f = pile2.absolute();
                    if (f > 0.0) {
                        pile2.normalize();
                        pile.subtract(pile2);
                    } else {
                        pile.setPileLocation(random, minX, minZ, maxX, maxZ);
                    }

                    bl = true;
                }

                if (pile.clamp(minX, minZ, maxX, maxZ)) {
                    bl = true;
                }
            }

            if (!bl) {
                Pile[] var28 = piles;
                int var29 = piles.length;

                for(k = 0; k < var29; ++k) {
                    pile2 = var28[k];
                    if (!pile2.isSafe(world, maxY)) {
                        pile2.setPileLocation(random, minX, minZ, maxX, maxZ);
                        bl = true;
                    }
                }
            }
        }

        if (d == 3.4028234663852886E38) {
            d = 0.0;
        }

        if (i >= 10000) {
            return;
        }
    }

    private static Pile[] makePiles(Random random, int count, double minX, double minZ, double maxX, double maxZ) {
        Pile[] piles = new Pile[count];

        for(int i = 0; i < piles.length; ++i) {
            Pile pile = new Pile();
            pile.setPileLocation(random, minX, minZ, maxX, maxZ);
            piles[i] = pile;
        }

        return piles;
    }

    static class Pile {
        double x;
        double z;

        Pile() {
        }

        double getDistance(Pile other) {
            double d = this.x - other.x;
            double e = this.z - other.z;
            return Math.sqrt(d * d + e * e);
        }

        void normalize() {
            double d = this.absolute();
            this.x /= d;
            this.z /= d;
        }

        double absolute() {
            return Math.sqrt(this.x * this.x + this.z * this.z);
        }

        public void subtract(Pile other) {
            this.x -= other.x;
            this.z -= other.z;
        }

        public boolean clamp(double minX, double minZ, double maxX, double maxZ) {
            boolean bl = false;
            if (this.x < minX) {
                this.x = minX;
                bl = true;
            } else if (this.x > maxX) {
                this.x = maxX;
                bl = true;
            }

            if (this.z < minZ) {
                this.z = minZ;
                bl = true;
            } else if (this.z > maxZ) {
                this.z = maxZ;
                bl = true;
            }

            return bl;
        }

        public int getY(BlockView blockView, int maxY) {
            BlockPos.Mutable mutable = new BlockPos.Mutable(this.x, (double)(maxY + 1), this.z);
            boolean bl = blockView.getBlockState(mutable).isAir();
            mutable.move(Direction.DOWN);

            boolean bl3;
            for(boolean bl2 = blockView.getBlockState(mutable).isAir(); mutable.getY() > blockView.getBottomY(); bl2 = bl3) {
                mutable.move(Direction.DOWN);
                bl3 = blockView.getBlockState(mutable).isAir();
                if (!bl3 && bl2 && bl) {
                    return mutable.getY() + 1;
                }

                bl = bl2;
            }

            return maxY + 1;
        }

        public boolean isSafe(BlockView world, int maxY) {
            BlockPos blockPos = BlockPos.ofFloored(this.x, (double)(this.getY(world, maxY) - 1), this.z);
            BlockState blockState = world.getBlockState(blockPos);
            return blockPos.getY() < maxY && !blockState.isLiquid() && !blockState.isIn(BlockTags.FIRE);
        }

        public void setPileLocation(Random random, double minX, double minZ, double maxX, double maxZ) {
            this.x = MathHelper.nextDouble(random, minX, maxX);
            this.z = MathHelper.nextDouble(random, minZ, maxZ);
        }
    }
}
