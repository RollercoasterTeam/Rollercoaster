package robomuss.rc.entity;

<<<<<<< HEAD:src/main/java/robomuss/rc/entity/RCEntitys.java
import net.minecraftforge.fml.common.registry.EntityRegistry;
import robomuss.rc.RCMod;


public class RCEntitys {
=======
import cpw.mods.fml.common.registry.EntityRegistry;
import robomuss.rc.RCMod;

/**
 * Created by Mark on 25/08/2014.
 */
public class RCEntities {
>>>>>>> master:src/main/java/robomuss/rc/entity/RCEntities.java
	
    public static void init() {
        int entityID = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(EntityTrainDefault.class, "EntityTrain", entityID);
        EntityRegistry.registerModEntity(EntityTrainDefault.class, "EntityTrain", entityID, RCMod.instance, 64, 1, true);
    }
}
