#include <stdbool.h>

#define ANSI_BOLD_WHITE "\033[0;1m"
#define ANSI_COLOR_MAGENTA "\033[35m"
#define ANSI_COLOR_CYAN "\033[36m"
#define ANSI_COLOR_ORANGE_BOLD "\033[38;5;214;1m"
#define ANSI_COLOR_GREEN_BOLD "\033[38;5;85;1m"
#define ANSI_COLOR_CYAN_BOLD "\033[36;1m"
// #define ANSI_COLOR_GREEN "\033[92m"
#define ANSI_COLOR_GREEN "\033[38;5;155m"
#define ANSI_COLOR_RESET "\x1b[0m"
#define ANSI_BACKGROUND "\033[105m"
#define ANSI_COLOR_CYAN_BACK "\033[105m"
#define ANSI_BACKGROUND_RESET "\033[40m"

#define MAX_LINE 1000

bool VERBOSE;
bool SEARCH_MODE;

/**
 * print_if() - Prints if a condition is true.
 * @condition: Condition to check, eg a bool.
 * @format: The format specifier to apply to the value.
 * @value: Value to print.
 * 
 * Can be passed a global boolean flag, eg. for only in verbose mode.
 */
#define print_if(condition, format, value) condition \
    ? printf(format, value) \
    : (void)0;