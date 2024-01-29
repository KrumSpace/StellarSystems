# Stellar Systems

## Abstract 

A collection of various calculations relating to stellar systems.

Currently, simulates barycentric systems and object collisions in cartesian and polar coordinates.

by [Krumuvecis](https://github.com/Krumuvecis)

_Work is under progress..._

## Demo

Look for `src\test\java\demo\Main` for a standalone demo.

## Notes

### Structure

 * BaryUniverse
   * BarySystem (BaryObject)
     * BarySystems/BaryObjects
   * BarySimpleObject (BaryObject)
     * SimpleBody

### Main updater cycle

1. universe.precalculate()
   * precalculates temp coordinates and velocities
2. universe.update()
   * updates actual coordinates to temps
3. universe.checkMeaninglessSystems()
   * go through all members
     * if it's a system, go deeper
     * if any object is outside the parent system's influence radius, move it up one level (doesn't apply to the universe)
   * check if current system has less than 2 members (doesn't apply to the universe)
     * if yes, dissolve it - move all members one level up
4. _create new systems where needed_
   * create new systems, indeed, but when?

Think about:
 * where to check if an object enters an already existing system?
 * maybe the universe doesn't need to be a BarySystem?
   * maybe it adds too many ifs and exceptions?
   * the fix would be for the direct-members of the universe to have their parents set to null
   * either baryUniverse or baryModel is removable - possible duplicate functionality
 * when to create new systems?
   * if current systems member count is greater than 2
     * except if the current system is the root object (universe) - then it's allowed to create a system of the only 2 members
   * if any one member enters any others influence radius
     * check if any one of them is a system
       * if both are, combine them into one (allowed on root, allowed in 2-systems)
       * if just one is, the other object enters it (allowed on root, allowed in 2-systems)
       * if none are, create a new system (allowed on root, not allowed in 2-systems)

### Influence radius

In a three-or-more-body system:

```
R_influence = R * (m / M) ^ (2 / 5)

R - distance to parent (if ellipse, R_influence changes with phaseAngle and is greatest at apocenter)
m - mass of self
M - mass of parent
```

Source: [Wikipedia - Sphere of influence](https://en.wikipedia.org/wiki/Sphere_of_influence_(astrodynamics))