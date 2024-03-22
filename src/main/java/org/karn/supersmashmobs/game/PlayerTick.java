package org.karn.supersmashmobs.game;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.packet.s2c.play.UpdateSelectedSlotS2CPacket;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.GameMode;
import org.joml.Vector3f;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.registry.SSMAttributes;
import org.karn.supersmashmobs.registry.SSMSounds;
import org.karn.supersmashmobs.util.GameMessages;
import org.karn.supersmashmobs.util.MessageSender;
import org.karn.supersmashmobs.util.Misc;

import java.util.*;

public class PlayerTick {
    public static void mainTick(ServerPlayerEntity player){
        if(player.isSpectator()) return;

        if(player.isOnGround() && !player.getAbilities().allowFlying && !EffectManager.hasBind(player)){
            player.getAbilities().allowFlying = true;
            player.sendAbilitiesUpdate();
        } else if(player.getAbilities().flying){
            player.getAbilities().flying = false;
            player.getAbilities().allowFlying = false;
            player.sendAbilitiesUpdate();
            player.setVelocity(player.getRotationVector().x/4,100/player.getAttributeValue(SSMAttributes.KNOCKBACK_TAKEN),player.getRotationVector().z/4);
            player.velocityModified = true;
            player.playSound(SoundEvents.ENTITY_BAT_TAKEOFF, SoundCategory.MASTER,0.5F,1.5F);
            player.getServerWorld().spawnParticles(new DustParticleEffect(new Vector3f(1,1,1),1),player.getX(),player.getY(),player.getZ(),30,0.5,0.5,0.5,0);
        }
        player.getHungerManager().setFoodLevel(20);

        if(!MainGame.getGameStatus()) return;
        if(player.server.getTicks()%20==0){
            player.heal((float) player.getAttributeValue(SSMAttributes.HEALTH_REGEN));
        }

        if(player.getInventory().selectedSlot != 4){
            HudApi a = (HudApi) player;
            if(!EffectManager.hasSilence(player)) {
                switch (player.getInventory().selectedSlot) {
                    case 0:
                        if (a.getSkillCoolA() <= 0) SkillRouter.routeSkillA(player);
                        break;
                    case 1:
                        if (a.getSkillCoolB() <= 0) SkillRouter.routeSkillB(player);
                        break;
                    case 2:
                        if (a.getSkillCoolC() <= 0) SkillRouter.routeSkillC(player);
                        break;
                    case 3:
                        if (a.canFinalSmash()) SkillRouter.routeSmash(player);
                        break;
                }
            }
            player.getInventory().selectedSlot = 4;
            player.networkHandler.sendPacket(new UpdateSelectedSlotS2CPacket(4));
        }

        if(player.getY() < 0){
            onPlayerDeath(player, player.getDamageSources().outOfWorld());
        }
    }

    public static void onPlayerDeath(ServerPlayerEntity player, DamageSource source){
        MessageSender.sendKillLog(player,source);

        if(!MainGame.isPlaying || MainGame.joinedPlayer.get(player) == null) return;
        int life = MainGame.joinedPlayer.get(player);
        MainGame.joinedPlayer.replace(player,life-1);

        player.setHealth(player.getMaxHealth());
        player.clearStatusEffects();
        player.changeGameMode(GameMode.SPECTATOR);
        player.getServerWorld().playSound(null,player.getX(),player.getY(),player.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER,2,1.5F);
        player.getServerWorld().playSound(null,player.getX(),player.getY(),player.getZ(), SSMSounds.DEATH2, SoundCategory.MASTER,2,1.5F);
        player.getServerWorld().spawnParticles(new DustParticleEffect(new Vector3f(1,1,0.33F),3),player.getX(),player.getY(),player.getZ(),200,0.1,5,0.1,0);

        player.teleport(0,70,0);
        if(life > 1){
            MessageSender.sendTitle(player, GameMessages.getDeathMsg(life-1),GameMessages.getReviveMsg(true));
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                public void run() {
                    MessageSender.clearTitle(player);
                    player.server.getCommandManager().executeWithPrefix(player.server.getCommandSource().withSilent().withEntity(player), "spreadplayers 0 0 16 80 false @s");

                    player.changeGameMode(GameMode.ADVENTURE);
                    player.sendAbilitiesUpdate();
                    //misc.spreadPlayers(player.getServerWorld(), temp, MainGame.ringPos, 16, 150, 200);
                }
            };
            timer.schedule(task, 60 * 50);
        } else {
            MessageSender.sendTitle(player,GameMessages.getDeathMsg(life-1),GameMessages.getReviveMsg(false));
        }
        MainGame.updateGame(player.server);
    }
}
