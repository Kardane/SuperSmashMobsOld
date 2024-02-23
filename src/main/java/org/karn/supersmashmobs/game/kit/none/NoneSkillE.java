package org.karn.supersmashmobs.game.kit.none;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.game.kit.AbstractSkill;
import org.karn.supersmashmobs.game.kit.SkillType;

public class NoneSkillE extends AbstractSkill {
    public static String id = "none_e";
    public static String name = "NoneSkillE";
    public static String desc = "Skill E of None Kit";
    public static Integer cooldown = 100;
    public static SkillType type = SkillType.UTILITY;
    public NoneSkillE(){
        super(id, name, desc, cooldown, type);
    }
    public static void main(ServerPlayerEntity player){
        player.sendMessage(Text.literal("You don't have E skill!").formatted(Formatting.RED));
        player.sendMessage(Text.literal(id));
        player.sendMessage(Text.literal(name));
        player.sendMessage(Text.literal(desc));
        ((HudApi) player).setSkillCoolA(cooldown);
    }

    @Override public String getId() {return id;}
    @Override public String getName() {return name;}
    @Override public String getDesc() {return desc;}
    @Override public SkillType getSkillType() {return type;}
    @Override public Integer getCooldown() {return cooldown;}
}
