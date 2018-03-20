# UR calculator REPL

<!-- You can see this README rendered _much_ nicer online: https://github.com/strugee/csc-172-labs/blob/master/project2/README.md -->

This project contains an implementation of project 2, the UR calculator.

## Running

Run `URCalculator.java`.

## Features

* `exit` command
* `debug enable` and `debug disable` commands to show the results of lexing
* Helpful error messages
* All the usual calculator stuff
* `1(1)`-style multiplication
* Variable manipulation with (e.g.) `a=1`, `show all`, `clear all`, and `clear varname`
* Floating-point operations supported throughout

## Design

This is a two-phase interpreter pipeline. There's the lexer, which is responsible for parsing the string into something vaguely sensical, and the evaluator, which actually evaluates the token stream that the lexer outputs.

## Bugs

* Doesn't support line continuations
* `a=b=c=1` doesn't work

## Git

The Git repository for this project, complete with revision history, can be cloned from [GitHub](https://github.com/strugee/csc-172-labs).

## Author

AJ Jordan <alex@strugee.net>, <ajord17@u.rochester.edu>

## License

Creative Commons Zero 1.0