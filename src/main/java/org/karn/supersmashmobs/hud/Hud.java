package org.karn.supersmashmobs.hud;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
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
        return Text.translatable("space.6")//전체 오프셋 조절
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
        MutableText base = Text.empty();
        base.append(Text.translatable("space.-2"))
                .append(Text.literal(a.getKit().icon).setStyle(guiStyle))
                .append("❤ "+ MainGame.joinedPlayer.get(player))
                .append("\uD83D\uDDE1"+(int) player.getAttributeValue(SSMAttributes.ATTACK_DMG))
                .append("⛏"+(int) player.getAttributeValue(SSMAttributes.ATTACK_SPEED))
                .append("\uD83D\uDEE1"+((int) player.getAttributeValue(SSMAttributes.PROTECTION) - 100))
                .append("⚓"+(int) player.getAttributeValue(SSMAttributes.KNOCKBACK_TAKEN))
                .append("\uD83E\uDD7E"+(int) player.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED))
                .append("\uD83D\uDC95"+(int)  player.getAttributeValue(SSMAttributes.HEALTH_REGEN))
        ;
        return base;
    }

    private static MutableText buildCooldown(ServerPlayerEntity player){
        HudApi a = (HudApi) player;
        MutableText base = Text.empty().append(Text.translatable("space.-180"));
        base.append(Text.literal("\u4006").setStyle(guiStyle))
                .append(getCooldownDisplay(a.getSkillCoolA()))
                .append(Text.translatable("space.2"));

        base.append(Text.literal("\u4007").setStyle(guiStyle))
                .append(getCooldownDisplay(a.getSkillCoolB()))
                .append(Text.translatable("space.2"));

        base.append(Text.literal("\u4008").setStyle(guiStyle))
                .append(getCooldownDisplay(a.getSkillCoolC()))
                .append(Text.translatable("space.2"));

        base.append(Text.literal("\u4009").setStyle(guiStyle))
                .append(getCooldownDisplay(a.getSkillCoolD()))
                .append(Text.translatable("space.2"));
        return base;
    }

    private static MutableText getCooldownDisplay(int cooldown){
        MutableText base = Text.empty();
        if(cooldown > 19){
            int a = cooldown/20;
            int b = String.valueOf(a).length();
            base.append(Text.translatable("space.-17"))
                    .append(Text.literal("\u4116").setStyle(guiStyle))
                    .append(Text.translatable("space.-"+(11 + (b-1) * 5 - (b-1))))//두자리수 9 //한자리수 11 //세자리수8
                    .append(Text.literal(String.valueOf(cooldown/20)).setStyle(guiSkillStyle))
                    .append(Text.translatable("space."+(11 - 6 - (b-1) - (b-1))));
            return base;
        } else if (cooldown > 0){
            double a = cooldown * 0.05;
            base.append(Text.translatable("space.-17"))
                    .append(Text.literal("\u4116").setStyle(guiStyle))
                    .append(Text.translatable("space.-16"))
                    .append(Text.literal("0").setStyle(guiSkillStyle))
                    .append(Text.translatable("space.-4"))
                    .append(Text.literal("."+String.valueOf(a).charAt(2)).setStyle(guiSkillStyle))
                    .append(Text.translatable("space.2"));
            return base;
        } else {
            return base;
        }
    }
}
