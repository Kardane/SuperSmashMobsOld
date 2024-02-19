package org.karn.supersmashmobs.util;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.ClearTitleS2CPacket;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.karn.supersmashmobs.game.MainGame;

public class MessageSender {
    public static void sendMsgAll(MinecraftServer server, Text msg){
        server.getPlayerManager().getPlayerList().forEach(p->{
            p.sendMessage(msg);
        });
    }

    public static void sendTitleAll(MinecraftServer server, Text title, @Nullable Text subtitle){
        server.getPlayerManager().getPlayerList().forEach(p->{
            sendTitle(p,title,subtitle);
        });
    }
    public static void sendKillLog(PlayerEntity player, DamageSource source){
        String sourceName = "null";
        if(source.isOf(DamageTypes.ON_FIRE) || source.isOf(DamageTypes.IN_FIRE) || source.isOf(DamageTypes.LAVA) || source.isOf(DamageTypes.HOT_FLOOR)){
            sourceName = "화염";
        } else if(source.isOf(DamageTypes.OUT_OF_WORLD) || source.isOf(DamageTypes.GENERIC_KILL) || source.isOf(DamageTypes.GENERIC) || source.isOf(DamageTypes.OUTSIDE_BORDER)){
            sourceName = "공허";
        } else if(source.getAttacker() != null){
            sourceName = source.getAttacker().getName().getString();
        } else {
            sourceName = source.getName();
        }
        int life = MainGame.joinedPlayer.get(player)-1;
        if(!MainGame.isPlaying) life = 0;
        Text killlog = Text.empty()
                .append(Text.literal("<"+player.getEntityName()+">").formatted(Formatting.RED).formatted(Formatting.BOLD))
                .append(Text.literal(" ⏪☠⏪ ").formatted(Formatting.YELLOW))
                .append(Text.literal("<"+sourceName+">").formatted(Formatting.RED).formatted(Formatting.BOLD))
                .append(Text.literal(" | ").formatted(Formatting.DARK_GRAY))
                .append("남은 목숨: "+life).formatted(Formatting.GRAY);
        sendMsgAll(player.getServer(),killlog);
    }

    public static void sendTitle(ServerPlayerEntity player, Text title, @Nullable Text subtitle){
        player.networkHandler.sendPacket(new TitleFadeS2CPacket(0,100,0));
        player.networkHandler.sendPacket(new TitleS2CPacket(title));
        if(subtitle == null)
            player.networkHandler.sendPacket(new SubtitleS2CPacket(Text.empty()));
        else
            player.networkHandler.sendPacket(new SubtitleS2CPacket(subtitle));
    }

    public static void clearTitle(ServerPlayerEntity player){
        player.networkHandler.sendPacket(new ClearTitleS2CPacket(true));
    }
}
