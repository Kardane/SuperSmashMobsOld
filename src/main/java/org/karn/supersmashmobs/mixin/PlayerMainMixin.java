package org.karn.supersmashmobs.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.game.kit.AbstractKit;
import org.karn.supersmashmobs.game.kit.none.NoneKit;
import org.karn.supersmashmobs.registry.SSMAttributes;
import org.karn.supersmashmobs.game.MainGame;
import org.karn.supersmashmobs.game.PlayerTick;
import org.karn.supersmashmobs.hud.Hud;
import org.karn.supersmashmobs.util.misc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Timer;
import java.util.TimerTask;

import static org.karn.supersmashmobs.hud.Hud.BIGDEALPERCENT;
import static org.karn.supersmashmobs.hud.Hud.BIGDEALWAITTICK;

@Mixin(ServerPlayerEntity.class)
public class PlayerMainMixin implements HudApi {
    private final ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
    public AbstractKit kit = new NoneKit();
    @Override public AbstractKit getKit() {return this.kit;}
    @Override public void setKit(AbstractKit kit) {this.kit = kit;}
    private int decreaseHurtValue = 0;
    public int AttackCooldown = 0;
    public int HurtValue = 0;
    public int SkillCoolA = -1;
    public int SkillCoolB = -1;
    public int SkillCoolC = -1;
    public int SkillCoolD = -1;
    public boolean slowHurtAnimation = false;
    @Override public int getHurtValue() {return this.HurtValue;}
    @Override public int getSkillCoolA() {return this.SkillCoolA;}
    @Override public int getSkillCoolB() {return this.SkillCoolB;}
    @Override public int getSkillCoolC() {return this.SkillCoolC;}
    @Override public int getSkillCoolD() {return this.SkillCoolD;}
    @Override public void setHurtValue(int value) {this.HurtValue = value;}
    @Override public void setSkillCoolA(int value) {this.SkillCoolA = value;}
    @Override public void setSkillCoolB(int value) {this.SkillCoolB = value;}
    @Override public void setSkillCoolC(int value) {this.SkillCoolC = value;}
    @Override public void setSkillCoolD(int value) {this.SkillCoolD = value;}

    @Inject(method = "tick", at = @At("TAIL"))
    private void showUI(CallbackInfo ci) {
        if(!player.getWorld().isClient && !player.isCreative() && !player.isSpectator()){
            if(player.server.getTicks()%2==0){
                player.sendMessage(Hud.getHud(player),true);
                if(HurtValue>=0 && !slowHurtAnimation){
                    HurtValue-=decreaseHurtValue;
                    if(HurtValue<0) HurtValue = 0;
                }
            }
            PlayerTick.mainTick(player);
            if(AttackCooldown>0)AttackCooldown--;
            if(SkillCoolA>=0)SkillCoolA--;
            if(SkillCoolB>=0)SkillCoolB--;
            if(SkillCoolC>=0)SkillCoolC--;
            if(SkillCoolD>=0)SkillCoolD--;
        }
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void addHurtValue(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(!misc.isVoidDamage(source) && !player.getWorld().isClient) {
            float finalAmount = (float) (amount * player.getAttributeValue(SSMAttributes.PROTECTION)/100);
            player.hurtTime = 0;

            HurtValue = (int) finalAmount;
            decreaseHurtValue = HurtValue/4+1;
            if (finalAmount / player.getMaxHealth() > BIGDEALPERCENT) {
                decreaseHurtValue = HurtValue/8;
                slowHurtAnimation = true;
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    public void run() {
                        slowHurtAnimation = false;
                    }
                };
                timer.schedule(task, BIGDEALWAITTICK * 50);
            }

            if(finalAmount >= player.getHealth()){
                PlayerTick.onPlayerDeath(player,source);
            } else {
                player.damage(player.getDamageSources().generic(),finalAmount);
            }
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void tryAttack(Entity target, CallbackInfo ci){
        if(!player.getWorld().isClient && !player.isSpectator()){
            if(AttackCooldown <= 0){
                target.damage(player.getDamageSources().playerAttack(player), (float) player.getAttributeValue(SSMAttributes.ATTACK_DMG));
                AttackCooldown = (int) player.getAttributeValue(SSMAttributes.ATTACK_SPEED);
            }
            ci.cancel();
        }
    }
}
