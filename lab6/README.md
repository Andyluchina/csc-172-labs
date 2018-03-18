# Lab 6

<!-- You can see this README rendered _much_ nicer online: https://github.com/strugee/csc-172-labs/blob/master/lab6/README.md -->

This project contains an implementation of lab 6.

> "When in doubt, use brute force." - Ken Thompson, co-creator of the C programming language and the Unix operating system

## Testcases

`MakeChange` has a commented-out `main()` that was used to test the code.

`EightQueen` does too, but it's not very interesting. However, commit `f511a291bc9681f6763c306afeae972e9375d54f` removes some code that was used to validate the acceptance test. You may find that of interest (you can view said commit easily on GitHub).

## Design

The inline comments should be enough to give you an idea of how stuff works.

If you're confused about what the EightQueen code does, I recommend uncommenting the `draw()` call around line 141, adding a breakpoint on that line, and then running the debugger and pressing play a couple times. This will be _way_ more effective than any dense prose explanation I could write.

## Bugs

* It doesn't use the pretty Unicode version of `|`
* There's a problem in `advanceQueenInRow()` where it will advancea  row that's just been reset to the initial position; this is a WONTFIX since it finds the solution anyway
* I went with a bruteforce solution instead of an optimized algorithm, because life is too short

## Git

The Git repository for this project, complete with revision history, can be cloned from [GitHub](https://github.com/strugee/csc-172-labs).

## Author

AJ Jordan <alex@strugee.net>, <ajord17@u.rochester.edu>

## License

Creative Commons Zero 1.0