package org.karn.supersmashmobs.game.kit.none;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.karn.supersmashmobs.game.kit.AbstractSkill;
import org.karn.supersmashmobs.game.kit.SkillType;

public class NoneSkillD extends AbstractSkill {
    public static String id = "none_d";
    public static String name = "NoneSkillD";
    public static String desc = "Skill D of None Kit";
    public static Integer cooldown = 100;
    public static SkillType type = SkillType.UTILITY;
    public NoneSkillD(){
        super(id, name, desc, cooldown, type);
    }
    public static void main(ServerPlayerEntity player){
        player.sendMessage(Text.literal("You don't have D skill!").formatted(Formatting.RED));
    }
}
