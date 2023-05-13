# CSF307 Advanced Object-Oriented Programming - Coursework 1
<p align="center">
  <img src="res/rubber-duck.png" alt="drawing" width="100"/><br><br>
  <img src="https://img.shields.io/github/followers/micahdougall?style=social" alt="drawing" width="100"/>
</p>




## 

Brief summary to program arguments, options and execution.

- [Usage](#usage)
- [System](#system)
- [References](#usage)


## Installation

This program comes with a [Makefile](Makefile) to (re-)compile source files as needed. To compile using GCC, simply run `make` from the root directory.

All text files used for product input should be placed in the [res/](res/) folder.

To remove executables from [bin/](bin/) and text files from [res/](res/), simply run `make clean` from the command line in the root directory.

## Usage

The program executable, **main**, will be written to the [bin/](bin/) folder (by Makefile) and has a required (first) argument which should be the name of a text file containing products, eg:

```bash
./bin/main T5-Products-10.txt
```

### Optional arguments

An *optional* additional argument, **V** can be supplied to run the program in verbose mode. This mode includes additional print outs:
  - Status updates during program execution.
  - Full report print-out (all products).
  - Score print-out for all products.
  - Graph Trie print-out.

Eg:
```bash
./bin/main T5-Products-10.txt V
```

A sample of a Trie graph print-out can be seen at

```
| | |\2
| | |  \8
| | |    \2
| | |      \9
| | |        \1
| | |          \0
| | |            \3 Brabantia_Ironing_Board_Cover
| | |\3
| | | |\5
| | | |  \1
| | | |    \3
| | | |      \6
| | | |        \0
| | | |          \3 All-Clad_Stockpot
| | |  \6
| | |   |\0
| | |   |  \9
| | |   |    \6
| | |   |      \6
| | |   |        \4 Yellow_Bell_Pepper
| | |    \4
| | |      \1
| | |        \4
| | |          \7
| | |            \4 Pyrex_Glass_Baking_Dish
```

### Compile-time arguments

Product printing for the report, trie and lookup searches all use color/style enhancements for printing to the console. These have been chosen with a dark background in mind.

To remove all styles for print outputs, an argument needs to be set at compile time to provide the condition to the preprocessor. When using the [Makefile](Makefile), simply run `make plain` instead of `make`. 

## System

This code has been developed using:
- ~~Blood, sweat and tears~~
- Macbook Pro M1
- Sublime Text
- A rubber duck
- GCC, version:
    ```
    Apple clang version 14.0.3 (clang-1403.0.22.14.1)
    Target: arm64-apple-darwin22.3.0
    Thread model: posix
    ```

## References

- https://www.gnu.org/software/gnu-c-manual/gnu-c-manual.html
- https://www.gnu.org/prep/standards/html_node/Writing-C.html
- https://www.flaticon.com/free-icons/rubber-duck

## TODO

- Resize buffers for the strings
- Do not use arrays in structs, change tp pointrts
- free() malloc free in insert_into_trie & print_trie not working
- const ref for pointers
- cleanup: colors not needed, global vars, method spacing, gcc options in readme, calloc
- Bug in trie print for 300

