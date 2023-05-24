CC=gcc
SRC_FILES=src/core/main.c include/modules//product.h src/modules/reader.c src/modules/printer.c src/modules/score.c src/modules/trie.c include/modules/global.h
INCLUDES=include/core/ include/modules/ src/modules

main: $(SRC_FILES)
	$(CC) $(foreach path, $(INCLUDES), -I $(path)) src/core/main.c -o bin/main

plain: $(SRC_FILES)
	$(CC) $(foreach path, $(INCLUDES), -I $(path)) src/core/main.c -o bin/main -D PRINTED_TEXT=PLAIN

clean:
	rm -f bin/* res/*.txt