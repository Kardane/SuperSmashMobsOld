package org.karn.supersmashmobs.game.kit.drowned;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.game.kit.AbstractSkill;
import org.karn.supersmashmobs.game.kit.SkillType;

public class DrownedSkillC extends AbstractSkill {
    public static String id = "drowned_c";
    public static String name = "밀려오는 파도";
    public static String desc = "전방으로 돌진합니다. 잠시후 파도가 뒤따라와 적을 밀쳐냅니다.";
    public static Integer cooldown = 100;
    public static SkillType type = SkillType.MOVEMENT;
    public DrownedSkillC(){
        super(id, name, desc, cooldown, type);
    }
    public static void main(ServerPlayerEntity player){
        player.sendMessage(Text.literal("asdc"));
        DrownedSkillE.main(player);
        ((HudApi) player).setSkillCoolC(cooldown);
    }
}
