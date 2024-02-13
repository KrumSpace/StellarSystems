# Notes to self

## Structure

* BaryUniverse
    * BarySystem (BaryObject)
        * BarySystems/BaryObjects
    * BarySimpleObject (BaryObject)
        * PhysicalBody


## Main updater cycle

1. universe.precalculate()
    * precalculates temp kineticParameters and velocities
2. universe.update()
    * updates actual kineticParameters from temps
3. universe.checkMeaninglessSystems()
    * go through all members
        * if it's a system, go deeper
        * if any object is outside the parent system's influence radius, move it up one level (doesn't apply to the universe)
    * check if current system has less than 2 members (doesn't apply to the universe)
        * if yes, dissolve it - move all members one level up
4. _create new systems where needed_
    * create new systems, indeed, but when?
    * some ideas are in the code, check there

Think about:
* where to check if an object enters an already existing system?
* maybe the universe doesn't need to be a BarySystem?
    * maybe it adds too many ifs and exceptions?
    * the fix would be for the direct-members of the universe to have their parents set to null
    * aargh, top-level container also has to be a system for proper three-or-more-body SOI radius calculations!!!
* when to create new systems?
    * if current systems member count is greater than 2
        * except if the current system is the root object (universe) - then it's allowed to create a system of the only 2 members
    * if any one member enters any others influence radius
        * check if any one of them is a system
            * if both are, combine them into one (allowed on root, allowed in 2-systems)
            * if just one is, the other object enters it (allowed on root, allowed in 2-systems)
            * if none are, create a new system (allowed on root, not allowed in 2-systems)


## Influence radius (SOI)

In a three-or-more-body system:

```
R_influence = R * (m / M) ^ (2 / 5)

R - distance to parent (if ellipse, R_influence changes with phaseAngle and is greatest at apocenter)
m - mass of self
M - mass of parent
```

Source: [Wikipedia - Sphere of influence](https://en.wikipedia.org/wiki/Sphere_of_influence_(astrodynamics))