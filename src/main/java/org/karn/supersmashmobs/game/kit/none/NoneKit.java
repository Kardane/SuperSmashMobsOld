package org.karn.supersmashmobs.game.kit.none;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.karn.supersmashmobs.game.kit.AbstractKit;
import org.karn.supersmashmobs.game.kit.AbstractSkill;
import org.karn.supersmashmobs.registry.SSMItems;

import java.util.ArrayList;
import java.util.List;

import static org.karn.supersmashmobs.SuperSmashMobs.MODID;

public class NoneKit extends AbstractKit {
    public static String id = "none";
    public static String name = "None";
    public static String desc = "Default kit. No special ability.";
    public static String icon = "\u5000";
    public static String smashicon = "\u5001";
    public static EntityType disguiseType = EntityType.ZOMBIE;

    public static Item SkillAItem = SSMItems.NONE_A;
    public static Item SkillBItem = SSMItems.NONE_B;
    public static Item SkillCItem = SSMItems.NONE_C;
    public static Item SkillDItem = SSMItems.NONE_D;
    public static Item SkillEItem = SSMItems.NONE_E;

    public static AbstractSkill SkillA = new NoneSkillA();
    public static AbstractSkill SkillB = new NoneSkillB();
    public static AbstractSkill SkillC = new NoneSkillC();
    public static AbstractSkill SkillD = new NoneSkillD();
    public static AbstractSkill SkillE = new NoneSkillE();

    public static Integer Health = 100;
    public static Integer HealthRegen = 1;
    public static Integer KnockbackMultiplier = 100;
    public static Float AttackSpeed = 20f;
    public static Float AttackDamage = 10f;
    public static Float Speed = 0.1f;
    public NoneKit(){
        super(id, name, desc, icon,smashicon,EntityType.ZOMBIE,
                Health,HealthRegen,KnockbackMultiplier,AttackSpeed,AttackDamage,Speed,
                SkillAItem,SkillBItem,SkillCItem,SkillDItem,SkillEItem,
                SkillA,SkillB,SkillC,SkillD,SkillE);
    }

    //================================================================================================
    @Override public void onRevive(ServerPlayerEntity player) {

    }
    @Override public void onDeath(ServerPlayerEntity player) {

    }
    @Override public void onHurt(ServerPlayerEntity player) {

    }
    @Override public void onAttack(ServerPlayerEntity player) {

    }

    @Override public String getId() {return id;}
    @Override public String getName() {return name;}
    @Override public String getDesc() {return desc;}

    @Override
    public String getSmashicon() {
        return smashicon;
    }

    @Override public EntityType getDisguiseMob() {return disguiseType;}
    @Override public AbstractSkill getSkillA() {return SkillA;}
    @Override public AbstractSkill getSkillB() {return SkillB;}
    @Override public AbstractSkill getSkillC() {return SkillC;}
    @Override public AbstractSkill getSkillD() {return SkillD;}
    @Override public AbstractSkill getSkillE() {return SkillE;}
    @Override public Item getSkillAItem() {return SkillAItem;}
    @Override public Item getSkillBItem() {return SkillBItem;}
    @Override public Item getSkillCItem() {return SkillCItem;}
    @Override public Item getSkillDItem() {return SkillDItem;}
    @Override public Item getSkillEItem() {return SkillEItem;}

    //================================================================================================

    public static class NoneAItem extends Item implements PolymerItem {
        private final PolymerModelData polymerModel;
        public NoneAItem() {
            super(new Item.Settings().maxCount(1));
            this.polymerModel = PolymerResourcePackUtils.requestModel(Items.PAPER, new Identifier(MODID, "item/none/a"));
            List<Text> tooltip = new ArrayList<>();
            tooltip.add(Text.of(SkillA.getDesc()));
            tooltip.add(Text.of(" 쿨다운: " + SkillA.getCooldown() + "초"));
            this.appendTooltip(this.getDefaultStack(), null,tooltip, TooltipContext.BASIC);
        }
        @Override public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.item();}
        @Override public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.value();}
    }

    public static class NoneEItem extends Item implements PolymerItem {
        private final PolymerModelData polymerModel;
        public NoneEItem() {
            super(new Item.Settings().maxCount(1));
            this.polymerModel = PolymerResourcePackUtils.requestModel(Items.PAPER, new Identifier(MODID, "item/none/e"));
        }
        @Override public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.item();}
        @Override public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.value();}
    }

    public static class NoneBItem extends Item implements PolymerItem {
        private final PolymerModelData polymerModel;
        public NoneBItem() {
            super(new Item.Settings().maxCount(1));
            this.polymerModel = PolymerResourcePackUtils.requestModel(Items.PAPER, new Identifier(MODID, "item/none/b"));
        }
        @Override public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.item();}
        @Override public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.value();}
    }

    public static class NoneCItem extends Item implements PolymerItem {
        private final PolymerModelData polymerModel;
        public NoneCItem() {
            super(new Item.Settings().maxCount(1));
            this.polymerModel = PolymerResourcePackUtils.requestModel(Items.PAPER, new Identifier(MODID, "item/none/c"));
        }
        @Override public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.item();}
        @Override public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.value();}
    }

    public static class NoneDItem extends Item implements PolymerItem {
        private final PolymerModelData polymerModel;
        public NoneDItem() {
            super(new Item.Settings().maxCount(1));
            this.polymerModel = PolymerResourcePackUtils.requestModel(Items.PAPER, new Identifier(MODID, "item/none/d"));
        }
        @Override public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.item();}
        @Override public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.value();}
    }
}
