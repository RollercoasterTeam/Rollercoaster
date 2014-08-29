package robomuss.rc.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import robomuss.rc.RCMod;

/**
 * Created by Mark on 25/08/2014.
 */
public class RCEntitys {
	
    public static void init() {
        int entityID = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(EntityTrain.class, "EntityTrain", entityID);
        EntityRegistry.registerModEntity(EntityTrain.class, "EntityTrain", entityID, RCMod.instance, 64, 5, true);
    }
}
