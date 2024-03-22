package org.karn.supersmashmobs.game.kit.drowned;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.game.kit.AbstractSkill;
import org.karn.supersmashmobs.game.kit.SkillType;

public class DrownedSkillB extends AbstractSkill {
    public static String id = "drowned_b";
    public static String name = "삼지창 던지기";
    public static String desc = "삼지창을 던집니다. 멀리서 맞은 적은 기절합니다.";
    public static Integer cooldown = 100;
    public static SkillType type = SkillType.ATTACK_RANGED;
    public DrownedSkillB(){
        super(id, name, desc, cooldown, type);
    }
    public static void main(ServerPlayerEntity player){
        player.sendMessage(Text.literal("asdb"));
        DrownedSkillE.main(player);
        ((HudApi) player).setSkillCoolB(cooldown);
    }
}
