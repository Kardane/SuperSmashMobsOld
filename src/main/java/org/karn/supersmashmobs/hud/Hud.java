package org.karn.supersmashmobs.hud;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.game.MainGame;
import org.karn.supersmashmobs.registry.SSMAttributes;

import static org.karn.supersmashmobs.SuperSmashMobs.MODID;

public class Hud {
    private static Style emptyStyle = Text.empty().getStyle();
    public static Style defaultStyle = emptyStyle.withFont(new Identifier("minecraft","default"));
    public static Style guiStyle = emptyStyle.withFont(new Identifier(MODID,"gui")).withColor(TextColor.parse("#4e5c24"));
    public static Style guiDisplayStyle = emptyStyle.withFont(new Identifier(MODID,"gui_display")).withColor(TextColor.parse("#4e5c24"));
    public static Style guiSkillStyle = emptyStyle.withFont(new Identifier(MODID,"gui_skillcool")).withColor(TextColor.parse("#4e5c24"));
    public static Style guiStat1 = emptyStyle.withFont(new Identifier(MODID,"gui_stat1")).withColor(TextColor.parse("#4e5c24"));
    public static Style guiStat2 = emptyStyle.withFont(new Identifier(MODID,"gui_stat2")).withColor(TextColor.parse("#4e5c24"));
    public static Style guiStat3 = emptyStyle.withFont(new Identifier(MODID,"gui_stat3")).withColor(TextColor.parse("#4e5c24"));
    public static Style guiHealth = emptyStyle.withFont(new Identifier(MODID,"gui_health")).withColor(TextColor.parse("#4e5c24"));
    public static Text hotbarUp = Text.literal("\u4000").setStyle(guiStyle);

    //
    public static Text barSpace = Text.translatable("space.-1").setStyle(emptyStyle.withFont(new Identifier("minecraft","default")));
    // bar
    public static Text emptyBar = Text.literal("\u4002")
            .setStyle(emptyStyle.withColor(TextColor.fromRgb(20993)).withFont(new Identifier(MODID,"gui"))).append(barSpace);
    public static Text HealthBar = Text.literal("\u4002")
            .setStyle(emptyStyle.withColor(TextColor.fromRgb(580876)).withFont(new Identifier(MODID,"gui"))).append(barSpace);
    public static Text ShieldBar = Text.literal("\u4002")
            .setStyle(emptyStyle.withColor(TextColor.fromRgb(16768256)).withFont(new Identifier(MODID,"gui"))).append(barSpace);
    public static Text HurtBar = Text.literal("\u4002")
            .setStyle(emptyStyle.withColor(TextColor.fromRgb(16734285)).withFont(new Identifier(MODID,"gui"))).append(barSpace);


    public static double BIGDEALPERCENT = 0.33;
    public static int BIGDEALWAITTICK = 6;
    public static Text getHud(ServerPlayerEntity player){
        HudApi a = (HudApi) player;
        if(a.getKit() == null) return Text.literal("현재 킷이 없습니다!").formatted(Formatting.RED);
        return Text.translatable("space.-103")//전체 오프셋 조절
                .append(buildHealthBar(player,a.getHurtValue()))
                .append(hotbarUp)
                .append(buildStat(player))
                .append(buildCooldown(player));
    }

    private static MutableText buildHealthBar(ServerPlayerEntity player, int hurtValue){
        HudApi hudapi = (HudApi) player;
        double healthpercent = player.getHealth()/(player.getMaxHealth() + player.getAbsorptionAmount());
        double absorptionpercent = player.getAbsorptionAmount()/(player.getMaxHealth() + player.getAbsorptionAmount());
        double hurtpercent = hurtValue/(player.getMaxHealth() + player.getAbsorptionAmount());
        int hpbar = (int) (45*healthpercent);
        int abosrptionbar = (int) (45*absorptionpercent);
        int hurtbar = (int) (45*hurtpercent);
        int emptybar = 45 - hpbar - abosrptionbar;
        if(emptybar < hurtbar){
            hurtbar = emptybar;
            emptybar = 0;
        } else {
            emptybar = emptybar - hurtbar;
        }

        MutableText base = Text.empty();
        for(var i = 0;i<hpbar;i++){
            base.append(HealthBar);
        }
        for(var i = 0;i<abosrptionbar;i++){
            base.append(ShieldBar);
        }
        for(var i = 0;i<hurtbar;i++){
            base.append(HurtBar);
        }
        for(var i = 0;i<emptybar;i++){
            base.append(emptyBar);
        }

        //수치 표시

        int a = (int) player.getHealth();
        int b = (int) player.getMaxHealth();
        String hp = a+"/"+b;
        int aOffset = String.valueOf(a).length();
        int bOffset = String.valueOf(b).length();
        int hpOffset = String.valueOf(hp).length();
        base.append(Text.translatable("space.-"+(58+ aOffset*6+bOffset*6 - (bOffset-2)*6)).setStyle(defaultStyle))
                .append(hp).setStyle(guiDisplayStyle)
                .append(Text.translatable("space."+(-111+ aOffset*6+bOffset*6 - (bOffset-2)*12 - (aOffset-2)*6)).setStyle(defaultStyle));//줄이면 체력바 오른쪽으로이동;
        //base.append(Text.translatable("space.-139"));
        return base;
    }

    private static MutableText buildStat(ServerPlayerEntity player){
        HudApi a = (HudApi) player;
        var dmg = (int) player.getAttributeValue(SSMAttributes.ATTACK_DMG);
        var as = (int) player.getAttributeValue(SSMAttributes.ATTACK_SPEED);
        var speed = player.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        var regen = (int) player.getAttributeValue(SSMAttributes.HEALTH_REGEN);
        var knockback = (int) player.getAttributeValue(SSMAttributes.KNOCKBACK_TAKEN);
        var protection = (int) player.getAttributeValue(SSMAttributes.PROTECTION);
        int b = String.valueOf(dmg).length();
        int c = String.valueOf(as%20).length();
        int d = String.valueOf(speed).length();
        int e = String.valueOf(regen).length();
        int f = String.valueOf(knockback).length();
        int g = String.valueOf(protection - 100).length();
        MutableText base = Text.empty()
                .append(Text.translatable("space.-23"));

        //life
        if(MainGame.joinedPlayer.get(player) == null) {
            base.append(Text.literal(" ?").setStyle(guiHealth));
        } else {
            base.append(Text.literal(" "+MainGame.joinedPlayer.get(player)).setStyle(guiHealth));
        }

        //icon
        base.append(Text.translatable("space.5"))
                .append(Text.literal(a.getKit().icon).setStyle(guiStyle))
                .append(Text.translatable("space.-1"))
                .append(Text.literal("\u4100").setStyle(guiStyle))
                .append(Text.translatable("space.-76"))

                .append(Text.literal("\u4102").setStyle(guiStyle))
                .append(Text.literal(String.valueOf(dmg)).setStyle(guiStat1))
                .append(Text.translatable("space."+(12+4 - (b-1)*6)))//dmg

                .append(Text.literal("\u4103").setStyle(guiStyle))
                .append(Text.literal(String.valueOf(as/20)).setStyle(guiStat1))
                .append(Text.translatable("space.-4"))
                .append(Text.literal(".").setStyle(guiStat1))
                .append(Text.translatable("space.1"))
                .append(Text.literal(String.valueOf(as%20)).setStyle(guiStat1))
                .append(Text.translatable("space.-"+(63 + (c-1)*6)))//as


                .append(Text.literal("\u4104").setStyle(guiStyle))
                .append(Text.literal(String.valueOf(protection - 100)).setStyle(guiStat2))
                .append(Text.translatable("space."+(12+4 - (g-1)*6)))//protection

                .append(Text.literal("\u4105").setStyle(guiStyle))
                .append(Text.literal(String.valueOf(knockback)).setStyle(guiStat2))
                .append(Text.translatable("space.-"+(52 + (f-1)*6)))//knockback

                .append(Text.literal("\u4106").setStyle(guiStyle))
                .append(Text.literal((String.valueOf((int) Math.floor(speed*100)/10))).setStyle(guiStat3))
                .append(Text.translatable("space.-4"))
                .append(Text.literal(".").setStyle(guiStat3))
                .append(Text.translatable("space.1"))
                .append(Text.literal(String.valueOf((int) Math.floor(speed*100)%10)).setStyle(guiStat3))
                .append(Text.translatable("space.8"))

                .append(Text.literal("\u4107").setStyle(guiStyle))
                .append(Text.literal(String.valueOf(regen)).setStyle(guiStat3))
        ;
        return base;
    }

    private static MutableText buildCooldown(ServerPlayerEntity player){
        HudApi a = (HudApi) player;
        MutableText base = Text.empty().append(Text.translatable("space.-248"));
        base.append(getCooldownDisplay(a.getSkillCoolA()))
                .append(Text.translatable("space.1"))
                .append(getCooldownDisplay(a.getSkillCoolB()))
                .append(Text.translatable("space.1"))
                .append(getCooldownDisplay(a.getSkillCoolC()))
                .append(Text.translatable("space.1"));

        if(a.canFinalSmash()){
            base.append(Text.translatable("space.4"))
                    .append(Text.literal("\u4108").setStyle(guiStyle))
                    .append(Text.literal("사용가능").setStyle(guiSkillStyle).formatted(Formatting.GREEN));
        } else {
            base.append(Text.translatable("space.4"))
                    .append(Text.literal("\u4108").setStyle(guiStyle))
                    .append(Text.literal("사용불가").setStyle(guiSkillStyle).formatted(Formatting.RED));
        }
        base.append(Text.translatable("space.-64"));

        return base.append(Text.translatable("space.-"+(4 + (String.valueOf(a.getSkillCoolA()/20).length()-1)*8 + (String.valueOf(a.getSkillCoolB()/20).length()-1)*8 + (String.valueOf(a.getSkillCoolC()/20).length()-1)*8) ));
    }

    private static MutableText getCooldownDisplay(int cooldown){
        MutableText base = Text.empty();
        if(cooldown > 0){
            int a = cooldown/20;
            int b = String.valueOf(a).length();
            return base.append(Text.literal("[").setStyle(guiSkillStyle).formatted(Formatting.RED))
                    .append(Text.literal(String.valueOf(a)).setStyle(guiSkillStyle).formatted(Formatting.RED))
                    .append(Text.translatable("space.-5"))
                    .append(Text.literal("]").setStyle(guiSkillStyle).formatted(Formatting.RED));
        }else {
            return base.append(Text.literal("[0").setStyle(guiSkillStyle).formatted(Formatting.GOLD))
                    .append(Text.translatable("space.-5"))
                    .append(Text.literal("]").setStyle(guiSkillStyle).formatted(Formatting.GOLD));
        }
    }
}
