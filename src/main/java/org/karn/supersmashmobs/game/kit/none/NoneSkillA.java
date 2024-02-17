package org.karn.supersmashmobs.game.kit.none;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.game.kit.AbstractSkill;
import org.karn.supersmashmobs.game.kit.SkillType;

public class NoneSkillA extends AbstractSkill {
    public static String id = "none_a";
    public static String name = "NoneSkillA";
    public static String desc = "Skill A of None Kit";
    public static Integer cooldown = 10;
    public static SkillType type = SkillType.UTILITY;
    public NoneSkillA(){
        super(id, name, desc, cooldown, type);
    }
    public static void main(ServerPlayerEntity player){
        player.sendMessage(Text.literal("You don't have A skill!").formatted(Formatting.RED));
        player.sendMessage(Text.literal(id));
        player.sendMessage(Text.literal(name));
        player.sendMessage(Text.literal(desc));
        ((HudApi) player).setSkillCoolA(cooldown);
    }
}
