package org.karn.supersmashmobs.particle;

import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.karn.supersmashmobs.util.Misc;

import java.util.List;
import java.util.Map;

public class LineRotation {
    public static List<Map<String, Double>> LineRotation(Vec3d pos1, Vec2f rot, double length, int numDots) {
        Vec3d pos2 = Misc.getLocalPos(pos1,rot,new Vec3d(0,0,length));
        List line = Line2Dots.Line2Point(pos1, pos2, numDots);
        return line;
    }

    public static List<Map<String, Double>> LineRotation(Vec3d pos1, Vec2f rot, double length, int numDots, Vec3d movement) {
        Vec3d pos2 = Misc.getLocalPos(pos1,rot,new Vec3d(0,0,length));
        List line = Line2Dots.Line2Point(pos1, pos2, numDots, movement);
        return line;
    }
}
