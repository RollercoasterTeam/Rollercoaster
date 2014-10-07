package robomuss.rc.entity;

import net.minecraftforge.fml.common.registry.EntityRegistry;
import robomuss.rc.RCMod;


public class RCEntitys {
	
    public static void init() {
        int entityID = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(EntityTrainDefault.class, "EntityTrain", entityID);
        EntityRegistry.registerModEntity(EntityTrainDefault.class, "EntityTrain", entityID, RCMod.instance, 64, 1, true);
    }
}
