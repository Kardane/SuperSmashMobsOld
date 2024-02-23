package org.karn.supersmashmobs.game.kit.none;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.karn.supersmashmobs.game.kit.AbstractSkill;
import org.karn.supersmashmobs.game.kit.SkillType;

public class NoneSkillC extends AbstractSkill {
    public static String id = "none_c";
    public static String name = "NoneSkillC";
    public static String desc = "Skill C of None Kit";
    public static Integer cooldown = 10;
    public static SkillType type = SkillType.UTILITY;
    public NoneSkillC(){
        super(id, name, desc, cooldown, type);
    }
    public static void main(ServerPlayerEntity player){
        player.sendMessage(Text.literal("You don't have C skill!").formatted(Formatting.RED));
    }

    @Override public String getId() {return id;}
    @Override public String getName() {return name;}
    @Override public String getDesc() {return desc;}
    @Override public SkillType getSkillType() {return type;}
    @Override public Integer getCooldown() {return cooldown;}
}
