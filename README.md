# VerdantRealms

A NeoForge 1.21.4 Minecraft world generation mod featuring 12 stunning biomes, giant trees, new structures, edible plants, and breathtaking visual effects.

## Features

### 12 Custom Biomes
| Biome | Theme | Key Features |
|-------|-------|-------------|
| **Crystal Caverns** | Underground | Glowing crystal formations, amethyst geodes, witch particles |
| **Ethereal Grove** | Magical Forest | Glowing trees, happy villager particles, lush vegetation |
| **Ember Wastes** | Volcanic Desert | Flame particles, blaze spawns, scorched earth |
| **Frostfell Peaks** | Icy Mountains | Snowflake particles, polar bears, glacial stone |
| **Skyward Isles** | Floating Islands | End rod particles, floating terrain, parrots |
| **Verdant Jungle** | Dense Jungle | Giant mushrooms, massive trees, tropical fish |
| **Blossom Valley** | Cherry Orchard | Cherry blossom particles, lavender fields, pink water |
| **Shadowmire** | Dark Swamp | Mycelium particles, witches, corrupted atmosphere |
| **Starfall Plains** | Night Plains | Falling star particles, phantom spawns, dark sky |
| **Volcanic Badlands** | Lava Fields | Lava particles, magma cubes, volcanic vents |
| **Ancient Grove** | Old Growth | Spore blossom particles, Tree of Life, ancient ruins |
| **Corrupted Reaches** | Ender Wasteland | Dragon breath particles, endermen, void roots |

### Giant Trees
- **Elderwood** — Tall mystical trees with glowing leaves (8-15 blocks tall)
- **Starwood** — Dark trees with star-like luminescent leaves
- **Ashwood** — Burnt volcanic trees with sparse foliage
- **Cherry Blossom** — Beautiful pink trees with falling petal particles
- **Tree of Life** — Ultra-rare (0.5% spawn chance) colossal tree, 25+ blocks tall with moonstone canopy

### New Structures
- Ancient Ruins (spawn in Ancient Grove)
- Volcanic Vents (spawn in volcanic biomes)
- Frost Spires (spawn in icy biomes)
- Floating Islands (spawn in Skyward Isles)
- Crystal Spikes (spawn in caverns)

### Edible Plants
- **Wild Berries** — Found in jungle bushes, restores 3 hunger
- **Glow Fruit** — Found in glowing bushes, restores 5 hunger
- **Star Root** — Underground crop, restores 4 hunger
- **Moon Bread** — Crafted from moon wheat, restores 7 hunger
- **Lavender Tea** — Brewed from lavender, always edible, calming effect

### Visual Effects
- 8 custom particle types: Glow Spores, Falling Stars, Embers, Crystal Dust, Frost Motes, Shadow Tendrils, Blossom Petals, Volcanic Ash
- Custom fog colors per biome
- Ambient biome particles (fireflies, snow, embers)
- Glowing blocks (crystal blocks, luminous moss, moonstone)

## Setup & Installation

### Prerequisites
- Java 21 or higher
- Gradle 8.5+

### Building from Source

1. **Clone or extract the mod files**
   ```bash
   cd VerdantRealms
   ```

2. **Setup Gradle wrapper**
   ```bash
   gradle wrapper
   ```

3. **Build the mod**
   ```bash
   ./gradlew build
   ```
   (Use `gradlew.bat build` on Windows)

4. **Find your mod file**
   The compiled `.jar` will be in:
   ```
   build/libs/verdantrealms-1.0.0.jar
   ```

5. **Install to Minecraft**
   - Install NeoForge 1.21.4 (version 21.4.50-beta or higher)
   - Place the `.jar` in your `mods` folder
   - Launch Minecraft with NeoForge profile

### Development Environment

**IntelliJ IDEA:**
```bash
./gradlew genIntellijRuns
```
Then open the project in IntelliJ and run the `client` configuration.

**VS Code:**
Install the Extension Pack for Java, then:
```bash
./gradlew vscode
```

**Eclipse:**
```bash
./gradlew genEclipseRuns
```

## File Structure
```
VerdantRealms/
├── src/main/java/com/verdantrealms/
│   ├── VerdantRealms.java              # Main mod class
│   ├── biome/
│   │   └── ModBiomes.java              # 12 biome definitions
│   ├── block/
│   │   └── ModBlocks.java              # All custom blocks
│   ├── item/
│   │   └── ModItems.java               # Food items & block items
│   ├── particle/
│   │   └── ModParticles.java           # 8 particle types
│   ├── worldgen/
│   │   ├── feature/
│   │   │   └── ModConfiguredFeatures.java  # Tree & feature configs
│   │   └── tree/
│   │       └── ModTreeGrowers.java     # Tree growth logic
│   └── datagen/
│       └── ModWorldGenProvider.java    # Data generation
├── src/main/resources/
│   ├── assets/verdantrealms/           # Textures & models (add your own)
│   ├── data/verdantrealms/worldgen/
│   │   ├── biome/                      # 12 biome JSON files
│   │   ├── configured_feature/         # Feature placements
│   │   ├── placed_feature/             # World placement rules
│   │   └── noise_settings/             # Terrain generation
│   └── META-INF/neoforge.mods.toml     # Mod metadata
├── build.gradle
├── gradle.properties
└── settings.gradle
```

## Adding Textures

The mod uses placeholder references for textures. To add your own:

1. Create PNG files (16x16 for items, 16x16 or 32x32 for blocks) in:
   ```
   src/main/resources/assets/verdantrealms/textures/block/
   src/main/resources/assets/verdantrealms/textures/item/
   ```

2. Create model JSON files in:
   ```
   src/main/resources/assets/verdantrealms/models/block/
   src/main/resources/assets/verdantrealms/models/item/
   ```

3. Create blockstate files in:
   ```
   src/main/resources/assets/verdantrealms/blockstates/
   ```

## Performance Notes

This mod is designed for high-end PCs:
- Dense particle effects in every biome
- Giant tree generation (Tree of Life is 25+ blocks)
- Complex terrain features
- Multiple ambient effects running simultaneously

**Recommended specs:**
- CPU: Intel i5/Ryzen 5 or better
- RAM: 8GB+ allocated to Minecraft
- GPU: GTX 1060/RX 580 or better

For lower-end systems, you can reduce particle density in the mod config (future feature).

## License
MIT License — feel free to modify, distribute, or include in modpacks.

## Credits
Built for NeoForge 1.21.4 by the VerdantRealms Team.

**Note:** This is a complete mod structure. Some features (like custom particles and structure generation) require additional implementation for full in-game functionality. The biome generation, blocks, items, trees, and terrain are fully implemented and ready to build.
