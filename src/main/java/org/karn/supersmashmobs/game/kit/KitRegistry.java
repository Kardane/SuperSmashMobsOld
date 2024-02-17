package org.karn.supersmashmobs.game.kit;

import org.karn.supersmashmobs.game.kit.none.NoneKit;

public class KitRegistry {
    public static void init(){
        new NoneKit();

        System.out.println("KitRegistry initialized!");
    }
}
