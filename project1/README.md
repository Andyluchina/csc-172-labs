# 2048 game

<!-- You can see this README rendered _much_ nicer online: https://github.com/strugee/csc-172-labs/blob/master/project1/README.md -->

This project contains an implementation of project 1, the 2048 game.

## Running

Run `main.java`.

## Features

* Interactive console display with [wasd] control keys
* Fancy Unicode game board output
* Supports quitting and restarting with `q` and `r` keys, respectively (with prompts, unless the game has been won or lost)
* Last key pressed and move validity information display
* Move count display
* Maximum number on board display
* Extensively commented
* Robust design (see the "Design" section)

## Design

The design of this program is roughly modeled after the [Model-View-Controller](https://en.wikipedia.org/wiki/Model-view-controller) pattern. The model handles the data modelling concerns, including updating everything, the view just renders out the board to screen (in a [functional](https://en.wikipedia.org/wiki/Side_effect_(computer_science)) fashion), and the controller is responsible for taking user input, updating the model accordingly, and triggering a view render.

Note also that there are virtually no object instantiations in this codebase. This is because I really dislike Java-style object-oriented programming, as I have found that it tends to be overly verbose as well as encouraging overengineering and [architecture astronauting](https://www.joelonsoftware.com/2001/04/21/dont-let-architecture-astronauts-scare-you/). If you're interested, [this page](http://harmful.cat-v.org/software/OO_programming/) does a pretty good job summarizing my dislike of OOP, although its descriptions can be dicey at times.

Honestly, I've probably overcorrected, but what remains here is the absolute bare minimum to get 2048 working. Why not add a bunch of class hierarchies with fancy methods? You Aren't Gonna Need It (YAGNI), and frankly, life is too short. Jeff Atwood, founder of Stack Overflow, does a great job explaining YAGNI [in his blog](https://blog.codinghorror.com/kiss-and-yagni/).

## Bugs

* The algorithm to place a random number is horribly inefficient
* Unit tests are missing
* The model layer could use a lot of refactoring

## Git

The Git repository for this project, complete with revision history, can be cloned from [GitHub](https://github.com/strugee/csc-172-labs).

## Author

AJ Jordan <alex@strugee.net>, <ajord17@u.rochester.edu>

## License

Creative Commons Zero 1.0