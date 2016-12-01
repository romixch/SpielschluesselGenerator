# Game generator

This fun project uses genetic algorithms from biology to solve a hard problem:

Find the best game schedule

The problem is hard because the solutions space is so big that you can't just try all possibilities and calculate a score for each. If ou have 8 teams or more
you won't finish in a useful time. So we need another approach. Evolutionary algorithms give us the opportunity to evolve to a quite good state by try and error
like it happens in nature.

## Implementation

To save some time writing and reading boilerplate code I used Kotlin as a programming language.

I did not write the evolutionary algorithms on my own. I use [JGap](http://jgap.sourceforge.net/) instead. It is not very hip. But it works quite well.