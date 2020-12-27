# rubikscube-solver

rubikscube-solver is a Java based algorithm that shuffles and solves a Rubik's cube returning a solution.

## Explanation

This project solves a Rubik's cube with the CFOP method (cross, first two layers, orient last layer, and permute last layer); the same process I use to solve a cube in real life.  The algorithm returns a solution in the form of an array of moves on the cube.  In running 1 million cubes, the average solution length was 105 moves, and the percent of successfully solved cube solutions was %.

## Build status

The program works, however the code is relatively ineffecient and needs a lot of improvement before it is finalized.  It uses if/else statements excessively, which should be replaced with higher level logic.
