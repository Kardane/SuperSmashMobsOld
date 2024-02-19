package org.karn.supersmashmobs.util;

import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import org.karn.supersmashmobs.effect.AbstractSSMEffect;
import org.karn.supersmashmobs.effect.SSMEffectType;

import java.util.*;


public class misc {
    public static boolean isVoidDamage(DamageSource source){
        return source.isOf(DamageTypes.GENERIC_KILL) || source.isOf(DamageTypes.GENERIC) || source.isOf(DamageTypes.OUT_OF_WORLD)|| source.isOf(DamageTypes.FALL);
    }
    public static int randomInt(int min, int max){
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static boolean hasBindEffect(ServerPlayerEntity player){
        var map = player.getActiveStatusEffects();
        boolean hasBind = false;
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getKey() instanceof AbstractSSMEffect) {
                if (AbstractSSMEffect.getType().equals(SSMEffectType.BIND) ||
                        AbstractSSMEffect.getType().equals(SSMEffectType.STUN) ||
                        AbstractSSMEffect.getType().equals(SSMEffectType.STOP) ||
                        AbstractSSMEffect.getType().equals(SSMEffectType.KNOCKBACK) ||
                        AbstractSSMEffect.getType().equals(SSMEffectType.KNOCKUP)) {
                    hasBind = true;
                }
            }
        }
        return hasBind;
    }

    public static void playSoundToAll(MinecraftServer server, SoundEvent sound, SoundCategory category, float volume, float pitch){
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            player.playSound(sound, category, volume, pitch);
        }
    }

    // This method is used to spread players in a circle
    public static void spreadPlayers(MinecraftServer server, Map<PlayerEntity, Integer> players, Vec2f center, float spreadDistance, float maxRange, int maxY) {
        Random random = Random.create();
        double d = (double)(center.x - maxRange);
        double e = (double)(center.y - maxRange);
        double f = (double)(center.x + maxRange);
        double g = (double)(center.y + maxRange);
        Pile[] piles = makePiles(random, players.size(), d, e, f, g);
        spread(center, (double)spreadDistance, server.getOverworld(), random, d, e, f, g, maxY, piles);
        getMinDistance(players.keySet().stream().toList(), server.getOverworld(), piles, maxY);
    }

    private static void getMinDistance(List<PlayerEntity> players, ServerWorld world, Pile[] piles, int maxY) {
        double d = 0.0;
        int i = 0;

        double e;
        for (PlayerEntity player: players) {
            Entity entity = (Entity) player;
            Pile pile;
            pile = piles[i++];

            entity.teleport(world, (double)MathHelper.floor(pile.x) + 0.5, (double)pile.getY(world, maxY), (double)MathHelper.floor(pile.z) + 0.5, Set.of(), entity.getYaw(), entity.getPitch());
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
