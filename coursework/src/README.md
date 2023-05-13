# CSF307 Advanced Object-Oriented Programming - Coursework 1
<p align="center">
  <img src="rubber-duck.png" alt="drawing" width="100"/><br><br>
  <img src="https://img.shields.io/github/followers/micahdougall?style=social" alt="drawing" width="100"/>
</p>




## 

Brief summary to program arguments, options and execution.

- [Usage](#usage)
- [System](#system)
- [References](#usage)



## Usage

**main** has a required (first) argument which should be the name of a local file containing products., eg:

```bash
./main T5-Products-10.txt
```



```mermaid
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


## System

This code has been developed using Sublime text editor and tested in CLion.
- Macbook Pro M1
- Sublime Text
- GCC:
    ```
    Apple clang version 14.0.3 (clang-1403.0.22.14.1)
    Target: arm64-apple-darwin22.3.0
    Thread model: posix
    ```

## References for tutorials/conventions used

- https://www.gnu.org/software/gnu-c-manual/gnu-c-manual.html
- https://www.gnu.org/prep/standards/html_node/Writing-C.html
- https://www.flaticon.com/free-icons/rubber-duck

## TODO

- Exit gracefully
- Function for interface
- free() memory
- Makefile clean and args for verbosity
- Resize buffers for the strings
- Consider that memory is not universal so check from malloc
- Check gcc compile options
- Assign free'd pointers to NULL or handle somehow
- Do not use arrays in structs, change tp pointrts
- malloc free in insert_into_trie not working
- consistent asterisk placement

