package org.karn.supersmashmobs.game.kit.none;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.game.kit.AbstractSkill;
import org.karn.supersmashmobs.game.kit.SkillType;

public class NoneSkillB extends AbstractSkill {
    public static String id = "none_b";
    public static String name = "NoneSkillB";
    public static String desc = "Skill B of None Kit";
    public static Integer cooldown = 300;
    public static SkillType type = SkillType.UTILITY;
    public NoneSkillB(){
        super(id, name, desc, cooldown, type);
    }
    public static void main(ServerPlayerEntity player){
        player.sendMessage(Text.literal("You don't have B skill!").formatted(Formatting.RED));
        ((HudApi) player).setSkillCoolB(cooldown);
    }
}
