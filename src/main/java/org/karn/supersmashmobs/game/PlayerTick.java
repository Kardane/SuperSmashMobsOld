package org.karn.supersmashmobs.game;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.packet.s2c.play.UpdateSelectedSlotS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.registry.SSMAttributes;
import org.karn.supersmashmobs.util.GameMessages;
import org.karn.supersmashmobs.util.MessageSender;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerTick {
    public static void mainTick(ServerPlayerEntity player){
        if(!MainGame.getGameStatus()) return;
        if(player.server.getTicks()%20==0){
            player.getHungerManager().setFoodLevel(20);
            player.heal((float) player.getAttributeValue(SSMAttributes.HEALTH_REGEN));
        }


        if(player.getInventory().selectedSlot != 4){
            HudApi a = (HudApi) player;
            switch (player.getInventory().selectedSlot){
                case 0:
                    if(a.getSkillCoolA() <= 0) SkillRouter.routeSkillA(player);
                    break;
                case 1:
                    if(a.getSkillCoolB() <= 0) SkillRouter.routeSkillB(player);
                    break;
                case 2:
                    if(a.getSkillCoolC() <= 0) SkillRouter.routeSkillC(player);
                    break;
                case 3:
                    if(a.getSkillCoolD() <= 0) SkillRouter.routeSkillD(player);
                    break;
            }
            player.getInventory().selectedSlot = 4;
            player.networkHandler.sendPacket(new UpdateSelectedSlotS2CPacket(4));
        }
    }

    public static void onPlayerDeath(ServerPlayerEntity player, DamageSource source){
        MessageSender.sendKillLog(player,source);
        int life = MainGame.joinedPlayer.get(player);
        MainGame.joinedPlayer.replace(player,life-1);

        player.setHealth(player.getMaxHealth());
        player.clearStatusEffects();
        player.changeGameMode(GameMode.SPECTATOR);

        if(life > 1){
            MessageSender.sendTitle(player, GameMessages.getDeathMsg(life-1),GameMessages.getReviveMsg(true));
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                public void run() {
                    player.changeGameMode(GameMode.ADVENTURE);
                    player.sendAbilitiesUpdate();
                    MessageSender.clearTitle(player);
                }
            };
            timer.schedule(task, 60 * 50);
        } else {
            MessageSender.sendTitle(player,GameMessages.getDeathMsg(life-1),GameMessages.getReviveMsg(false));
        }
        MainGame.updateGame(player.server);
    }
}
