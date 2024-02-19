package org.karn.supersmashmobs.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class GameMessages {
    public static MutableText MODNAMETEXT = Text.empty().append(Text.literal("[SSM] ").formatted(Formatting.YELLOW).formatted(Formatting.BOLD));
    public static Text getDeathMsg(int life){
        return Text.empty().append(Text.literal("죽었습니다! ❤ "+(life)+"개").formatted(Formatting.GOLD));
    }

    public static Text getReviveMsg(boolean canRevive){
        if(canRevive)
            return Text.empty().append(Text.literal("잠시후 부활합니다...").formatted(Formatting.GREEN));
        else
            return Text.empty().append(Text.literal("남은 목숨이 없어 탈락했습니다!").formatted(Formatting.RED));
    }

    public static Text getGameStartMsg(){
        return Text.empty().append(Text.literal("[SSM] ").formatted(Formatting.YELLOW).formatted(Formatting.BOLD))
                .append(Text.literal("게임 시작!").formatted(Formatting.YELLOW).formatted(Formatting.BOLD));
    }

    public static Text getGameStopMsg(){
        return Text.empty().append(Text.literal("[SSM] ").formatted(Formatting.YELLOW).formatted(Formatting.BOLD))
                .append(Text.literal("게임 종료!").formatted(Formatting.RED).formatted(Formatting.BOLD));
    }

    public static Text getCrystalMsg(Vec2f pos){
        return Text.empty().append(Text.literal("[SSM] ").formatted(Formatting.YELLOW).formatted(Formatting.BOLD))
                .append(Text.literal("스매시 크리스탈이 [" + (int) pos.x + ","+ (int) pos.y + "] 에 소환됩니다!").formatted(Formatting.LIGHT_PURPLE).formatted(Formatting.BOLD));
    }

    public static Text getWinnerMsg(PlayerEntity player){
        return Text.empty().append(Text.literal("\uD83D\uDC51 ").append(player.getName()).append(" \uD83D\uDC51").formatted(Formatting.YELLOW).formatted(Formatting.BOLD));
    }
}
