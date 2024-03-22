package org.karn.supersmashmobs.game.kit.drowned;

import net.minecraft.server.network.ServerPlayerEntity;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.game.kit.AbstractSkill;
import org.karn.supersmashmobs.game.kit.SkillType;

public class DrownedSkillD extends AbstractSkill {
    public static String id = "drowned_d";
    public static String name = "폭풍을 부르는 자";
    public static String desc = "폭풍우를 불러와 이동속도가 크게 증가하고 삼지창에 공격당한 적들은 기절합니다.";
    public static Integer cooldown = 100;
    public static SkillType type = SkillType.MOVEMENT;
    public DrownedSkillD(){
        super(id, name, desc, cooldown, type);
    }
    public static void main(ServerPlayerEntity player){
        DrownedSkillE.main(player);
    }
}
