package org.karn.supersmashmobs.game.kit.drowned;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.game.MainGame;
import org.karn.supersmashmobs.game.kit.AbstractSkill;
import org.karn.supersmashmobs.game.kit.SkillType;
import org.karn.supersmashmobs.hitbox.LineHitbox;
import org.karn.supersmashmobs.particle.Line2Dots;
import org.karn.supersmashmobs.particle.LineRotation;
import org.karn.supersmashmobs.particle.ParticleDrawer;
import org.karn.supersmashmobs.util.Misc;

import java.util.TimerTask;

public class DrownedSkillA extends AbstractSkill {
    public static String id = "drowned_a";
    public static String name = "삼지창 찌르기";
    public static String desc = "삼지창으로 전방으로 찌릅니다.";
    public static Integer cooldown = 40;
    public static SkillType type = SkillType.ATTACK_MELEE;
    public static int length = 6;
    public DrownedSkillA(){
        super(id, name, desc, cooldown, type);
    }
    public static void main(ServerPlayerEntity player){
        drawParticle(player);
        scanHitbox(player);
        DisplayEntity.ItemDisplayEntity trident = new DisplayEntity.ItemDisplayEntity(EntityType.ITEM_DISPLAY,player.getServerWorld());
        trident.setPosition(player.getPos().add(0,1,0));

        trident.saveNbt(new NbtCompound());
        player.getServerWorld().spawnEntity(trident);
        MainGame.gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                trident.kill();
            }
        },20*50);

        player.getServerWorld().playSound(null,player.getBlockPos(), SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.MASTER,1,1);
        DrownedSkillE.main(player);
        ((HudApi) player).setSkillCoolA(cooldown);
    }

    private static void drawParticle(ServerPlayerEntity player){
        Vec3d pos = player.getPos().add(0,1,0);
        Vec2f rot = player.getRotationClient();

        var particle = Line2Dots.Line2Point(pos,Misc.getLocalPos(pos,rot,0,0,length),50);
        var particle2 = Line2Dots.Line2Point(Misc.getLocalPos(pos,rot,0.25,0,0),Misc.getLocalPos(pos,rot,0,0,length),50);
        var particle3 = Line2Dots.Line2Point(Misc.getLocalPos(pos,rot,-0.25,0,0),Misc.getLocalPos(pos,rot,0,0,length),50);
        ParticleDrawer.DotArrayDraw(player.getServerWorld(),DrownedKit.drownedDust,particle);
        ParticleDrawer.DotArrayDraw(player.getServerWorld(),DrownedKit.drownedDust,particle2);
        ParticleDrawer.DotArrayDraw(player.getServerWorld(),DrownedKit.drownedDust,particle3);
    }

    private static void scanHitbox(ServerPlayerEntity player){
        Vec3d pos = player.getPos().add(0,1,0);
        Vec2f rot = player.getRotationClient();
        Vec3d endPos = Misc.getLocalPos(pos,rot,0,0,length);
        var hitbox = LineHitbox.getLivingEntities(player.getServerWorld(), pos,endPos,1,length*10);
        hitbox.forEach(entity -> {
            if(entity != player) {
                entity.damage(player.getDamageSources().playerAttack(player),5);
            }
        });
    }
}
