package top.jingwenmc.wtfsurvival.util;
//TODO:Use in future Server Edition
import org.bukkit.*;

import java.io.File;

public class WorldUtil {
    public static World createNewWorld(String worldName) {
        try {
            WorldCreator worldCreator = new WorldCreator(worldName);
            worldCreator.environment(World.Environment.NORMAL);
            worldCreator.generateStructures(true);
            worldCreator.hardcore(false);
            World world = worldCreator.createWorld();
            assert world != null;
            world.setDifficulty(Difficulty.NORMAL);
            world.setSpawnFlags(true, true);
            world.setPVP(true);
            world.setStorm(false);
            world.setThundering(false);
            world.setWeatherDuration(Integer.MAX_VALUE);
            world.setAutoSave(false);
            world.setKeepSpawnInMemory(false);
            world.setTicksPerAnimalSpawns(5);
            world.setTicksPerMonsterSpawns(15);
            world.setGameRule(GameRule.DISABLE_RAIDS, true);
            world.setGameRule(GameRule.DO_FIRE_TICK, false);
            world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            return world;
        } catch (Throwable e) {
            ExceptionUtil.print(e);
        }
        return null;
    }

    public static void unloadWorld(String w) {
        World world = Bukkit.getServer().getWorld(w);
        if(world != null) {
            Bukkit.getServer().unloadWorld(world, true);
        }
    }

    public static void deleteWorld(String name) {
        unloadWorld(name);
        File target = new File (Bukkit.getServer().getWorldContainer().getAbsolutePath(), name);
        deleteWorldFiles(target);
    }

    public static boolean deleteWorldFiles(File path) {
        try {
        if(path.exists()) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteWorldFiles(files[i]);
                } else {
                    if(!files[i].delete())throw new IllegalStateException("Unexpected Error");
                }
            }
        }
        return(path.delete());
    } catch (Throwable e) {
        ExceptionUtil.print(e);
    }
        return false;
    }
}
