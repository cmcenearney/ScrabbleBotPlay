ScrabbleBot
===========

```
         ,     ,
         (\____/)
          (_oo_)
            (O)
          __||__    \)
       []/______\[] /
       / \______/ \/
      /    /__\
     (\   /____\


```

There will be words...



Writing a program to play Scrabble is a great way to exercise and apply various string algorithms and data structures.

v1 of ScrabbleBot naively finds the highest scoring move for any given state of the board, and rack of tiles. Future versions may 
aim to implement some strategy beyond that.

To-do:

- [X] implement getHighestScoringMove
- [ ] add support for blank tiles (wildcards)
- [ ] convert the trie to a dawg (minimized dfa)
- [ ] convert move finding alg from two-pass to single-pass
- [ ] move the core into a separate project that the web app depends on
- [ ] optimize the prefix generation - using a "gaddag" or reverse prefix tree


