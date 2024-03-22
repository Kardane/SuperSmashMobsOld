package org.karn.supersmashmobs.registry;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.karn.supersmashmobs.game.kit.drowned.DrownedKit;
import org.karn.supersmashmobs.game.kit.none.NoneKit;

import static org.karn.supersmashmobs.SuperSmashMobs.MODID;

public class SSMItems {
    public static final Item NONE_A = register("none_a", new NoneKit.NoneAItem());
    public static final Item NONE_B = register("none_b", new NoneKit.NoneBItem());
    public static final Item NONE_C = register("none_c", new NoneKit.NoneCItem());
    public static final Item NONE_D = register("none_d", new NoneKit.NoneDItem());
    public static final Item NONE_E = register("none_e", new NoneKit.NoneEItem());
    public static final Item DROWNED_A = register("drowned_a", new DrownedKit.DrownedAItem());
    public static final Item DROWNED_B = register("drowned_b", new DrownedKit.DrownedBItem());
    public static final Item DROWNED_C = register("drowned_c", new DrownedKit.DrownedCItem());
    public static final Item DROWNED_D = register("drowned_d", new DrownedKit.DrownedDItem());
    public static final Item DROWNED_E = register("drowned_e", new DrownedKit.DrownedEItem());

    public static <T extends Item> T register(String path, T item) {
        Registry.register(Registries.ITEM, new Identifier(MODID, path), item);
        return item;
    }
    public static void init() {}
}
