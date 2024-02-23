package org.karn.supersmashmobs.game;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.game.kit.none.NoneSkillA;
import org.karn.supersmashmobs.game.kit.none.NoneSkillB;
import org.karn.supersmashmobs.game.kit.none.NoneSkillC;
import org.karn.supersmashmobs.registry.SSMSounds;
import org.karn.supersmashmobs.util.GameMessages;
import org.karn.supersmashmobs.util.Misc;

public class SkillRouter {
    public static void routeSkillA(ServerPlayerEntity player){
        HudApi a = (HudApi) player;
        switch (a.getKit().getId()){
            case "none":
                NoneSkillA.main(player);
                break;
            case "creeper":
                break;
            case "enderman":
                break;
            case "skeleton":
                break;
            case "spider":
                break;
            case "zombie":
                break;
        }
    }
    public static void routeSkillB(ServerPlayerEntity player){
        HudApi a = (HudApi) player;
        switch (a.getKit().getId()){
            case "none":
                NoneSkillB.main(player);
                break;
            case "creeper":
                break;
            case "enderman":
                break;
            case "skeleton":
                break;
            case "spider":
                break;
            case "zombie":
                break;
        }
    }
    public static void routeSkillC(ServerPlayerEntity player){
        HudApi a = (HudApi) player;
        switch (a.getKit().getId()){
            case "none":
                NoneSkillC.main(player);
                break;
            case "creeper":
                break;
            case "enderman":
                break;
            case "skeleton":
                break;
            case "spider":
                break;
            case "zombie":
                break;
        }

    }

    public static void routeSmash(PlayerEntity player){
        HudApi a = (HudApi) player;
        Misc.playSoundToAll(player.getServer(), SSMSounds.FINAL_SMASH, SoundCategory.MASTER,2,1F);
        player.getServer().sendMessage(GameMessages.getCrystalUseMsg(player));
        SmashCrystal.showCrystalCutScene(player.getServer(), player);
        a.setFinalSmash(false);
        switch (a.getKit().getId()){
            case "none":
                break;
            case "creeper":
                break;
            case "enderman":
                break;
            case "skeleton":
                break;
            case "spider":
                break;
            case "zombie":
                break;
        }
    }
}
