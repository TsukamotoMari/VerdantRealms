# TitanScape RSPS

A custom RuneScape Private Server built from scratch in Java 17 with Netty networking, a Swing-based client, and a massive amount of custom content.

## Quick Start (Windows)

**Requirements:** Java 17+ installed ([Download here](https://adoptium.net))

1. **Build everything:** Double-click `Build-All.bat`
2. **Start the server:** Double-click `Start-Server.bat`
3. **Start the client:** Double-click `Start-Client.bat`
4. **Login and play!**

## Owner Account

- **Username:** `code187`
- **Password:** `Jackson123`
- **Rights:** Owner (all commands, maxed account, all custom items)

## Custom Bosses (4 Unique Bosses)

### Titan Lord (Combat 785)
The ultimate boss with 5,000 HP and 4 phases:
- **Phase 1:** Ground Slam — AoE melee damage in 3-tile radius
- **Phase 2:** Fire Breath — Ranged fire attack hitting all nearby players
- **Phase 3:** Shadow Bolt — Teleports randomly, magic attacks from darkness
- **Enrage (<10% HP):** Combo attack + self-healing from damage dealt
- **Drops:** Titanforge Blade (1/100), Titan Lord's Crown (1/25)

### Void Reaper (Combat 650)
A spectral boss that manipulates dimensions:
- **Phase 1:** Invisible Strike — Random damage from unseen attacks
- **Phase 2:** Soul Drain — Steals HP from players to heal itself
- **Phase 3:** Void Rift — Teleports players to random positions
- **Enrage:** Summons mirror clones that double its attacks
- **Drops:** Voidreaper Scythe (1/100), Spectral Cloak (1/25)

### Inferno Dragon (Combat 720)
A massive dragon deep in the Molten Caverns:
- **Phase 1:** Fire Pools — Leaves burning ground beneath players
- **Phase 2:** Takes Flight — Only rangeable/mageable, rains fireballs
- **Phase 3:** Earthquake Landing — Lava eruptions around arena
- **Enrage:** Permanent fire aura + devastating nova attack
- **Drops:** Dragonfire Lance (1/125), Infernal Wings (1/33)

### Crystal Golem (Combat 800)
The tankiest boss with 6,000 HP:
- **Phase 1:** Rock Throw + Crystal Shield (damage reduction)
- **Phase 2:** Crystal Shards — Exploding shards in 5-tile radius
- **Phase 3:** Crystal Encase — Stuns players in crystal prisons
- **Enrage:** Shatters for massive AoE then reforms with 25% HP
- **Drops:** Crystal Maul (1/100), Crystal Armor Shard (1/25)

## Custom Items (17 Unique Items)

| Item | Slot | Special Effect |
|------|------|----------------|
| Titanforge Blade | Weapon | 20% chance double damage + ignite |
| Voidreaper Scythe | Weapon | 3x3 AoE attacks + 10% lifesteal |
| Dragonfire Lance | Weapon | Dragonfire immunity + 15% fire breath |
| Crystal Maul | Weapon | Ignores 50% defence + 5% stun |
| Titan Lord's Crown | Helmet | +10% boss damage |
| Spectral Cloak | Cape | 25% dodge chance |
| Infernal Wings | Cape | Double run energy + flight cosmetic |
| Shadow Amulet | Amulet | 10% prayer drain on hit |
| Infernal Boots | Boots | Immune to ground effects |
| Ring of the Titans | Ring | +15% boss drop rate (crafted) |
| Titan Arena Cape | Cape | +5% all combat stats |
| Titan Essence | Currency | Universal custom currency |
| Void Shard | Material | Void-tier crafting |
| Dragon Heart | Material | Dragon-tier crafting |
| Crystal Armor Shard | Material | Crystal armor crafting |
| Golem Core | Material | Crystal armor enhancement |
| Arena Champion Token | Token | Arena reward shop currency |

## Custom Content

### Titan Arena (Minigame)
- Wave-based survival with scaling difficulty
- 1-4 players, rewards scale with waves survived
- Wave 10+: Arena Champion Token
- Wave 25+: Titan Arena Cape (best-in-slot cape)

### Custom Areas
- **Lumbridge** — Safe spawn with shops and Titan Forgemaster
- **Titan Fortress** — Titan Lord lair (combat 100+ required)
- **Void Realm** — Void Reaper dimension (combat 80+ required)
- **Molten Caverns** — Inferno Dragon's lair (combat 90+ required)
- **Crystal Caverns** — Crystal Golem's domain (combat 95+ required)
- **Titan Arena** — Wave survival minigame (combat 50+ required)

### Custom Skill: Titan Forging
A unique skill for crafting the most powerful equipment using boss materials and Titan Essence at the Titan Forge.

### Shops
- **General Store** — Basic supplies
- **Weapon Shop** — Starter weapons
- **Titan Essence Shop** — Custom items for Titan Essence

## Commands

### Player Commands
| Command | Description |
|---------|-------------|
| `::help` | Show all commands |
| `::heal` | Heal to full HP |
| `::pos` | Show current position |
| `::players` | Show online count |
| `::save` | Save your progress |

### Staff Commands
| Command | Description |
|---------|-------------|
| `::item [id] [amount]` | Spawn an item |
| `::tele [x] [y]` | Teleport |
| `::master` | Set all skills to 99 |
| `::setlevel [skill] [level]` | Set a skill level |

### Admin/Owner Commands
| Command | Description |
|---------|-------------|
| `::kick [name]` | Kick a player |
| `::ban [name]` | Ban a player |
| `::give [name] [id] [amt]` | Give item to player |
| `::spawn [npcId]` | Spawn an NPC |
| `::reload` | Reload definitions |

## Technical Architecture

- **Language:** Java 17
- **Networking:** Netty 4.1 (async NIO)
- **Client:** Java Swing with custom rendering
- **Data format:** JSON (items, NPCs, bosses, shops, drops, areas)
- **Build system:** Gradle 8.5
- **Game loop:** 600ms tick cycle
- **Protocol:** Custom binary packet protocol (opcode + length + payload)
- **Save system:** JSON-based player saves with auto-save

## Project Structure

```
titanscape-rsps/
├── Start-Server.bat          # One-click server start
├── Start-Client.bat          # One-click client start
├── Build-All.bat             # Build both JARs
├── build.gradle              # Gradle build config
├── data/
│   ├── items/                # Item definitions (JSON)
│   ├── npcs/                 # NPC definitions (JSON)
│   ├── bosses/               # Boss definitions (JSON)
│   ├── shops/                # Shop definitions (JSON)
│   ├── drops/                # Drop table definitions (JSON)
│   ├── areas/                # Area definitions (JSON)
│   └── saves/                # Player save files (JSON)
└── src/main/java/com/titanscape/
    ├── server/               # Server-side code
    │   ├── TitanScape.java   # Main server entry point
    │   ├── net/              # Networking (Netty)
    │   ├── model/            # Game models
    │   │   ├── entity/       # Players, NPCs, Bosses
    │   │   ├── item/         # Item system
    │   │   ├── combat/       # Combat engine
    │   │   ├── content/      # Shops, areas, minigames
    │   │   └── world/        # World, positions
    │   ├── task/             # Game engine loop
    │   ├── io/               # Save/load system
    │   ├── definition/       # JSON definition loaders
    │   └── util/             # Utilities
    └── client/               # Client-side code
        ├── TitanClient.java  # Main client entry point
        ├── ui/               # Swing UI (login, game, chat)
        └── net/              # Client networking
```

## Customization

All game content is defined in JSON files under `data/`. To add new items, bosses, NPCs, or areas, simply edit the corresponding JSON file and restart the server (or use `::reload`).

## License

This project is for educational and personal use.
