package robomuss.rc.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import robomuss.rc.RCMod;

/**
 * Created by Mark on 25/08/2014.
 */
public class RCEntities {
    public static void init() {
        int entityID = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(EntityTrainDefault.class, "EntityTrain", entityID);
        EntityRegistry.registerModEntity(EntityTrainDefault.class, "EntityTrain", entityID, RCMod.instance, 64, 1, true);

        int entityID2 = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(EntityTrainDefault2.class, "EntityTrain2", entityID2);
        EntityRegistry.registerModEntity(EntityTrainDefault2.class, "EntityTrain2", entityID2, RCMod.instance, 64, 1, true);
    }
}
