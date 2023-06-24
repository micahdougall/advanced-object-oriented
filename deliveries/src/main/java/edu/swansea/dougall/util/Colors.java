package edu.swansea.dougall.util;

/**
 * Global class with ANSI escape codes for terminal colours.
 */
public class Colors {
    public static final String ANSI_BOLD_WHITE = "\033[0;1m";
    public static final String ANSI_COLOR_MAGENTA = "\033[95m";
    public static final String ANSI_COLOR_GREEN_BOLD = "\033[38;5;85;1m";
    public static final String ANSI_COLOR_CYAN_BOLD = "\033[96;1m";
    public static final String ANSI_COLOR_GREEN = "\033[38;5;155m";
    public static final String ANSI_COLOR_PURPLE = "\u001B[95m";
    public static final String ANSI_COLOR_RESET = "\u001B[0m";
    public static final String ANSI_COLOR_GRAY = "\u001B[90m";
    public static final String ANSI_COLOR_YELLOW = "\u001B[93m";
    public static final String ANSI_COLOR_RED = "\u001B[91m";
    public static final String ANSI_COLOR_BLUE = "\u001B[34m";
    public static final String ANSI_BACKGROUND = "\033[97;105m";
}
