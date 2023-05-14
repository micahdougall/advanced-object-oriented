CC = gcc
src_files = src/main.c src/product.h src/reader.c src/printer.c src/score.c src/trie.c src/global.h

main: $(src_files)
	$(CC) src/main.c -o bin/main

plain: $(src_files)
	$(CC) src/main.c -o bin/main -D PRINTED_TEXT=PLAIN

clean:
	rm -f bin/* res/*.txt