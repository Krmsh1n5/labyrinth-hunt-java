# Labyrinth Hunt

A text-based dungeon crawler written in Java. You wake up in a dark, four-room dungeon and have to fight your way out — moving across a grid, looting chests, managing ammo and bandages, collecting keys, and beating a healing boss to reach the exit.

The whole game runs in the terminal. No external libraries, no build tools — just the JDK.

Built as a practical project for the **Object Oriented Programming 1 (OOP1)** course — see [Course context](#course-context) below.

## Features

- **Grid-based movement** across four connected rooms rendered as an ASCII map.
- **Turn-based combat** with weapon switching, ammo tracking, and healing mid-fight.
- **Loot and inventory** — weapons, ammo, bandages, keys, and a crowbar, grouped and displayed by type.
- **Locks and keys** — doors and chests open with the matching key, and some can be forced with a crowbar.
- **A boss fight** — the Dungeon Lord hits harder than regular mobs and heals itself every few turns.
- **Multiple routes to the exit** — beat the boss for the exit key, or find the alternative key hidden in a locked chest.

## Gameplay

**Objective:** escape the dungeon. Work through Rooms 1–4, then leave through the exit door.

**Controls**

| Key | Action |
|-----|--------|
| `W` `A` `S` `D` | Move up / left / down / right |
| `O` | Open a nearby door or chest |
| `F` | Fight an adjacent enemy |
| `B` | Use your best bandage to heal |
| `I` | View inventory |
| `H` | Show help |
| `Q` | Quit (asks to confirm) |

Doors, chests, and enemies only respond when you're next to them (within one tile).

**Map legend**

| Symbol | Meaning |
|--------|---------|
| `@` | You |
| `M` | Monster / boss |
| `D` | Door |
| `C` | Chest |
| `#` | Wall |
| `.` | Floor |

**Combat** is menu-driven: attack with your current weapon, switch weapons, or heal. Attacking consumes ammo matching the weapon type, so keep an eye on your stock and swap when you run dry. The boss can restore its own health, so burst it down.

**Progression** runs Room 1 → 2 → 3 → 4 → exit. You start with the Bronze Key to Room 2; later keys are found in chests or dropped by enemies. The crowbar can pry open some doors and chests when you don't have the right key.

## Project structure

```
labyrinth-hunt-java/
├── Main.java              # Game setup and main loop (map, items, enemies, input handling)
├── entities/
│   ├── Entity.java        # Abstract base: health, inventory, position, strength
│   ├── Player.java        # The player — movement, surroundings, looting, key lookup
│   ├── Mob.java           # Regular enemy, lootable on death
│   └── Boss.java          # Stronger enemy that heals itself and hits harder
├── items/
│   ├── Item.java          # Base item
│   ├── Weapon.java        # Weapon with a type and damage
│   ├── Ammo.java          # Ammo tied to a weapon type, with a quantity
│   ├── Bandage.java       # Healing item with a quantity
│   ├── Key.java           # Unique key (UUID) that matches a door or chest
│   └── Crowbar.java       # Forces open crowbar-accessible doors/chests
├── world/
│   ├── World.java         # Container for rooms, doors, chests, entities, items
│   ├── Room.java          # 7×10 grid room; renders walls, doors, chests, entities
│   ├── Door.java          # Connects two rooms; lockable by key or crowbar
│   └── Chest.java         # Holds loot; lockable by key or crowbar
└── util/
    ├── Point.java         # 2D coordinate
    ├── Pair.java          # Generic (left, right) tuple
    ├── Activatable.java   # Interface for things unlocked with a key
    └── FightSequence.java # Turn-based combat loop
```

## Design overview

The code is organized around a small object model:

- **Entities** share an abstract `Entity` base (name, health, inventory, position, strength). `Player` adds movement and inventory logic, `Mob` adds lootable-on-death behavior, and `Boss` extends `Mob` with self-healing and extra damage.
- **Items** share an `Item` base and specialize into `Weapon`, `Ammo`, `Bandage`, `Key`, and `Crowbar`.
- **World** objects model the space: a `Room` owns a grid plus its doors, chests, and entities; `Door` and `Chest` implement a shared `Activatable` interface so both can be unlocked the same way (by matching key UUID, or by crowbar where allowed).
- **Util** holds small helpers — `Point`, a generic `Pair`, and `FightSequence`, which drives combat as a loop between player and mob turns.

`Main` wires everything together: it builds the four rooms (plus a room `0` that represents "outside"), populates them with items, mobs, and the boss, connects them with keyed doors, and then runs the input loop.

## Course context

This project was built for **Object Oriented Programming 1 (OOP1)**.

- **Parent course unit:** Computer sciences 2 (15 ECTS)
- **Component ECTS:** not separately published

The course covers Java OOP through:

- Classes and objects
- Inheritance and polymorphism
- Encapsulation and abstraction
- Exception handling
- File and stream I/O
- Generics
- Java Swing graphical user interfaces, event handling, and layout management
- Practical projects culminating in the design and implementation of a Java application

Labyrinth Hunt is one such practical project. It leans most directly on the first few topics — the class hierarchies under `entities/` and `items/`, polymorphic overrides like `attack()` and `getSymbol()`, encapsulated state behind getters/setters, the abstract `Entity` and the `Activatable` interface, and generics via the reusable `Pair<L, R>`.

## Requirements

- **JDK 8 or newer** (`javac` and `java` must be on your PATH).
- A terminal with **UTF-8** support, so the box-drawing characters and emoji in the game text display correctly.

## Build and run

From the project root, compile all sources into `bin/`:

```bash
javac -d bin Main.java items/*.java entities/*.java world/*.java util/*.java
```

Then run the game:

```bash
java -cp bin Main
```

> Note: compiled `.class` files and the `bin/` directory are git-ignored, so you build them locally.
