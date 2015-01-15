package chococraft.common.config.gson;

import chococraft.common.ModChocoCraft;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.world.biome.BiomeGenBase;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by clienthax on 14/1/2015.
 */
public class ConfigGson {

	private Gson gson = new GsonBuilder().setVersion(1.0).setPrettyPrinting().create();
	public ChocoConfig config = null;
	private final File configFile = new File(ModChocoCraft.configFolder, "chococraft.json");

	public ConfigGson() {
		getConfig();
	}

	public ChocoConfig getConfig() {
		if(config == null)
			loadConfig();
		saveConfig();
		return config;
	}

	private void loadConfig() {
		if(!configFile.exists()) {
			convertOldConfig();
			return;
		}
		try {
			String jsonString = IOUtils.toString(new FileReader(configFile));
			config = gson.fromJson(jsonString, ChocoConfig.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveConfig() {
		try {
			FileWriter fileWriter = new FileWriter(configFile);
			IOUtils.write(gson.toJson(config), fileWriter);
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void convertOldConfig() {
		config = new ChocoConfig();
		config.showChocoboNames = ModChocoCraft.showChocoboNames;
		config.chocoboWingFlutter = ModChocoCraft.chocoboWingFlutter;
		config.hungerEnabled = ModChocoCraft.hungerEnabled;
		config.riderBuffsEnabled = ModChocoCraft.riderBuffsEnabled;
		config.saddledCanWander = ModChocoCraft.saddledCanWander;
		config.renderNameHeight = ModChocoCraft.renderNameHeight;
		config.livingSoundProbability = ModChocoCraft.livingSoundProb;
		config.gysahlGreenMutationRate = ModChocoCraft.gysahlGreenMutationRate;
		config.gysahlLoverlyMutationRate = ModChocoCraft.gysahlLoveMutationRate;
		config.gysahlGreenWorldGenerationRate = ModChocoCraft.gysahlWorldGenRate;
		config.breedingDelayFemale = ModChocoCraft.breedingDelayFemale;
		config.breedingDelayMale = ModChocoCraft.breedingDelayMale;
		config.growUpDelayStatic = ModChocoCraft.growupDelayStatic;
		config.growUpDelayRandom = ModChocoCraft.growupDelayRandom;
		config.hungerDelayChicobo = ModChocoCraft.hungerDelayChicobo;
		config.hungerDelayChocobo = ModChocoCraft.hungerDelayChocobo;
		config.penHealProbability = ModChocoCraft.penHealProbability;
		config.penHealCauldronRange = ModChocoCraft.penHealCauldronRange;
		config.wildCanDespawn = ModChocoCraft.wildCanDespawn;
		config.chocopediaInDungeons = ModChocoCraft.chocopediaInDungeons;

		HashSet<String> biomeNames = new HashSet<String>();
		for(BiomeGenBase biomeGenBase : ModChocoCraft.spawnBiomes)
			biomeNames.add(biomeGenBase.biomeName);

		config.spawnBiomesNames = biomeNames;

		config.spawnTimeDelay = ModChocoCraft.spawnTimeDelay;
		config.spawnProbability = ModChocoCraft.spawnProbability;
		config.spawnGroupMin = ModChocoCraft.spawnGroupMin;
		config.spawnGroupMax = ModChocoCraft.spawnGroupMax;
		config.spawnTotalMax = ModChocoCraft.spawnTotalMax;
		config.spawnLimitChunkRadius = ModChocoCraft.spawnLimitChunkRadius;
		config.distanceNextWild = ModChocoCraft.spawnDistanceNextWild;
		config.featherDelayRandom = ModChocoCraft.featherDelayRandom;
		config.featherDelayStatic = ModChocoCraft.featherDelayStatic;
		config.featherDropChance = ModChocoCraft.featherDropChance;
	}

	public class ChocoConfig {
		public boolean showChocoboNames;
		public boolean chocoboWingFlutter;
		public boolean hungerEnabled;
		public boolean riderBuffsEnabled;
		public boolean saddledCanWander;
		public double renderNameHeight;
		public int livingSoundProbability;
		public int gysahlGreenMutationRate;
		public int gysahlLoverlyMutationRate;
		public int gysahlGreenWorldGenerationRate;
		public int breedingDelayFemale;
		public int breedingDelayMale;
		public int growUpDelayStatic;
		public int growUpDelayRandom;
		public int hungerDelayChicobo;
		public int hungerDelayChocobo;
		public int penHealProbability;
		public int penHealCauldronRange;
		public boolean wildCanDespawn;
		public boolean chocopediaInDungeons;
		public Set<String> spawnBiomesNames;
		public int spawnTimeDelay;
		public int spawnProbability;
		public int spawnGroupMin;
		public int spawnGroupMax;
		public int spawnTotalMax;
		public int spawnLimitChunkRadius;
		public int distanceNextWild;
		public int featherDelayRandom;
		public int featherDelayStatic;
		public int featherDropChance;
	}



}
