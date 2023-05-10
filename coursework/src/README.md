# CSF307 Advanced Object-Oriented Programming - Coursework 1 🏆

![GitHub followers](https://img.shields.io/github/followers/micahdougall?style=social)


## Table of Contents (Optional)

If your README is long, add a table of contents to make it easy for users to find what they need.

- [System](#system)
- [Usage](#usage)
- [References](#usage)


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

## Usage

main.c takes a single argument as an input file containing products., eg:

```bash
./main T5-Products-10.txt
```

To add a screenshot, create an `assets/images` folder in your repository and upload your screenshot to it. Then, using the relative filepath, add it to your README using the following syntax:

    ```md
    ![alt text](assets/images/screenshot.png)
    ```
---



## References for tutorials/conventions used

- https://www.gnu.org/software/gnu-c-manual/gnu-c-manual.html
- https://www.gnu.org/prep/standards/html_node/Writing-C.html


## TODO

- Tidy up Trie logic
- Exit gracefully
- Comments for Trie
- Trie pretty print
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

